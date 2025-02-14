# -Asynchronous-Order-Processing-System

## 📌 Description
Developed a highly scalable and resilient asynchronous order processing system using Spring Boot and message queues. The system efficiently handles order placement, payment processing, inventory updates, and notifications asynchronously using Kafka , reducing user wait time and improving system performance.

## 🛠 Tech Stack
- **🖥 Programming Language:** Java
- **⚙ Frameworks & Libraries:** Spring Boot (Web, Data JPA, AOP, Cache, Actuator)
- **📩 Event-Driven Communication:** Kafka
- **🗄 Database:** MySQL (Relational Database for order, payment, and inventory management)
- **⚡ Caching:** Redis (Jedis) for improved response time
- **🔒 Authentication & Security:** Spring Security (JWT-based authentication)
- **🌐 Service Discovery & Load Balancing:** Spring Cloud, Eureka
- **🚪 API Gateway:** Spring Cloud Gateway (Routing & Security)
- **🛡 Resilience & Fault Tolerance:** Resilience4j (Circuit Breaking)
- **📊 Monitoring & Metrics:** Prometheus & Grafana
- **🛠 Utilities:** Lombok (for reducing boilerplate code)

## 🏗 System Architecture

### 🏢 Microservices Architecture
- **📦 Order Service** → Handles order creation and publishes events to Kafka/RabbitMQ.
- **💳 Payment Service** → Listens to order events and processes payments asynchronously.
- **📦 Inventory Service** → Updates stock levels and reserves items for orders.
- **📨 Notification Service** → Sends real-time order status updates to users.
- **🚪 API Gateway** → Routes requests securely to appropriate services.
- **🔐 Auth Service** → Implements user authentication with JWT.

### 🔄 Asynchronous Processing & Event-Driven Communication
- Orders are processed in the background using Kafka.
- Payment & Inventory services subscribe to order events to handle tasks independently.
- Redis is used to cache order and payment status for quick retrieval.
- Resilience4j is integrated for circuit breaking, improving system fault tolerance.
- Implemented Spring Retry for automatic failure handling in case of transient issues.

## 🚀 How to Run the Project

### 📌 Prerequisites
Ensure you have the following installed:
- Java 17+
- Apache Kafka & Zookeeper
- Redis Server
- MySQL Server
- Docker (optional for containerized deployment)

### 🏁 Running Kafka
```sh
# Start Zookeeper (Required for Kafka)
zookeeper-server-start.bat config/zookeeper.properties &

# Start Kafka Broker
kafka-server-start.bat config/server.properties &

# Create Kafka Topics
kafka-topics.sh --create --topic order-topic --bootstrap-server localhost:9092 --replication-factor 1 --partitions 3
kafka-topics.sh --create --topic payment-topic --bootstrap-server localhost:9092 --replication-factor 1 --partitions 3
kafka-topics.sh --create --topic notification-topic --bootstrap-server localhost:9092 --replication-factor 1 --partitions 3
```

### 🔥 Running Redis
```sh
# Start Redis Server
redis-server

# Verify Redis is running
redis-cli ping
```


### ▶ Running the Spring Boot Application
```sh
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

## 🖼 Screenshots
- **Eureka Server UI:** ![Eureka Server]([image_url_here](https://github.com/NikhilRajOfficial/-Asynchronous-Order-Processing-System/blob/f296b1e02046990193b596236b610cc8b07cfb31/Screenshot%202025-02-14%20183643.png))
- **Postman API Request & Response:** ![Postman API]([image_url_here](https://github.com/NikhilRajOfficial/-Asynchronous-Order-Processing-System/blob/b1c3b378bd086f4b2159068e026e2faf22d68899/Screenshot%202025-02-15%20000402.png))

_(Replace `image_url_here` with actual image URLs when uploading to GitHub)_

## 📈 Future Improvements
- Implement real-time WebSockets for live order tracking.
- Introduce Kubernetes for better container orchestration.
- Add AI-based demand forecasting for inventory management.

## 👨‍💻 Contributors
- **Nikhil Raj** (GitHub: [NikhilRajOfficial](https://github.com/NikhilRajOfficial))




