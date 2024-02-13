# JavaFX Desktop Application with MySQL Integration

This project is a desktop application developed using JavaFX for the frontend and MySQL for the backend database. It aims to provide a simple yet comprehensive example of how to create a Java desktop application with a relational database.

## Features

- User-friendly graphical interface built with JavaFX.
- MySQL database integration for data storage and retrieval.
- CRUD (Create, Read, Update, Delete) operations on database records.
- Responsive design for various screen sizes.

## Prerequisites

Before running this application, ensure you have the following installed:

- Java Development Kit (JDK) 8 or higher
- MySQL Database Server
- JavaFX
- MySQL Connector/J library for Java

## Setup

1. Clone this repository to your local machine:
2. Import the project into your preferred Java IDE (e.g., IntelliJ IDEA, Eclipse).

3. Set up your MySQL database:
- Create a new database.
- Create necessary tables according to the application's requirements.

4. Configure database connection:
- Update the `application.properties` file with your database connection details.

```properties
# Database connection properties
db.url=jdbc:mysql://localhost:3306/your_database_name
db.username=your_username
db.password=your_password


