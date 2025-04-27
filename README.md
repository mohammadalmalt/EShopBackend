# E-commerce Backend API

[![Build Status](https://img.shields.io/badge/build-passing-brightgreen)](https://shields.io/)
[![License](https://img.shields.io/badge/license-MIT-blue)](https://opensource.org/licenses/MIT)
[![Made with](https://img.shields.io/badge/Made%20with-Java%20Spring%20Boot-ff69b4.svg)](https://spring.io/projects/spring-boot)

A **Spring Boot**-based RESTful API for an e-commerce platform with user authentication, product management, and order processing.

---

## ✨ Features

- User registration and login with JWT-based authentication
- Product browsing, searching, and filtering
- Shopping cart and order management
- Admin capabilities for product CRUD operations
- Swagger/OpenAPI documentation
- H2 in-memory database
- Unit and integration tests with JUnit, MockMvc, and Cucumber

---

## 🛠 Tech Stack

- Spring Boot 3.3.4
- Spring Security with JWT
- Spring Data JPA
- H2 Database
- ModelMapper for DTO mapping
- Swagger/OpenAPI for API documentation
- JUnit 5 and Cucumber for testing

---

## 🚀 Setup

1. **Clone the repository:**

   ```bash
   git clone <repository-url>
   ```
2. **Navigate to the project directory:**
    ```bash
    cd ecommerce-backend
   ```
3. **Build and run the application:**
    ```bash
   mvn spring-boot:run
   ```
4. **Access the API**:
    ```bash
   - [Swagger UI](http://localhost:8080/swagger-ui.html)
   - [H2 Console](http://localhost:8080/h2-console)

      ```
## 📚 API Endpoints

| Category | Method | Endpoint | Description |
|:---------|:-------|:---------|:------------|
| Auth     | POST   | `/api/auth/register` | Register a new user |
| Auth     | POST   | `/api/auth/login` | Login and receive JWT token |
| Products | GET    | `/api/products` | Get all products |
| Products | POST   | `/api/products` | Create a new product (Admin) |
| Products | GET    | `/api/products/{id}` | Get product by ID |
| Products | PUT    | `/api/products/{id}` | Update product by ID (Admin) |
| Products | DELETE | `/api/products/{id}` | Delete product by ID (Admin) |
| Orders   | GET    | `/api/orders` | Get all orders |
| Orders   | POST   | `/api/orders` | Create a new order |
| Orders   | GET    | `/api/orders/{id}` | Get order by ID |
| Orders   | PUT    | `/api/orders/{id}/status` | Update order status (Admin) |

---

## 🧪 Testing

Run all tests with:

```bash
mvn test
```
## ⚡ Notes

> - Replace `your-256-bit-secret-key-for-jwt-signing` in `application.yml` with a secure key.
> - Product image uploads are simulated using `imageUrl` fields.
> - The application uses an in-memory H2 database for development purposes.

Made with ❤️ by Mohammad Almalt 😎
