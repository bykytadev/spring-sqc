DROP DATABASE IF EXISTS `sqc_management`;
CREATE DATABASE `sqc_management`;
USE `sqc_management`;

CREATE TABLE departments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE employees (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    dob DATE NOT NULL,
    gender ENUM('Male', 'Female', 'Other') NOT NULL,
    salary DECIMAL(15, 2) NOT NULL,
    phone VARCHAR(15) NOT NULL,
    department_id BIGINT,
    FOREIGN KEY (department_id) REFERENCES departments(id)
);

INSERT INTO departments (id, name) VALUES
(1, 'HR'),
(2, 'Finance'),
(3, 'IT'),
(4, 'Marketing'),
(5, 'Sales');

INSERT INTO employees (id, name, dob, gender, salary, phone, department_id) VALUES
(1, 'Nguyen Van A', '2000-01-01', 'Male', 4500000.00, '0123456789', 1),
(2, 'Ho Van B', '2001-02-02', 'Female', 7500000.00, '0987654321', 2),
(3, 'Kim Dinh C', '2002-03-03', 'Male', 15000000.00, '0123987654', 1),
(4, 'Cau Nghe D', '2003-04-04', 'Female', 25000000.00, '0987123456', 3),
(5, 'Bang Quang F', '2004-05-05', 'Male', 9500000.00, '0123456780', 2);