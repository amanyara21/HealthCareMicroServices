
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

- Java 17+
- Gradle
- MySQL/PostgreSQL
- Redis (optional)
- Postman or Swagger for API testing
- Docker (optional for containerized setup)

---

## 🧪 How to Run

> You can run each service individually, but a recommended setup is to start them in this order:

1. **Config Server**
```bash
cd config-server
./gradlew bootRun
````

2. **Eureka Server**

```bash
cd eureka-server
./gradlew bootRun
```

3. **API Gateway**

```bash
cd api-gateway
./gradlew bootRun
```

4. **Core Microservices (in any order after above)**

```bash
cd user-service
./gradlew bootRun

cd doctor-service
./gradlew bootRun

cd appointment-service
./gradlew bootRun

cd health-record-service
./gradlew bootRun

cd admin-service
./gradlew bootRun
```

---

## ⚙️ Configuration

Each microservice fetches configuration from the **Config Server**, which pulls from a Git-based config repo.

Ensure your `application.yml` in each service points to:

```yaml
spring:
  config:
    import: optional:configserver:http://localhost:8888
```

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


