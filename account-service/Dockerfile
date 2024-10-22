# Use an official OpenJDK 17 image as the base
FROM openjdk:21-slim

# Set environment variables for the Spring Boot profile and database credentials
ENV SPRING_BOOT_PROFILE=docker \
    DATABASE_URL=jdbc:postgresql://postgres:5432/account_db \
    DATABASE_USERNAME=your_name \
    DATABASE_PASSWORD=your_password

# Set the working directory to /app
WORKDIR /app

# Copy the .jar file into the container
COPY target/account-service.jar /app/

# Expose the port
EXPOSE 8083

# Run the command to start the Spring Boot application
CMD ["java", "-jar", "account-service.jar"]