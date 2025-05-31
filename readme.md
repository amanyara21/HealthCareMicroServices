
# HealthCareMicroservices

This repository contains the **microservices-based architecture** for the HealthCare system. It is designed to scale various modules independently while offering the same functionality as the monolithic version with improved modularity, maintainability, and scalability.

> 💡 You can also check out the [HealthCareBackend (Monolithic Version)](https://github.com/amanyara21/HealthCareBackend) for a simpler, all-in-one implementation.
---

## 🏗️ Technologies Used

- Java 19
- Spring Boot
- Spring Cloud (Eureka, Config Server, Gateway)
- Spring Data JPA
- Spring Security + JWT
- Gradle (build tool)
- RESTful APIs
- MySQL

---

## 🧩 Microservices Overview

### 1. **API Gateway**
- Entry point for all clients

### 2. **Service Registry (Eureka Server)**
- Service discovery for all microservices

### 3. **User Service**
- Manages users (patients), registration, login
- Handles authentication and JWT token generation

### 4. **Doctor Service**
- Manages doctor profiles, availability, and appointments

### 5. **Appointment Service**
- Handles booking and managing online/offline appointments

### 6. **Prescription Service**
- Stores and manages test reports, prescriptions, and medicine data

### 7. **Cloudinary Service**
- Upload Image and PDF in the cloudinary.

### 8. **Body Part Service**
- Add Body Parts for the doctor

---

## 👤 User Roles & Features

### 🔹 User
- Register/Login
- Browse doctors
- Book appointments (WebRTC-enabled)
- View prescriptions, health data, and test reports

### 🔹 Doctor
- View appointments
- Add test reports and medicines
- Mark unavailable days

### 🔹 Admin
- Add/manage doctors
- Add body parts used in report classification

---

## 🚀 Getting Started

### ✅ Prerequisites

- Java 19
- Gradle
- MySQL

---

## 🧪 How to Run

> You can run each service individually, but a recommended setup is to start them in this order:


1. **Eureka Server**

```bash
cd eureka-server
./gradlew bootRun
```

2**API Gateway**

```bash
cd api-gateway
./gradlew bootRun
```

3**Core Microservices (in any order after above)**

```bash
cd userservice
./gradlew bootRun

cd doctorservice
./gradlew bootRun

cd appointmentservice
./gradlew bootRun

cd prescriptionservice
./gradlew bootRun

cd bodypartservice
./gradlew bootRun
```

---

### ⚙️ Configuration

Each microservice should have its own `application.properties` file with the following base settings:

**Example `application.properties`:**

```properties
server.port=8081

spring.application.name=userservice

spring.datasource.url=jdbc:mysql://localhost:3306/healthcare_users
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect

eureka.client.service-url.defaultZone=http://localhost:8761/eureka
```

> 🔧 Update `application.properties` in each service according to:

* `spring.application.name`
* `server.port`
* `spring.datasource.url`

Make sure all services register correctly with **Eureka** and connect to their appropriate **MySQL** database schema.

---




---

## 🔐 Security

* All services use **JWT-based authentication** via the API Gateway.
* Protected endpoints require `Authorization: Bearer <token>` header.

---

## 📬 Sample Endpoints

| Microservice          | Endpoint                | Method | Description               |
| --------------------- | ----------------------- | ------ | ------------------------- |
| User Service          | `/users/register`       | POST   | Register a new user       |
| Doctor Service        | `/doctors/availability` | GET    | Check doctor availability |
| Appointment Service   | `/appointments/book`    | POST   | Book an appointment       |
| Health Record Service | `/records/user/{id}`    | GET    | Get all health records    |
| Admin Service         | `/admin/add-doctor`     | POST   | Add new doctor            |

---




## 🤝 Contributing

Feel free to open issues or submit PRs. Let's build a better health tech ecosystem together!

---


