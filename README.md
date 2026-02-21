# 🕒 User Attendance Management System

A robust, full-stack web application designed to track user attendance, manage daily check-ins/check-outs, and provide a comprehensive administrative dashboard for user management. Built as a portfolio project demonstrating MVC architecture, secure authentication, and relational database management.

## 🚀 Tech Stack

**Backend:**
* **Java 17+**
* **Spring Boot 3.x** (Spring Web, Spring Data JPA)
* **Spring Security** (Role-based access)
* **MySQL** (Relational Database)
* **Hibernate** (ORM for Database Mapping)

**Frontend:**
* **Thymeleaf** (Server-side Java template engine)
* **Bootstrap 5** (Responsive UI/UX styling)
* **HTML5 / CSS3 / Vanilla JS**

---

## ✨ Key Features

### 👤 User Portal

<img width="1370" height="477" alt="image" src="https://github.com/user-attachments/assets/c71a7e49-c88c-4ee5-acf3-30513689e095" />


* **Daily Attendance Tracking:** Users can securely punch in and punch out for the day.
* **Real-time Status:** Validates whether an employee has already checked in or checked out, dynamically disabling UI buttons to prevent duplicate entries.
* **Time Formatting:** Accurately logs and displays `LocalTime` and `LocalDate` using Java 8 Time APIs and Thymeleaf `#temporals` formatting.

### 🛡️ Admin Dashboard

<img width="1917" height="532" alt="image" src="https://github.com/user-attachments/assets/4a449d28-5be7-4c33-82bf-4f121ecec52a" />

* **Role-Based Access Control:** Strictly protected routes ensuring only users with `ADMIN` roles can access management tools.
* **User Management CRUD:** * Create new staff accounts with secure, hashed passwords.
  <img width="1401" height="868" alt="image" src="https://github.com/user-attachments/assets/1d5df917-3fdd-4c13-bac4-ef6a49ba3fd7" />

  * View a tabulated list of all registered employees.
  * Update/Edit existing user details.
* **Attendance Reports:** View historical data and attendance logs for the entire organization.

---

## 📚 Documentation

Comprehensive project documentation is available in the `Documentation` directory to assist developers and reviewers in understanding the system architecture:
* **UML Diagrams:** Includes detailed structural and behavioral diagrams mapping out the system design, entity relationships, and data flow.
* **API Collection:** A complete Postman collection is provided for seamless testing of the application's endpoints, detailing expected request payloads and response structures.

---

## 🚀 Roadmap & Future Enhancements

The application is actively being refined. The following technical improvements are scheduled for upcoming releases:
* **Soft Deletion Mechanism:** Transitioning from hard deletes to soft deletes for the `User` entity using `isActive` flag.
* **Server-Side Pagination:** Implementing Spring Data JPA pagination for administrative table views to optimize database queries, reduce load times, and improve UI performance when handling large datasets.
* **Global Exception Handling:** Establishing a centralized `@ControllerAdvice` architecture to catch backend errors gracefully, providing standardized, user-friendly error messages and robust system logging.
* **User and request for leaves and editing attendance information.

---
## 🛠️ Installation & Setup

### Prerequisites
* JDK 17 or higher
* Maven 3.6+
* MySQL Server (running on default port `3306`)

### 1. Clone the repository
```bash
git clone [https://github.com/your-username/attendance-app.git](https://github.com/your-username/attendance-app.git)
cd attendance-app
```

### 2. Update Credentials:
change `src/main/resources/application.example.properties` into `src/main/resources/application.properties` and enter your MySQL username and password.

### 3. Build Run
```bash
# Install dependencies and build
./mvnw clean install

#Run the application
./mvnw spring-boot:run
```
* The application will be running at `http://localhost:8080`

### 4. Test with demo user
I have seeded the demo users and admin with default password `password`.
* Demo Admin : username `admin_admin` | password: `password`.
* Demo User : username `mchen` | password: `password`.

