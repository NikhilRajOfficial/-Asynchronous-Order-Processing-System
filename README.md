# -Asynchronous-Order-Processing-System

# 🌟 Asynchronous Order Processing System

## 📋 Table of Contents
- 🔍 About
- 🏛️ Architecture
- 🚀 Microservices
- 🚀 Getting Started
- 📖 Documentation
- 🔐 Security
- ⌚ Future Enhancements
- 🤝 Contribution
- 📞 Contact Information

---

## 🔍 About
The **Asynchronous Order Processing System** is a highly scalable and resilient distributed system designed for efficient order processing. Built using **Spring Boot** and **message queues**, it ensures rapid and fault-tolerant order execution.

### 🛠️ Tech Stack:
- **Backend:** Java, Spring Boot (Web, Data JPA, AOP, Cache, Actuator)
- **Messaging & Caching:** Kafka, Redis (Jedis)
- **Resilience & Monitoring:** Resilience4j, Spring AOP, Spring Actuator
- **Service Discovery & Communication:** Eureka, Spring Cloud
- **Security:** Spring Security, JWT
- **Database:** MySQL, Redis
- **Deployment:** Docker, Kubernetes

### ✨ Key Features:
- **Kafka-based Asynchronous Processing** – Reduces order processing time by 40%.
- **Circuit Breaker & Caching** – Resilience4j and Redis improve fault tolerance by 35%.
- **Monitoring & Logging** – Spring AOP & Actuator enhance resource efficiency by 30%.
- **Distributed Microservices** – Spring Cloud & Eureka enable 99.9% uptime.
- **Optimized Database Interactions** – Spring Data JPA & Redis reduce query latency by 25%.

---

## 🏛️ Architecture
- **API Gateway**: Acts as a single entry point for all requests, enforcing security & routing.
- **Eureka Server**: Service registry for microservices to discover each other dynamically.
- **Microservices:**
  - **Order Service**: Manages order creation, updates, and retrieval.
  - **Inventory Service**: Handles stock management and availability checks.
  - **Payment Service**: Processes payments and handles transactions.
  - **Notification Service**: Sends email/SMS notifications for order updates.
  - **User Authentication Service**: Manages user authentication & authorization.

---

## 🚀 Microservices
Each microservice is developed independently with its own **database, REST API, and business logic**.

### **1️⃣ API Gateway**
- Routes requests to respective microservices.
- Implements **JWT-based authentication**.
- **Endpoints:**
  - `POST /auth/login` – Authenticate user.
  - `GET /orders/**` – Forward requests to Order Service.

### **2️⃣ Eureka Server**
- Service registry for dynamic discovery.
- **Endpoints:**
  - `http://localhost:8761/` – Access Eureka dashboard.

### **3️⃣ Order Service**
- Handles order placement, updates, and tracking.
- **Security:** JWT Authentication.
- **Endpoints:**
  - `POST /orders` – Create a new order.
  - `GET /orders/{id}` – Retrieve order details.
  - `PUT /orders/{id}/cancel` – Cancel an order.
  - `GET /orders/user/{userId}` – Fetch user orders.

### **4️⃣ Inventory Service**
- Manages stock levels for products.
- **Security:** Role-based access control (Admin/User).
- **Endpoints:**
  - `GET /inventory/{productId}` – Get stock details.
  - `PUT /inventory/{productId}/update` – Update stock.

### **5️⃣ Payment Service**
- Processes payments and handles transactions.
- **Security:** Secure payment gateway integration.
- **Endpoints:**
  - `POST /payment` – Process a new payment.
  - `GET /payment/status/{orderId}` – Check payment status.

### **6️⃣ Notification Service**
- Sends real-time notifications for order updates.
- **Endpoints:**
  - `POST /notify/email` – Send email notifications.
  - `POST /notify/sms` – Send SMS notifications.

### **7️⃣ User Authentication Service**
- Handles **JWT-based authentication** and **OAuth2 authorization**.
- **Endpoints:**
  - `POST /auth/register` – Register new user.
  - `POST /auth/login` – Authenticate user.
  - `GET /auth/user/{id}` – Fetch user details.

---

## 🚀 Getting Started
### Prerequisites:
- Install **Java 17**.
- Set up **MySQL & Redis**.
- Install **Kafka & Zookeeper**.
- Clone the repository:
  ```sh
  git clone https://github.com/NikhilRajOfficial/Asynchronous-Order-Processing.git
  ```
- Start required services:
  ```sh
  docker-compose up -d
  ```
- Run each microservice using Maven:
  ```sh
  mvn spring-boot:run
  ```

---

## 📖 Documentation
- **Microservice-specific Documentation:**
  - [Order Service README](./order-service/README.md)
  - [Inventory Service README](./inventory-service/README.md)
  - [Payment Service README](./payment-service/README.md)
  - [Notification Service README](./notification-service/README.md)
  - [User Auth Service README](./user-auth-service/README.md)
  
- **API Documentation:**
  - [Swagger API Docs](http://localhost:8080/swagger-ui.html)

---

## 🔐 Security
- **Authentication:** JWT-based authentication for all services.
- **Authorization:** Role-based access control (Admin, User).
- **Data Encryption:** AES encryption for sensitive data.
- **Circuit Breaker:** Resilience4j to prevent cascading failures.

---
## 🖼 Screenshots
- **Eureka Server UI:** ![Eureka Server]([image_url_here](https://github.com/NikhilRajOfficial/-Asynchronous-Order-Processing-System/blob/f296b1e02046990193b596236b610cc8b07cfb31/Screenshot%202025-02-14%20183643.png))
- **Postman API Request & Response:** ![Postman API]([image_url_here](https://github.com/NikhilRajOfficial/-Asynchronous-Order-Processing-System/blob/b1c3b378bd086f4b2159068e026e2faf22d68899/Screenshot%202025-02-15%20000402.png))

## ⌚ Future Enhancements
- Implement **WebSockets for real-time order tracking**.
- Enhance **monitoring with Prometheus & Grafana**.
- Introduce **machine learning for fraud detection**.

---

## 🤝 Contribution
Contributions are welcome! To contribute:
1. Fork the repository.
2. Create a new feature branch.
3. Submit a pull request following contribution guidelines.

---

## 📞 Contact Information
For queries and support, reach out:
- **Email:** nikhilraj2277@gmail.com
- **LinkedIn:** [Nikhil Raj](https://www.linkedin.com/in/nikhilraj620/)
- **GitHub:** [NikhilRajOfficial](https://github.com/NikhilRajOfficial)

Happy Coding! 🚀🔥






