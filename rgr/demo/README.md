# RGR: Spring Boot Web Application (Championships / Teams / Participants)

A training project on **Java + Spring Boot**. The application implements CRUD operations for the entities:
- **Team**
- **Participant**
- **Championship**
and manages the **Championship ↔ Teams** relationship (add/remove a team from the championship) via a web interface.

## Technologies
- Java 17+ (or your version)
- Spring Boot
- Spring MVC + Thymeleaf
- Spring Data JPA (Hibernate)
- Spring Security (USER/ADMIN roles)
- Maven
- MySQL (or other DB you use)

## Project structure
- `src/main/java` — Java code (controllers, services, repositories, entities, dto)
- `src/main/resources/templates` — Thymeleaf HTML templates (`index.html`, etc.)
- `src/main/resources/application.properties` — application settings (no secrets)
- `pom.xml` — Maven configuration
- `.gitignore` — excludes `target/`, build files, and IDE files

## Settings
In the `src/main/resources/application.properties` file, specify the connection to the DB:
- `spring.datasource.url=...`
- `spring.datasource.username=...`
- `spring.datasource.password=...` (recommended via environment variables or empty value)

Example:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/lab3
spring.datasource.username=root
spring.datasource.password=root123

## How to run

### 1) Run via Maven
```bash
mvn spring-boot:run
### 2) Build and run JAR
```mvn clean package
```java -jar target/*.jar
```After running, the application is available at: http://localhost:12345/

## Roles and access
- USER: view data
- ADMIN: create/edit/delete + manage teams in the championship
