# University Management (Gestion Université)

Spring Boot web application for managing a university: students, teachers, groups, modules, rooms and the **weekly timetable**. Access depends on the user's role (admin, teacher or student).

## Features

**Authentication and roles (Spring Security)**
- Custom login page with email and password. Users are stored in MySQL and passwords are hashed with BCrypt.
- Three roles, `ADMIN`, `TEACHER` and `STUDENT`, each redirected to its own dashboard after login.
- URL access rules per role: `/admin/**`, `/teacher/**` and `/student/**`.

**Administration** (`/admin/...`)
- Create, edit and delete students, teachers, groups, modules and rooms. Creating a student or a teacher also creates their user account and role.
- Assign groups to modules.
- **Timetable sessions** with server-side validation:
  - time format and start time before end time;
  - **conflict detection**: a room, a teacher or a group cannot have two sessions in overlapping time slots on the same day.

**Dashboards**
- **Teacher**: personal weekly schedule, sorted by day and time.
- **Student**: the weekly schedule of their group.

## Tech stack

Java 17, Spring Boot 3.5 (Web, Security, Data JPA, Validation), Thymeleaf with `thymeleaf-extras-springsecurity6`, MySQL, Maven. Pages are styled with Tailwind CSS and Bootstrap Icons, loaded from a CDN.

## Architecture

Layered architecture:

```
src/main/java/com/univ/gest/
├── config/          Spring Security, role-based login redirect, user details, data initializer
├── domain/entity/   JPA entities: User, Role, StudentProfile, TeacherProfile, GroupEntity, ModuleEntity, Room, SessionEntity
├── domain/dto/      form and display DTOs
├── repository/      Spring Data JPA repositories, including timetable conflict queries
├── service/         service interfaces and implementations (business rules)
└── web/controller/  MVC controllers (admin CRUD, auth, dashboards)
src/main/resources/templates/   Thymeleaf views
```

## Getting started

Prerequisites: JDK 17 and a MySQL server on `localhost:3306`.

1. Check the database settings in `src/main/resources/application.properties`. The database `university_gest` is created automatically. The default user is `root` with an empty password; change it if needed.
2. Run the application:
   ```bash
   ./mvnw spring-boot:run        # Windows: mvnw.cmd spring-boot:run
   ```
3. Open `http://localhost:8080/login`.

On first startup, `DataInitializer` creates the three roles and a **local demo admin account** (`admin@univ.local`; the password is set in `DataInitializer.java`). Use it only for local development and change it for any other use.

## Limitations

- The demo admin password is hard-coded in `DataInitializer`. It should come from an environment variable.
- The only test is the default Spring Boot context test.
- Tailwind is loaded from its CDN, which is fine for development but not for production.
- Students are linked to a group by its name, not by a foreign key.

## Possible improvements

- Read the admin credentials from environment variables, and use Spring profiles (dev / prod).
- Add service tests, especially for the timetable conflict rules, and controller tests with `spring-security-test`.
- Export timetables (PDF, iCal).
- Add Docker Compose (app and MySQL).
