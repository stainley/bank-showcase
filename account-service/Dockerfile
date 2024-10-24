# Stage 1: Build
FROM maven:3.9.4-eclipse-temurin-21-alpine AS builder

WORKDIR /app
COPY pom.xml /app/
COPY src /app/src
COPY --from=common-library /app/target/common-1.0.0.jar /app/libs/common-1.0.0.jar

RUN mvn clean package -DskipTests -Dmaven.repo.local=/app/libs


# Stage 2: Runtime
FROM eclipse-temurin:21-jre-jammy

# Create non-root user and group
RUN groupadd -r appgroup && useradd -r -g appgroup -m -d /app -s /sbin/nologin appuser

# Switch to non-root user
USER appuser

# Set environment variables (replace with secure methods)
ENV DATABASE_URL=${DATABASE_URL}
ENV DATABASE_USERNAME=${DATABASE_USERNAME}
ENV DATABASE_NAME=${DATABASE_NAME}

WORKDIR /app
COPY --from=builder /app/target/account-service-*.jar /app/account-service.jar

EXPOSE 8083

CMD ["java", "-jar", "/app/account-service.jar"]