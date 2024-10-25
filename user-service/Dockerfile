# Stage 1: Build
FROM maven:3.9.4-eclipse-temurin-21 AS builder

WORKDIR /app
COPY pom.xml .
COPY src src
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM openjdk:21-slim

# Create non-root user and group
RUN groupadd -r appgroup && useradd -r -g appgroup -m -d /app -s /sbin/nologin appuser

# Switch to non-root user
USER appuser

WORKDIR /app

# Copy JAR file from build stage
COPY --from=builder /app/target/user-service-0.0.1-SNAPSHOT.jar user-service.jar

EXPOSE 8081

# Set Java security flags
ENV JAVA_OPTS "-Djava.security.manager -Djava.security.egd=file:/dev/./urandom"

# Run Spring Boot application
ENTRYPOINT ["java", "${JAVA_OPTS}", "-jar", "user-service.jar"]