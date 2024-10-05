# Use an official Java 8 runtime as the base image
FROM openjdk:8-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/coindesk-0.0.1-SNAPSHOT.jar coindesk.jar

# Expose the port that your Spring Boot app runs on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "coindesk.jar"]
