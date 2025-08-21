# Course-Student MCP Server

A Spring Boot application providing REST CRUD APIs for managing **Courses** and **Students**, integrated with **MySQL** (or H2 for development), and exposing **MCP (Multi-Channel Processing) tools** for AI clients like Claude Desktop.

---

## Features

* **REST API** for Courses and Students (Create, Read, Update, Delete)
* **Student enrollment** into courses
* **MCP STDIO tools** accessible by AI agents (Claude Desktop)
* **Optional HTTP MCP discovery** for web-based clients
* **Persistence** via MySQL (production) or H2 (in-memory development)
* Fully structured, maintainable project using Spring Boot, Spring Data JPA, and Spring AI MCP starter

---

## Technologies

* **Java 21**
* **Spring Boot 3.3**
* **Spring Data JPA**
* **MySQL** / **H2**
* **Spring AI MCP Server**
* **Lombok** (optional)


## Setup

### Prerequisites

* Java 21+
* Maven
* MySQL (or H2 for in-memory development)
* (Optional) Claude Desktop for MCP interaction

### MySQL Setup

1. Create database:

```sql
CREATE DATABASE course_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. Update `application.properties` with your MySQL credentials:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/course_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=your_mysql_password
```


## Build & Run

```bash
mvn clean package
java -jar target/course-student-mcp-1.0.0.jar
```

---

## REST API Endpoints

### Students

| Method | Endpoint                                      | Description                        |
| ------ | --------------------------------------------- | ---------------------------------- |
| GET    | `/api/students`                               | List all students                  |
| POST   | `/api/students`                               | Create a student (`name`, `email`) |
| GET    | `/api/students/{id}`                          | Get a student by ID                |
| PUT    | `/api/students/{id}`                          | Update student info                |
| DELETE | `/api/students/{id}`                          | Delete a student                   |
| POST   | `/api/students/{studentId}/enroll/{courseId}` | Enroll student in a course         |

### Courses

| Method | Endpoint            | Description                              |
| ------ | ------------------- | ---------------------------------------- |
| GET    | `/api/courses`      | List all courses                         |
| POST   | `/api/courses`      | Create a course (`title`, `description`) |
| GET    | `/api/courses/{id}` | Get course by ID                         |
| PUT    | `/api/courses/{id}` | Update course info                       |
| DELETE | `/api/courses/{id}` | Delete a course                          |

---

## MCP (Claude Desktop)

The MCP server exposes methods via **STDIO** for AI clients:

* `listStudents()` — List all students
* `addStudent(name, email)` — Add a student
* `listCourses()` — List all courses
* `addCourse(title, description)` — Add a course
* `enrollStudent(studentId, courseId)` — Enroll a student into a course

### STDIO Setup for Claude Desktop

1. In Claude Desktop, add MCP server configuration:

```json

{
  "mcpServers": {
    "education-mcp": {
      "command": "/usr/bin/java",
      "args": [
        "-Dspring.main.banner-mode=off",
        "-Dlogging.level.root=OFF",
        "-jar",
        "/ABSOLUTE/PATH/TO/target/course-student-mcp-1.0.0.jar"
      ]
    }
  }
}
```

2. Restart Claude Desktop — the MCP server will start automatically.

---

## Optional HTTP MCP

* Discover tools: `GET http://localhost:8080/mcp/tools`
* Call tool: `POST http://localhost:8080/mcp/call` with body:

```json
{
  "method": "addStudent",
  "params": {"name":"Alice","email":"alice@example.com"}
}
```

---

## Optional Demo Data

Add some initial records via `data.sql`:

```sql
INSERT INTO courses (title, description) VALUES ('Java Basics', 'Intro to Java'), ('Spring Boot', 'REST & JPA');
INSERT INTO students (name, email) VALUES ('Alice','alice@example.com'), ('Bob','bob@example.com');
INSERT INTO student_course (student_id, course_id) VALUES (1,1);
```

---

## Notes

* **Transactions & Validation:** Basic checks implemented; you can extend with `@Valid` annotations.
* **Error Handling:** Add `@ControllerAdvice` for better REST responses.
* **Security:** MCP and HTTP endpoints are open; add JWT/OAuth for production use.
* **Claude Desktop:** All MCP calls run locally; your data stays private.
