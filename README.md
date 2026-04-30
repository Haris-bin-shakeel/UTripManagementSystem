# 🎓 University Trip Management System (UTMS)

> A full-stack Java Swing desktop application implementing a university trip 
> lifecycle management system — built as part of the Software Design & Analysis 
> course (BS-CS 4D).

---

## 👥 Group Members

| No. | Name | Roll No. | Contribution |
|-----|------|----------|--------------|
| 1 | Muhammad Haris | 24P-0638 | Model Layer, Controller Layer, Sequence Diagram |
| 2 | Arham Ali | 24P-0748 | State Machine, Activity Diagram, View Layer, Document Compilation |

---

## 📌 Project Overview

The University Trip Management System (UTMS) is a role-based desktop application 
that digitizes and automates the complete lifecycle of university-organized trips. 
It eliminates paper-based workflows and provides real-time access to all 
stakeholders.

### ✨ Key Features:
- **Stunning Modern GUI**: A neon-accented dark 3D interface using custom glassmorphism and starfield effects.
- **Robust Error Handling**: Real-time validation for all inputs, including conflict detection for vehicles and drivers.
- **Role-Based Dashboards**: Tailored interfaces for Admin, Teacher, and Student roles.

---

## 🏗 Architecture

This project strictly follows **MVC (Model-View-Controller)** architecture 
as required by NFR-09.

```text
src/
├── model/        → 11 entity classes from Task 1 & 2 Class Diagram
├── view/         → Java Swing GUI (8 view files, role-based dashboards)
├── controller/   → Business logic mapped from Sequence Diagram
├── lifecycle/    → TripStatus enum (11 states from State Machine Diagram)
└── util/         → DataStore (in-memory data management)
```

### Design Patterns Used:
| Pattern | Where Applied | Purpose |
|---------|--------------|---------|
| MVC | Entire project | Separation of concerns |
| Template Method | User → Teacher/Student/Admin/Driver | Role-based inheritance |
| Observer | NotificationService in controllers | Alert stakeholders on state change |

---

## 🔄 Trip Lifecycle (11 States)
CREATED → PENDING → APPROVED / REJECTED
↘ UNDER_REVISION → PENDING
APPROVED → RESOURCE_ASSIGNED → ENROLLMENT_OPEN
→ IN_PROGRESS → COMPLETED → ARCHIVED
→ CANCELLED

---

## ✅ Functional Requirements Implemented

| FR ID | Description | Status |
|-------|-------------|--------|
| FR-01 | Role-based login with university credentials | ✅ |
| FR-02 | Role-based access control (Admin/Teacher/Student/Driver) | ✅ |
| FR-03 | Teacher creates trip request | ✅ |
| FR-04 | Admin approves or rejects trip with remark | ✅ |
| FR-05 | Student enrollment in approved trips | ✅ |
| FR-06 | Vehicle assignment by Admin | ✅ |
| FR-07 | Driver assignment with conflict prevention | ✅ |
| FR-09 | Automated notifications on state change | ✅ |
| FR-10 | Budget estimation and finance request | ✅ |
| FR-12 | Attendance marking by teacher | ✅ |
| FR-13 | Trip cancellation with notification | ✅ |
| FR-16 | Conflict detection for vehicle/driver scheduling | ✅ |

---

## 🖥 How to Compile

Make sure you have **Java JDK 11 or higher** installed.

```bash
# From the UTripManagementSystem/ directory
javac -d bin -sourcepath src src/lifecycle/TripStatus.java src/model/*.java src/util/DataStore.java src/controller/*.java src/view/*.java src/main/Main.java
```

---

## ▶ How to Run

```bash
java -cp bin main.Main
```

---

## 🔐 Default Login Credentials

| Role | Email | Password |
|------|-------|----------|
| Admin | admin@utms.edu | admin123 |
| Teacher | teacher@utms.edu | teacher123 |
| Student | student1@utms.edu | student123 |
| Driver | driver@utms.edu | driver123 |

---

## 📁 Project Structure

```text
UTripManagementSystem/
├── src/
│   ├── main/Main.java
│   ├── model/          (User, Teacher, Student, Admin, Driver,
│   │                    TripRequest, Vehicle, Enrollment,
│   │                    Budget, Notification, Feedback)
│   ├── view/           (LoginView, AdminDashboard, TeacherDashboard,
│   │                    StudentDashboard, CreateTripView,
│   │                    TripApprovalView, StudentEnrollmentView,
│   │                    UIComponents)
│   ├── controller/     (AuthController, TripController,
│   │                    EnrollmentController, ResourceController)
│   ├── lifecycle/      (TripStatus - 11 states)
│   └── util/           (DataStore)
├── bin/                (Compiled .class files)
├── README.md
└── .gitignore
```

---

## 🎓 Course Information

| Field | Detail |
|-------|--------|
| Course | Software Design & Analysis |
| Assignment | Task 3 — From Design to Implementation |
| Section | BS-CS 4D |
| Instructor | Engr. Muhammad Umer Haroon |
| Scenario | 6 — Trip Management System |
