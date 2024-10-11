# Monitoring Module

This module provides monitoring and observability for the TD Bank Showcase microservices using OpenTelemetry for tracing
and Prometheus for metrics. Grafana is used for visualizing these metrics.

## Overview

- **OpenTelemetry**: Used for distributed tracing and exporting spans to the OpenTelemetry collector.
- **Prometheus**: Scrapes metrics exposed by Spring Boot Actuator.
- **Grafana**: Provides a visual interface for monitoring the metrics.

## Services Monitored

- `user-service`
- `account-service`
- `transaction-service`
- `monitoring-service`

## Setup

### Docker

- To run Prometheus and Grafana locally along with the monitoring service, use the following Docker Compose command:

    ```bash
    docker-compose up --build

This will start the following services:

- `monitoring-service`: A microservice that exposes tracing and metrics.
- `Prometheus`: Scrapes metrics from all microservices and provides a queryable interface.
- `Grafana`: Visualizes the metrics collected by Prometheus.

## Prometheus

Prometheus can be accessed at `http://localhost:9090`.

## Grafana

Grafana can be accessed at `http://localhost:3000` with the default username `admin` and password `admin`. After logging
in, you should see the pre-configured dashboard for Spring Boot microservices.

## Endpoints

- **Prometheus Metrics**: Available at `/actuator/prometheus`
- **Health Check**: Available at `/actuator/health`
- **Distributed Tracing**: Configured via OpenTelemetry, spans are sent to the collector.

## Grafana Dashboards

You can view the dashboards for microservice metrics (e.g., CPU usage, memory, HTTP requests, etc.) in Grafana. The
dashboards are automatically provisioned in Grafana when it starts.

