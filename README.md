## Motivation

A spring-boot application used to demonstrate different development techniques from here and there. The original structure follows hexagonal architecture and comes from [this project](https://github.com/gshaw-pivotal/spring-hexagonal-example). Hence, all rules of the tic tac toe game are implemented in the domain while adapters take care of implementing the technologies choices. Unit tests are only behavioral and on the game logic. To test that adapters work well together, there are integration tests at the application level with different examples depending on the chosen adapters.

The wiring is in `application/pom.xml` for Spring instanced classes. Choosing a h2 database or a mariadb instance needs only to change the dependency used for `?db-adapter` in `application/pom.xml`.
API v1 or v2 can be enabled through configuration by modifying application.properties.

## Other inspirations

- [API versioning](https://medium.com/@XenoSnowFox/youre-thinking-about-api-versioning-in-the-wrong-way-6c656c1c516b)
- [Integration tests](https://www.testcontainers.org/)
