# 1) Build stage: compile & package without running tests
FROM eclipse-temurin:17-jdk-focal AS build
WORKDIR /app

# Copy source & make the wrapper executable
COPY . .
RUN chmod +x ./gradlew

# Build the JAR (skip tests so it can complete in CI/Docker)
RUN ./gradlew clean build --no-daemon -x test

# 2) Runtime stage: slim JRE + your packaged JAR
FROM eclipse-temurin:17-jre-focal
WORKDIR /app

# Copy the exact JAR produced by Gradle, and rename it for consistency
COPY --from=build /app/build/libs/pet-adoption-api-1.0.0-SNAPSHOT.jar ./app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
