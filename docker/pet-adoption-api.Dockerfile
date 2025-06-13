# Use Eclipse Temurin Java 17 base image (ARM-compatible)
FROM eclipse-temurin:17-jdk-jammy

# Set working directory
WORKDIR /app

# Copy build files into image
COPY . .

# (Optional) If your build requires JAVA_HOME explicitly
ENV JAVA_HOME=/opt/java/openjdk
ENV PATH="${JAVA_HOME}/bin:${PATH}"

# Build the app (adjust for Maven or Gradle)
RUN ./mvnw clean package

# Run the app
CMD ["java", "-jar", "target/pet-adoption-api.jar"]
