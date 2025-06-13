FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

# copy everything
COPY . .

# make sure the Gradle wrapper is executable
RUN chmod +x ./gradlew

# build with Gradle
RUN ./gradlew clean build --no-daemon

# run the resulting JAR
CMD ["java", "-jar", "build/libs/pet-adoption-api.jar"]
