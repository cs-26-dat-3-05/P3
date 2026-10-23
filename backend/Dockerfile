# Build stage: compile the backend with Maven and Java 25
FROM maven:3.9-eclipse-temurin-25 AS build
WORKDIR /app

# Download dependencies first, so they are cached between builds
COPY pom.xml .
RUN mvn -q dependency:go-offline

COPY src ./src
RUN mvn -q package -DskipTests

# Dev stage: runs from source with DevTools (used by compose.yaml)
FROM maven:3.9-eclipse-temurin-25 AS dev
WORKDIR /app
CMD ["sh", "dev.sh"]

# Run stage: only the Java runtime and the built jar
FROM eclipse-temurin:25-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
