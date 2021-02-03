package me.alex.application.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = "me.alex.game")
@EnableJpaRepositories(basePackages = "me.alex.game")
@EntityScan("me.alex.game")
//@ComponentScan(basePackages = {"me.alex.game", "me.alex.h2db", "me.alex.restapiv1"})
public class ApplicationConfiguration {

//    @Bean
//    public MovesV1Controller addMovesV1Controller(GameDrawer gameDrawer) {
//        return new MovesV1Controller(gameDrawer);
//    }
//
//    @Bean
//    public MovesV2Controller addMovesV2Controller(GameDrawer gameDrawer) {
//        return new MovesV2Controller(gameDrawer);
//    }
//    @Bean
//    public CaseStateSaver addCaseStateSaver(CaseCRUD caseCRUD) {
//        return caseCRUD;
//    }
//    @Bean
//    public CaseCRUD addCaseCRUD(CaseRepository repository) {
//        return new CaseCRUD( repository);
//    }
}
