# HRMS – Human Resource Management System

## Project Overview

The Human Resource Management System (HRMS) is a console-based application developed using Core Java and MySQL. It helps organizations manage employee information, attendance, salary records, and notices or holidays through a menu-driven interface.

The application uses JDBC to connect Java with MySQL and perform database operations.

## Technologies Used

- **Programming Language:** Core Java
- **Database:** MySQL
- **Database Connectivity:** JDBC
- **IDE:** IntelliJ IDEA
- **Version Control:** Git and GitHub

## Main Features

### 1. Organization Management
- Register a new organization.
- Log in using email and password.
- Recover or reset a password.
- Return to the main menu or exit the application.

Organization fields include Organization ID, organization name, address, email, and password hash.

### 2. Employee Management
- Add new employees.
- View employee details.
- Update employee information.
- Delete employee records.

Employee details include Employee ID, Organization ID, employee name, salary, and department.

### 3. Attendance Management
- Create attendance records.
- View attendance details.
- Update attendance status.
- Delete attendance records.

Attendance status supports Present, Absent, and Leave.

### 4. Salary Management
- Create salary records for employees.
- View salary details.
- Update salary amounts.
- Delete salary records.

Each salary record is linked to an employee through Employee ID.

### 5. Notices and Holidays
- Create notices or holiday announcements.
- View existing notices.
- Update notice details.
- Delete notices.

Notice details include Notice ID, Organization ID, title, description, and type.

## Application Menu

The application starts with the following options:

1. Create New Organization
2. Login to Organization
3. Exit

After successful login, the organization dashboard provides:

1. Employee Management
2. Attendance Management
3. Salary Management
4. Notices / Holidays
5. Logout

Logging out returns the user to the main menu.

## Database Design

The project uses a MySQL database named `HRMS` with five tables.

| Table | Purpose |
|---|---|
| `Organizations` | Stores organization details and login credentials |
| `Employees` | Stores employee information and organization links |
| `Attendance` | Stores employee attendance statuses |
| `Salaries` | Stores employee salary records |
| `Notices` | Stores organization notices and holiday announcements |

### Relationships

- One organization can have multiple employees.
- Each employee belongs to an organization.
- Each attendance record belongs to an employee.
- Each salary record belongs to an employee.
- Each notice belongs to an organization.

Primary keys uniquely identify records, while foreign keys maintain relationships between the tables.

## Core Java and JDBC Concepts

The project demonstrates:

- Classes, methods, and encapsulation.
- Variables, data types, and conditional statements.
- Loops and switch statements for menu navigation.
- Scanner for console input.
- JDBC connections to MySQL.
- PreparedStatement for parameterized SQL queries.
- ResultSet for retrieving database records.
- SQL CRUD operations: Create, Read, Update, and Delete.
- Password hashing for organization credentials.

## Setup and Execution

1. Install a compatible Java JDK and MySQL Server.
2. Open the project in IntelliJ IDEA.
3. Add MySQL Connector/J to the project libraries.
4. Execute `HRMSDatabase.sql` in MySQL.
5. Configure the database URL, username, and password in `DBConnection.java`.
6. Open `Main.java` and run the application.

## Project Structure

```text
HRMS/
├── src/
│   └── hrms/
│       ├── AttendanceService.java
│       ├── DBConnection.java
│       ├── EmployeeService.java
│       ├── HRMSMenu.java
│       ├── Main.java
│       ├── NoticeService.java
│       ├── OrganizationService.java
│       └── SalaryService.java
├── HRMSDatabase.sql
├── .gitignore
└── README.md
```

## Security Notes

- Passwords should be stored as secure hashes rather than plain text.
- Database credentials should not be committed to GitHub.
- Password recovery should verify email ownership before allowing a password change.
- Organization-specific operations should restrict access to records belonging to the logged-in organization.

## Future Scope

- Improve input validation and error handling.
- Add automated tests for database operations.
- Improve password recovery security.
- Add transaction management for related database operations.

## Conclusion

This project demonstrates how Core Java, JDBC, and MySQL can be combined to build a menu-driven HRMS application with organization login and employee, attendance, salary, and notice management.

## Author

Java HRMS learning project.
