# Use an official OpenJDK runtime as a parent image
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Create a non-root user
RUN groupadd -r appgroup && useradd -r -g appgroup -G root -u 1001 appuser

# Copy the JAR file built by Maven into the container
COPY target/config-server-0.0.1-SNAPSHOT.jar /app/config-server.jar

# Change ownership to the non-root user
RUN chown -R appuser:appgroup /app

# Expose the port the config server will run on
EXPOSE 8888

# Run the application as the non-root user
USER appuser
ENTRYPOINT ["java", "-jar", "config-server.jar"]