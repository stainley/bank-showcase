# Use an official OpenJDK runtime as a parent image
FROM openjdk:21-slim

# Set the working directory
WORKDIR /app

# Copy the built jar file from target directory
COPY target/user-service-0.0.1-SNAPSHOT.jar user-service.jar

# Expose the port the application runs on
EXPOSE 8081

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "user-service.jar"]
