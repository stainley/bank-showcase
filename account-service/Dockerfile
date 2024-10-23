# Stage 1 Buid
FROM maven:3.9.4-eclipse-temurin-21-alpine AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml and source code to the container
COPY pom.xml /app/
COPY src /app/src

# Build the application and package it as a JAR file
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:21-jre-jammy

# Set environment variables for the database connection
ENV DATABASE_URL=${DATABASE_URL}
ENV DATABASE_USERNAME=${DATABASE_USERNAME}
ENV DATABASE_PASSWORD=${DATABASE_PASSWORD}
ENV DATABASE_NAME=${DATABASE_NAME}

# Set the working directory to /app
WORKDIR /app

# Copy the .jar file from the build stage
COPY --from=builder /app/target/account-service-*.jar /app/account-service.jar

# Expose the port
EXPOSE 8083

# Run the Spring Boot application
CMD ["java", "-jar", "/app/account-service.jar"]