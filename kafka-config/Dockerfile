# Start with an official Java runtime image
FROM openjdk:21-jdk-slim

# Set working directory inside the container
WORKDIR /app

# Copy the packaged jar file into the container
COPY target/kafka-config-0.0.1-SNAPSHOT.jar /app/kafka-config.jar

# Expose the default Spring Boot port
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "kafka-config.jar"]
