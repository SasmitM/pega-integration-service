# Pega Integration Service

A Micronaut-based Kotlin microservice for processing and managing complaint integrations with external systems.

## Overview

This service provides a REST API for submitting and processing complaints. It integrates with external CRM systems and provides monitoring capabilities through Prometheus and Grafana.

## Features

- RESTful API for complaint submission
- External API integration with timeout handling
- Prometheus metrics collection
- Health check endpoints
- Docker and Kubernetes support
- Coroutine-based asynchronous processing

## Technology Stack

- Kotlin 1.9.22
- Micronaut 4.2.1
- Java 21
- Gradle 8.x
- Prometheus
- Grafana

## Prerequisites

- Java 21 or higher
- Gradle 8.x (or use the included Gradle wrapper)

## Building the Project

```bash
./gradlew build
```

To create a shadow JAR:

```bash
./gradlew shadowJar
```

## Running the Application

### Local Development

```bash
./gradlew run
```

The service will start on port 8080.

### Using Docker

Build the Docker image:

```bash
docker build -t pega-integration-service .
```

Run the container:

```bash
docker run -p 8080:8080 pega-integration-service
```

### Using Docker Compose

Start all services (application, Prometheus, Grafana):

```bash
docker-compose up
```

This will start:
- Integration service on port 8080
- Prometheus on port 9090
- Grafana on port 3000 (admin/admin)

## API Endpoints

### Submit Complaint

```
POST /integrations/complaints
Content-Type: application/json
```

Request body:
```json
{
  "customerId": "string",
  "complaintType": "string",
  "priority": "string",
  "description": "string"
}
```

### Health Check

```
GET /health
```

### Metrics

```
GET /metrics
GET /prometheus
```

## Configuration

Configuration is managed through `application.yml`. Key settings:

- Server port: 8080
- External API base URL: configurable via `external-api.base-url`
- Request timeout: configurable via `external-api.timeout`
- Metrics collection interval: 15 seconds

## Testing

Run tests:

```bash
./gradlew test
```

## Deployment

### Kubernetes

Kubernetes deployment files are provided in the `k8s/` directory:

```bash
kubectl apply -f k8s/
```

This includes:
- Namespace configuration
- Deployment manifest
- Service configuration
- Prometheus and Grafana deployments

## Monitoring

The application exposes metrics at `/prometheus` for Prometheus scraping. Grafana dashboards are provisioned in `grafana/provisioning/dashboards/`.

Access Grafana:
- URL: http://localhost:3000
- Username: admin
- Password: admin

## Project Structure

```
src/main/kotlin/com/pega/integration/
├── Application.kt              # Application entry point
├── client/                     # External API clients
├── controller/                 # REST controllers
├── service/                    # Business logic
├── model/                      # Data models and DTOs
├── metrics/                    # Metrics collection
└── exception/                  # Exception handling
```

## License

This is a demo project for Pega integration.
