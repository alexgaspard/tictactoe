package me.alex.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class IntegrationTest {

    private final String rootURL = "http://localhost:";
    @Value("${local.server.port:8888}")
    private String port;
    private TestRestTemplate restTemplate;

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
}
