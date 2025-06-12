# ARM-compatible Gradle builder with Java 17
FROM arm32v7/gradle:8.9.0-jdk17 AS build
WORKDIR /build
COPY . .

RUN ./gradlew clean build --no-daemon -p .

# ARM-compatible Java runtime using BellSoft
FROM bellsoft/liberica-openjdk:17.0.10-3
WORKDIR /app
COPY --from=build /build/build/libs/pet-adoption-api-1.0.0-SNAPSHOT.jar app.jar

ENTRYPOINT exec java $JAVA_OPTS -jar app.jar
