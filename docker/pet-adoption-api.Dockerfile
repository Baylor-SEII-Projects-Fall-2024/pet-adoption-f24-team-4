# 1) Build stage using an ARM-compatible Java 17 JDK
FROM eclipse-temurin:17-jdk-jammy AS build

WORKDIR /app
COPY . .

# ensure the Gradle wrapper is executable
RUN chmod +x ./gradlew

# tell Gradle exactly where Java lives in this image
ENV JAVA_HOME=/usr/lib/jvm/java-17-temurin
ENV PATH="$JAVA_HOME/bin:$PATH"

# compile and package
RUN ./gradlew clean build --no-daemon

# 2) Runtime stage using the slimmer JRE
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app
# pull in only the fat JAR from build stage
COPY --from=build /app/build/libs/pet-adoption-api.jar .

ENTRYPOINT ["java","-jar","pet-adoption-api.jar"]
