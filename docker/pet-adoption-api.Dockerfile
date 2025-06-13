# 1) Build stage using an ARM-compatible Java 17 JDK
FROM eclipse-temurin:17-jdk-focal AS build

WORKDIR /app
COPY . .

# Ensure the Gradle wrapper is executable and build the app
RUN chmod +x ./gradlew \
 && ./gradlew clean build --no-daemon -x test

# 2) Runtime stage using the slimmer Java 17 JRE
FROM eclipse-temurin:17-jre-focal

WORKDIR /app

# Copy the fat JAR from the build stage
COPY --from=build /app/build/libs/pet-adoption-api.jar .

# Launch the application
ENTRYPOINT ["java", "-jar", "pet-adoption-api.jar"]
