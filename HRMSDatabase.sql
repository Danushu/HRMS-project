
CREATE DATABASE HRMS;
USE HRMS;

-- 1. Organizations
CREATE TABLE Organizations (
    OrganizationId INT PRIMARY KEY AUTO_INCREMENT,
    OrganizationName VARCHAR(100) NOT NULL,
    Address VARCHAR(150),
    Email VARCHAR(100) NOT NULL UNIQUE,
    PasswordHash VARCHAR(255) NOT NULL
);

-- 2. Employees
CREATE TABLE Employees (
    EmployeeId INT PRIMARY KEY AUTO_INCREMENT,
    OrganizationId INT NOT NULL,
    EmployeeName VARCHAR(100) NOT NULL,
    Salary DECIMAL(10,2),
    Department VARCHAR(50),
    FOREIGN KEY (OrganizationId)
        REFERENCES Organizations(OrganizationId)
);

-- 3. Attendance
CREATE TABLE Attendance (
    AttendanceId INT PRIMARY KEY AUTO_INCREMENT,
    EmployeeId INT NOT NULL,
    Status VARCHAR(20) NOT NULL,
    FOREIGN KEY (EmployeeId)
        REFERENCES Employees(EmployeeId)
);

-- 4. Salaries
CREATE TABLE Salaries (
    SalaryId INT PRIMARY KEY AUTO_INCREMENT,
    EmployeeId INT NOT NULL,
    Salary DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (EmployeeId)
        REFERENCES Employees(EmployeeId)
);

-- 5. Notices and Holidays
CREATE TABLE Notices (
    NoticeId INT PRIMARY KEY AUTO_INCREMENT,
    OrganizationId INT NOT NULL,
    Title VARCHAR(100) NOT NULL,
    Description VARCHAR(255),
    Type VARCHAR(20),
    FOREIGN KEY (OrganizationId)
        REFERENCES Organizations(OrganizationId)
);

INSERT INTO Organizations
(OrganizationName, Address, Email, PasswordHash)
VALUES
('Softmason', 'Hyderabad', 'admin@softmason.com', 'sample_hash');

INSERT INTO Employees
(OrganizationId, EmployeeName, Salary, Department)
VALUES
(1, 'Ravi', 25000, 'IT'),
(1, 'Priya', 30000, 'HR');

INSERT INTO Attendance (EmployeeId, Status)
VALUES (1, 'Present'), (2, 'Absent');

INSERT INTO Salaries (EmployeeId, Salary)
VALUES (1, 25000), (2, 30000);

INSERT INTO Notices (OrganizationId, Title, Description, Type)
VALUES (1, 'Holiday Announcement', 'Office holiday', 'Holiday');

select * from Organizations

Select * from Employees

