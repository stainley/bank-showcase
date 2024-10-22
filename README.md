# 🌐 Bank Showcase Project

![Java](https://img.shields.io/badge/Java-21-orange?style=flat-square&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.3.4-brightgreen?style=flat-square&logo=spring-boot)
![Maven](https://img.shields.io/badge/Maven-3.8.1-C71A36?style=flat-square&logo=apache-maven)
![SonarQube](https://img.shields.io/badge/SonarQube-Quality_Gate-blue?style=flat-square&logo=sonarqube)

## 📄 Project Overview

The **Bank Showcase Project** is a microservices-based architecture built with Spring Boot and Java. It demonstrates
core banking services, including a **Config Server**, **Discovery Server**, **Gateway Server**, and multiple
microservices for users, accounts, and transactions. The project adheres to modern software engineering practices, such
as reactive microservices, event sourcing, CQRS, and asynchronous messaging with Kafka.

This project serves as a showcase for Bank, demonstrating capabilities in building enterprise-level solutions with
scalability, security, and performance in mind.

---

## 🛠️ Tech Stack

- **Java 21** - Core language for all services
- **Spring Boot 3.3.4** - Framework for building microservices
- **Spring Cloud** - Service Discovery, Config, and Gateway
- **Kafka** - Messaging broker for event-driven architecture
- **Docker** - Containerization for consistent environments
- **Kubernetes** - Orchestration of services (optional)
- **SonarQube** - Static code analysis and quality checks
- **JUnit, Mockito** - Unit and integration testing
- **OpenTelemetry, Prometheus** - Monitoring and observability

---

## 📚 Key Features

- **RESTful API Design**: Provides RESTful endpoints for all core banking services.
- **Reactive Microservices**: Implements reactive programming for non-blocking I/O operations using Spring WebFlux.
- **Event Sourcing & CQRS**: Separates reads and writes with CQRS and event sourcing for consistency and scalability.
- **Asynchronous Messaging**: Utilizes Kafka for distributed messaging and real-time data streaming.
- **Centralized Configuration**: Manages configuration across microservices with Spring Cloud Config Server.
- **Service Discovery**: Automatically discovers microservices using Eureka or Spring Cloud Discovery.
- **API Gateway**: Routes and secures requests through a single entry point with Spring Cloud Gateway.

---

## 🚀 Getting Started

### Prerequisites

To get started with this project, you need the following installed on your machine:

- **Java 21**
- **Maven 3.8.1+**
- **Docker** (for containerization)
- **SonarQube** (for code quality checks)
- **Kafka** (for messaging services)

### Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/yourusername/bank-showcase.git
   cd bank-showcase

2. **Build the project**:
    ```bash
   mvn clean install

3. **Run the services**: Use Docker Compose or Kubernetes to orchestrate the services:
    ```bash
   docker-compose up

4. **Run SonarQube analysis**: Ensure SonarQube is running and then run the following command:
    ```bash
   mvn sonar:sonar

---

## 📂 Project Structure

- ```yaml
    bank-showcase/
        │
        ├── config-server/             # Centralized configuration service
        ├── discovery-server/          # Eureka or Spring Cloud Discovery server
        ├── gateway-server/            # API Gateway service
        ├── user-service/              # Microservice handling user-related operations
        ├── account-service/           # Microservice for managing bank accounts
        ├── transaction-service/       # Microservice for handling transactions
        │
        ├── pom.xml                    # Parent Maven configuration
        └── README.md                  # Project documentation

---

## 🔧 Configuration

- Ensure that the `application.properties` in each service is properly configured. For example:
   ```properties
         # Config Server (config-server)
         server.port=8888
         spring.cloud.config.server.git.uri=https://github.com/your-config-repo

---

## 📊 SonarQube Integration

* The project uses **SonarQube** for static code analysis. To configure SonarQube, you can set the following properties
  in your `pom.xml`:
    ```xml
    <properties>
      <sonar.host.url>http://localhost:9000</sonar.host.url>
      <sonar.login>${env.SONAR_LOGIN}</sonar.login>
      <sonar.projectKey>bank-showcase</sonar.projectKey>
    </properties>

You can replace the `sonar.login` with your SonarQube token or use environment variables.

---

## 🧪 Running Tests

- Run unit tests with Maven:

    ```bash
    mvn test

- For integration tests, ensure all services are up and running:

    ```bash
    mvn verify

---

## 📈 Monitoring & Observability

This project includes support for monitoring with OpenTelemetry and Prometheus. You can configure Prometheus to scrape
metrics from your services.

---

## 🧑‍💻 Contribution Guidelines

We welcome contributions! Please submit a pull request or file an issue if you find a bug or have a feature request.

---

## 📜 License

This project is licensed under the MIT License - see the LICENSE file for details.

---

## 📞 Contact

For more information, please contact **Stainley Lebron**
at [stainley.lebron@gmail.com](mailto:stainley.lebron@gmail.com).