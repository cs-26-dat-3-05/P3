# P3 Repository

## Forudsætninger

- Du skal kun bruge [Docker Desktop](https://www.docker.com/products/docker-desktop/) (indeholder Docker Compose).
- Hvis du vil udvikle udenfor Docker: Java 25, Maven 3.9 og Node.js 24.

## Opstart

1. Klon repository

   ```bash
   git clone git@github.com:cs-26-dat-3-05/P3.git
   ```

2. Start docker (Hvis du bruger Mac/Windows: Hav Docker Desktop kørende først):

   ```bash
   docker compose up --build
   ```

3. Tjek at alt kører:

   | Hvad             | Hvor                                    | Forventet                |
   | ---------------- | --------------------------------------- | ------------------------ |
   | Frontend         | <http://localhost:5173>                 | Hello world              |
   | Backend (Spring) | <http://localhost:8080/actuator/health> | JSON med `"status":"UP"` |
   | Swagger UI       | <http://localhost:8080/swagger-ui.html> | API-dokumentation (tom)  |
   | MailHog          | <http://localhost:8025>                 | Indbakke for mails (tom) |

4. Stop docker med `Ctrl+C`, eller `docker compose down` hvis det kører i baggrunden.

En `.env`-fil er ikke nødvendig, da alle værdier har standardværdier i `compose.yaml`. Se
[Konfiguration](#konfiguration), hvis du vil ændre dem.

## Generel udvikling

**Start i baggrunden og følg loggen for én tjeneste**

```bash
docker compose up -d --build
docker compose logs -f backend
```

**Genstart efter ændringer i `pom.xml` eller `package.json`**

```bash
docker compose restart backend     # eller frontend
```

**Nulstil alle databaser.** Dette sletter alle data og kører migrationerne og
mock-scriptet igen. Ikke vær bange for at gøre det - vi har altid test data i repositoriet:

```bash
docker compose down -v
docker compose up --build
```

**Tilføj en databasemigration.** Opret en fil i `backend/src/main/resources/db/migration/`
med navnet `V2__beskriv_aendringen.sql`, derefter `V3__...` og så videre. Ret aldrig i en
migration, der allerede er merget. Flyway nægter at starte, hvis en migration, der allerede
er kørt, er blevet ændret. Lav i stedet en ny migration.

**Forbind til en database** med f.eks. DBeaver, IntelliJ eller `psql`:

```bash
psql -h localhost -p 5432 -U p3_project p3_project     # password: p3_project
psql -h localhost -p 5433 -U place2book place2book     # password: place2book
```

**Kør backenden uden for Docker.** Start de andre tjenester med
`docker compose up db place2book-db mailhog`, og kør backenden fra din IDE eller med
`mvn spring-boot:run -Dspring-boot.run.profiles=dev` i `backend/`. Bemærk, at standardværdierne
i `application.yml` ikke passer til Compose-databasen, så du skal sætte
`SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/p3_project` samt brugernavn og kodeord
(`p3_project`) som miljøvariabler.

### Unit Tests (Backend)

Når docker containeren kører, kan man bruge:
```bash
docker exec p3-backend-1 mvn test
```

Tests ligger i `backend/src/test/java/`, og et eksempel findes i `ExampleUnitTest.java`. 

Man kan læse yderligere dokumentation om tests her:
- [JUnit User Guide](https://docs.junit.org/current/user-guide/): sådan skrives tests (`@Test`, `assertThrows`, `@ParameterizedTest` m.m.)
- [AssertJ](https://assertj.github.io/doc/): `assertThat(...)`-assertions
- [Spring Boot: Testing](https://docs.spring.io/spring-boot/reference/testing/index.html): test af Spring-kode (til senere)

**Kør frontenden uden for Docker.** Kør `npm install` og derefter `npm run dev` i `frontend/`.
Den videresender `/api` til `http://localhost:8080`.
