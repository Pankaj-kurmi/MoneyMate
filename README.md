MoneyMate – Personal Finance Management System
Overview

MoneyMate is a personal finance management system developed to help users efficiently track their income, expenses, and monthly spending habits. The application provides a simple and user-friendly interface for managing financial records, analyzing expenses, and maintaining better control over personal budgets.

The project was built using Java, Spring Boot, and MySQL, following backend development best practices and RESTful API architecture.

Features
Add, update, and delete income records
Track daily and monthly expenses
Categorize expenses for better financial analysis
View total balance, income, and expenditure summaries
RESTful APIs for seamless data management
MySQL database integration for persistent storage
Exception handling and input validation
Clean and scalable backend architecture
Tech Stack
Backend
Java
Spring Boot
Spring Data JPA
Hibernate
Database
MySQL
Tools & Technologies
Maven
Postman
Git & GitHub
IntelliJ IDEA
Project Architecture

MoneyMate follows a layered architecture:

Controller Layer – Handles HTTP requests and API endpoints
Service Layer – Contains business logic and financial calculations
Repository Layer – Manages database operations using JPA
Database Layer – Stores user transactions and finance records in MySQL
Functionalities
Income Management

Users can:

Add income sources
Update income details
Delete income records
View total monthly income
Expense Management

Users can:

Add expenses with categories
Track spending history
Update or remove expenses
Analyze monthly expenses
Financial Summary

The system automatically calculates:

Total income
Total expenses
Remaining balance
REST API Endpoints
Method	Endpoint	Description
POST	/income	Add income
GET	/income	Get all income records
POST	/expenses	Add expense
GET	/expenses	Get all expenses
PUT	/expenses/{id}	Update expense
DELETE	/expenses/{id}	Delete expense
Database

The project uses MySQL for storing:

Income details
Expense records
Transaction history
Monthly financial summaries
Key Learnings

Through this project, I gained practical experience in:

Backend development using Spring Boot
REST API creation and testing
Database design and SQL queries
CRUD operations
Exception handling
API testing with Postman
Project structuring using layered architecture
