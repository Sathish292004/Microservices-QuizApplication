# 🧠 Microservices Quiz Application

A fully backend **Quiz Application** built with **Java Spring Boot** following a **Microservices Architecture**. The application is decomposed into independently deployable services that communicate via REST, are discovered through a Eureka Service Registry, and are accessed through an API Gateway.

---

## 📐 Architecture Overview

```
Client
  │
  ▼
┌──────────────────────┐
│      API Gateway      │  ← Port 8765
│  (Spring Cloud GW)   │
└──────────┬───────────┘
           │  Routes requests
    ┌──────┴──────┐
    ▼             ▼
┌─────────────┐  ┌─────────────┐
│  Quiz       │  │  Question   │
│  Service    │  │  Service    │
│  (Port 8090)│  │  (Port 8080)│
└──────┬──────┘  └──────┬──────┘
       │  Feign Client  │
       └────────────────┘
           ▲       ▲
           │       │
    ┌──────┴───────┴──────┐
    │    Service Registry  │  ← Port 8761 (Eureka)
    └─────────────────────┘
```

---

## 🗂️ Project Structure

```
Microservices-QuizApplication/
├── GateWay/             # API Gateway (Spring Cloud Gateway)
├── QuestionService/     # Manages quiz questions (CRUD)
├── QuizService/         # Manages quizzes, generation, and scoring
└── Service-registry/    # Eureka Service Discovery Server
```

---

## 🛠️ Tech Stack

| Technology               | Purpose                              |
|--------------------------|--------------------------------------|
| Java 17+                 | Core language                        |
| Spring Boot              | Microservices framework              |
| Spring Cloud Gateway     | API Gateway and request routing      |
| Spring Cloud Netflix Eureka | Service Discovery & Registration  |
| Spring Data JPA          | Data persistence / ORM               |
| OpenFeign                | Inter-service HTTP communication     |
| PostgreSQL / MySQL       | Relational database                  |
| Maven                    | Build tool                           |
| Postman                  | API testing                          |

---

## 📦 Services

### 1. 🔍 Service Registry (`/Service-registry`) — Port `8761`
The **Eureka Server** acts as the service registry. All other services register themselves here, enabling dynamic service discovery without hardcoded URLs.

- Built with `spring-cloud-starter-netflix-eureka-server`
- Dashboard available at: `http://localhost:8761`

---

### 2. ❓ Question Service (`/QuestionService`) — Port `8080`
Responsible for all **CRUD operations** on quiz questions. Each question has a category, difficulty level, and multiple-choice options.

**Key Endpoints:**

| Method | Endpoint                          | Description                     |
|--------|-----------------------------------|---------------------------------|
| GET    | `/question/allQuestions`          | Fetch all questions             |
| GET    | `/question/category/{category}`   | Fetch questions by category     |
| POST   | `/question/add`                   | Add a new question              |
| DELETE | `/question/delete/{id}`           | Delete a question by ID         |
| GET    | `/question/generate`              | Generate questions for a quiz   |
| POST   | `/question/getScore`              | Calculate score from responses  |

---

### 3. 📝 Quiz Service (`/QuizService`) — Port `8090`
Orchestrates quiz creation and scoring by calling the **Question Service** via **Feign Client**.

**Key Endpoints:**

| Method | Endpoint                  | Description                        |
|--------|---------------------------|------------------------------------|
| POST   | `/quiz/create`            | Create a quiz (category + limit)   |
| GET    | `/quiz/get/{id}`          | Get quiz questions by quiz ID      |
| POST   | `/quiz/submit/{id}`       | Submit answers and get score       |

---

### 4. 🌐 API Gateway (`/GateWay`) — Port `8765`
The single entry point for all client requests. Routes incoming requests to the appropriate microservice based on path predicates.

**Route Configuration:**
```
/question-service/**  →  Question Service
/quiz-service/**      →  Quiz Service
```

---

## 🚀 Getting Started

### Prerequisites
- Java 17+
- Maven 3.6+
- PostgreSQL or MySQL running locally
- Git

### Setup & Run

> **Important:** Services must be started in this exact order.

#### Step 1 — Clone the Repository
```bash
git clone https://github.com/Sathish292004/Microservices-QuizApplication.git
cd Microservices-QuizApplication
```

#### Step 2 — Configure the Database
In both `QuestionService` and `QuizService`, update `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/quizdb
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

#### Step 3 — Start Service Registry (Eureka)
```bash
cd Service-registry
mvn spring-boot:run
```
Visit: `http://localhost:8761` to confirm Eureka is running.

#### Step 4 — Start Question Service
```bash
cd ../QuestionService
mvn spring-boot:run
```

#### Step 5 — Start Quiz Service
```bash
cd ../QuizService
mvn spring-boot:run
```

#### Step 6 — Start API Gateway
```bash
cd ../GateWay
mvn spring-boot:run
```

All services should now be registered and visible in the Eureka dashboard.

---

## 📬 Sample API Requests

All requests go through the **API Gateway** at `http://localhost:8765`.

### Add a Question
```http
POST http://localhost:8765/question-service/question/add
Content-Type: application/json

{
  "questionTitle": "What is JVM?",
  "option1": "Java Virtual Machine",
  "option2": "Java Verified Module",
  "option3": "Java Visual Manager",
  "option4": "None of the above",
  "rightAnswer": "Java Virtual Machine",
  "difficultyLevel": "Easy",
  "category": "Java"
}
```

### Create a Quiz
```http
POST http://localhost:8765/quiz-service/quiz/create
Content-Type: application/json

{
  "category": "Java",
  "numQuestions": 5,
  "title": "Java Basics Quiz"
}
```

### Get Quiz Questions
```http
GET http://localhost:8765/quiz-service/quiz/get/{quizId}
```

### Submit Quiz & Get Score
```http
POST http://localhost:8765/quiz-service/quiz/submit/{quizId}
Content-Type: application/json

[
  { "id": 1, "response": "Java Virtual Machine" },
  { "id": 2, "response": "Compile time" }
]
```

---

## 🔄 Inter-Service Communication

The **Quiz Service** communicates with the **Question Service** using **OpenFeign**, a declarative REST client. Service discovery is handled automatically by Eureka — no hardcoded URLs are needed.

```java
@FeignClient("QUESTION-SERVICE")
public interface QuizInterface {
    @GetMapping("/question/generate")
    ResponseEntity<List<Integer>> getQuestionsForQuiz(
        @RequestParam String categoryName,
        @RequestParam Integer numQuestions
    );
}
```

---

## 📌 Key Concepts Demonstrated

- ✅ Microservices Architecture with Spring Boot
- ✅ Service Discovery with Eureka Server
- ✅ API Gateway with Spring Cloud Gateway
- ✅ Declarative REST Client with OpenFeign
- ✅ Inter-service Communication
- ✅ RESTful API Design with `ResponseEntity`
- ✅ JPA-based data persistence
- ✅ Separation of concerns across services

---

## 🧪 Testing

Use **Postman** or **cURL** to test the APIs. Import the endpoints listed above and ensure all four services are running before making requests.

---

## 🙌 Author

**Sathish Kumar B** — [@Sathish292004](https://github.com/Sathish292004)

---

## 📄 License

This project is licensed under an **Educational Use Only License**.
See the [LICENSE](LICENSE) file for details.

© 2026 Sathish Kumar B. All rights reserved.
