# Use an official OpenJDK image as the base image
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the packaged jar file to the container
COPY target/transaction-service-0.0.1-SNAPSHOT.jar /app/transaction-service.jar

# Expose the port the service will run on
EXPOSE 8081

# Command to run the application
ENTRYPOINT ["java", "-jar", "transaction-service.jar"]
