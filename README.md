
#  🚚 Drive and Deliver API

## Overview
   drive-and-deliver-api

## Table of Contents
- [Introduction](#introduction)
- [Technologies Used](#technologies-used)
- [Prerequisites](#prerequisites)
- [Environment Setup](#environment-setup)
- [Installation](#installation)
- [Running the application](#running-the-application)
- [Docker](#docker)
- [Usage](#usage)
- [Testing](#testing)
- [API Documentation](#api-documentation)

## Introduction

This project is a Spring Boot application that allows customers to choose their delivery method and schedule their delivery time slots.
- **Delivery Methods** : Customers can choose from the following delivery methods

   -  ```DRIVE ```
   -  ```DELIVERY ```
   -  ```DELIVERY_TODAY ```
   -  ```DELIVERY_ASAP ```
- **Time Slot Selection** : Customers can select a specific day and time slot for their delivery. Time slots are unique to each delivery method and can be booked by different customers.


## Technologies Used

- Java 21
- Spring Boot 3.3.4
- Maven
- Spring Data JPA
- Spring Security (JWT)
- HATEOAS
- Swagger/OpenAPI 3
- Spring Cache
- Spring event publishing
- H2 for database
- JUnit and MockMvc for testing

## Prerequisites

- Java 21 or above
- Maven 3.8+
- H2 in-memory DB


## Environment Setup

Ensure Java and Maven are installed:
   ```bash
   java -version
   mvn -version
   ```

## Installation
1. **Clone the repository:**
   ```bash
   git clone https://github.com/abdellah85/deliver-service-api
   cd deliver-service-api
   ```
2. **Install dependencies:**
   ```bash
   mvn clean install
    ```
## Running the application

To run the application, use the following command :
  ```bash
    mvn spring-boot:run
 ```
You can access the application at   http://localhost:8080.

## Docker

**Building the Docker Image**

The project can be containerized using Docker. 

The Dockerfile is located in the ```drive-and-deliver-api``` folder. To build the Docker image, follow these steps:

1. Navigate to the drive-and-deliver-api directory:
   ```bash
   cd drive-and-deliver-api
   ```

2. Build the Docker image using the following command:
   ```bash
   docker build -t drive-and-deliver .
   ```

**Running the Docker Container**

Once the image is built, you can run the application in a Docker container:

```bash
docker run -p 8080:8080 drive-and-deliver
```

This command maps port 8080 on your local machine to port 8080 in the container, allowing you to access the application at http://localhost:8080.

## Usage
After running the application, you can interact with the following principal endpoints:

- **Customer Registration :** ```POST /users/register ```
- **Login :** ```POST /auth/login ```
- **Add a time slot:** ```POST /time-slots```
- **Get delivery methods :** ```GET /deliveries/methods```
- **Get available time slots :** ```GET /time-slots/available?method={deliveryMethod}```
- **Book a delivery :** ```POST /deliveries```

## Testing

To run tests:
   ```bash
   mvn test
   ```

## API Documentation
This project includes Swagger for API documentation. Once the application is running, you can access it at:

- Swagger UI: http://localhost:8080/swagger-ui/index.html
- OpenAPI Specification: http://localhost:8080/v3/api-docs