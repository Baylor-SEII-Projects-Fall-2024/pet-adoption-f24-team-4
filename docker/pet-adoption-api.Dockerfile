# Use ARM-compatible Gradle image
FROM arm32v7/gradle:8.9.0-jdk17 AS build
WORKDIR /build
COPY . .

# Clean and then build the project
RUN ./gradlew clean build --no-daemon -p .

# Runtime image (OpenJDK for ARM)
FROM arm32v7/openjdk:17
WORKDIR /app
COPY --from=build /build/build/libs/pet-adoption-api-1.0.0-SNAPSHOT.jar app.jar

# Run the app
ENTRYPOINT exec java $JAVA_OPTS -jar app.jar
