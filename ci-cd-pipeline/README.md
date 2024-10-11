# CI/CD Pipeline

This directory contains the configuration files for the Continuous Integration/Continuous Deployment (CI/CD) pipeline
for the TD Bank Showcase project.

## Overview

The pipeline is designed to automate the following tasks:

- Code checkout
- Building the project using Maven
- Running unit tests
- Code analysis with SonarQube
- Building Docker images for the microservices
- Pushing Docker images to DockerHub
- (Optional) Deploying to Kubernetes

## Available Pipelines

### Jenkins Pipeline

The Jenkins pipeline (`Jenkinsfile`) automates the entire CI/CD process from build to deployment. You can configure it
by adding it to your Jenkins server.

#### Setup

- Install Jenkins on your server and configure the necessary plugins (Maven, SonarQube, Docker).
- Add the SonarQube and Docker credentials in Jenkins under "Manage Jenkins" > "Credentials".
- Set up the `DOCKER_USERNAME` and `DOCKER_PASSWORD` environment variables.

### GitHub Actions

The GitHub Actions workflow (`github-actions.yml`) provides an alternative CI/CD pipeline that runs directly on GitHub.

#### Setup

- Add the following secrets to your GitHub repository:
    - `SONARQUBE_HOST_URL`
    - `SONARQUBE_LOGIN`
    - `DOCKER_USERNAME`
    - `DOCKER_PASSWORD`

- GitHub Actions will automatically trigger the pipeline when code is pushed to the `main` branch.

## Docker Integration

Both the Jenkins and GitHub Actions pipelines build Docker images for the microservices and push them to DockerHub.

### Building Images Locally

- If you want to build and test the Docker images locally, you can use the following commands:

    ```bash
    docker build -t your-dockerhub-repo/user-service ./user-service
    docker build -t your-dockerhub-repo/account-service ./account-service
    docker build -t your-dockerhub-repo/transaction-service ./transaction-service

- Then push the images:

    ```bash
    docker push your-dockerhub-repo/user-service
    docker push your-dockerhub-repo/account-service
    docker push your-dockerhub-repo/transaction-service

### Kubernetes Deployment (Optional)

- If you want to deploy the services to a Kubernetes cluster, you can use the sample `k8s/deployment.yaml` file in the
  project. Update the image URLs to match your DockerHub repository and apply the deployment configuration:
    ```bash
    kubectl apply -f k8s/deployment.yaml
