# common/Dockerfile
FROM maven:3.9.4-eclipse-temurin-21-alpine

WORKDIR /app

COPY pom.xml .
COPY src src

RUN mvn clean install
