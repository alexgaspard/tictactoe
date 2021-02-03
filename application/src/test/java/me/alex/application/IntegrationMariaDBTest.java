package me.alex.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//@SpringBootTest(classes = {ConfigFileApplicationContextInitializer.class, DemoConfiguration.class})
@ContextConfiguration(initializers = IntegrationMariaDBTest.Initializer.class)
@ActiveProfiles("test")
@Testcontainers
class IntegrationMariaDBTest {

   private static final String SECRET = "secret";
   private static final String DB = "mydb";
   @Container
   public static GenericContainer<?> mariadb = new GenericContainer<>("postgres:13")
           .withExposedPorts(5432)
           .withEnv("POSTGRES_PASSWORD", SECRET)
           .withEnv("POSTGRES_DB", DB)
           .waitingFor(Wait.forLogMessage(".*PostgreSQL init process complete; ready for start up.*", 1));
   private final String rootURL = "http://localhost:";
   @Value("${local.server.port:8888}")
   private String port;
   private TestRestTemplate restTemplate;
   @Value("${spring.datasource.url}")
   private String mariadbUrl;
   @Value("${spring.datasource.username}")
   private String mariadbUsername;
   @Value("${spring.datasource.password}")
   private String mariadbPassword;

   @BeforeEach
   public void setup() {
      restTemplate = new TestRestTemplate();
   }

   @Test
   void createMoveAndResetScenario() throws MalformedURLException {
      String newUser = "{\"x\": \"0\",\"y\": \"0\",\"symbol\": \"X\"}";

      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.valueOf("application/vnd.alex.move-v1+json"));

      HttpEntity<String> entity = new HttpEntity<>(newUser, headers);
      ResponseEntity<String> response = restTemplate.postForEntity(buildURL() + "/moves", entity, String.class);
      assertEquals(HttpStatus.CREATED, response.getStatusCode());
      assertEquals("{\"wins\": false}", response.getBody());

      response = restTemplate.postForEntity(buildURL() + "/moves", entity, String.class);
      assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

      restTemplate.delete(buildURL() + "/moves");
      assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
      response = restTemplate.postForEntity(buildURL() + "/moves", entity, String.class);
      assertEquals(HttpStatus.CREATED, response.getStatusCode());
      assertEquals("{\"wins\": false}", response.getBody());
   }

   private URL buildURL() throws MalformedURLException {
      return new URL(rootURL + port);
   }

   static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

      @Override
      public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
         TestPropertyValues.of(
                 "spring.datasource.url="
                         + "jdbc:postgresql://" + mariadb.getContainerIpAddress() + ":" + mariadb.getFirstMappedPort() + "/" + DB,
                 "spring.datasource.username=" + "postgres",
                 "spring.datasource.password=" + SECRET
         ).applyTo(configurableApplicationContext.getEnvironment());
      }

   }
}
