# Builder stage: ARM-compatible Gradle + JDK 17
FROM arm32v7/gradle:8.4.0-jdk17 AS build
WORKDIR /build
COPY . .

RUN ./gradlew clean build --no-daemon -p .

# Runtime stage: ARM-compatible JDK 17
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /build/build/libs/pet-adoption-api-1.0.0-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
