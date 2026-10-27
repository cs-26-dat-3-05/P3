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

**Tilføj en databasemigration.** Opret en fil i `backend/src/main/resources/db/migration/` med navnet `V2__beskriv_aendringen.sql`, derefter `V3__...` og så videre. Ret aldrig i en migration, der allerede er merget. Flyway nægter at starte, hvis en migration, der allerede er kørt, er blevet ændret. Lav i stedet en ny migration.

**Forbind til en database** med f.eks. DBeaver, IntelliJ eller `psql`:

```bash
psql -h localhost -p 5432 -U p3_project p3_project     # password: p3_project (dette er spring databasen)
psql -h localhost -p 5433 -U p3_project_reader place2book     # password: reader (dette er den interne database)
```

Place2Book-databasen (port 5433) er en mock af Place2Books interne database, som vi kun må læse fra. Brug derfor altid read-only-brugeren `p3_project_reader` (password: `reader`), både i backenden og når du forbinder manuelt. Brugeren kan kun lave `SELECT`, så man ikke ved et uheld kommer til at ændre data.