# Cafe Mangagement

## Table of Contents

---

- [Overview](#overview) 
- [Technologies Used](#technologies-used)
-  [Setup](#setup)
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
    - [Loading Initial Data](#loading-initial-data)



## Overview

---

Cafe management is a Java-based application designed to manage a café's menu, customer interactions, and shopping cart functionalities. 
Built using Spring Boot and JPA, this project provides a structured way to handle café-related operations, allowing users to view menus, 
manage their shopping carts, and place orders. This particular one is supernatural themed.


## Features

---
### Current features:
- **Cafe Menu Management**: Load and view cafe menus with various sections and items.
- **Customer Management**: Register, update, and manage customer information.
- **Shopping Cart**: Add, update, and remove items from the cart, with total price calculations.
- **Role-Based Access**: Different roles including ADMIN, EMPLOYEE, and CUSTOMER to handle permissions.
- _Currently in MVP stage_ 

### Future features:
- Reviews
- Blog
- Sign up for rewards
- Promo codes
- Earn points
- Join view/Events

## Technologies Used

---

- Java 17
- Spring Boot
- JPA (Hibernate)
- Lombok
- Jackson for JSON processing
- MySQL Database

## Setup

---

### Project Structure

```
    cafe_mvp/
    │
    ├── src/
    │   ├── main/
    │   │   ├── java/
    │   │   │   └── org/
    │   │   │       └── perscholas/
    │   │   │           └── cafe_mvp/
    │   │   │               ├── model/
    │   │   │               ├── repository/
    │   │   │               ├── service/
    │   │   │               └── CafeMvpApplication.java
    │   │   └── resources/
    │   │       ├── application.properties
    │   │       └── menu.json
    │   └── test/
    │       └── java/
    ├── pom.xml
    └── README.md

```

## Getting Started

---

### Prerequisites

- Java 17 or higher
- Maven

### Installation

---

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/cafe_mvp.git
   cd cafe_mvp
   ```
2. Make sure you name the database `cafemvpdb` or alternatively, rename it in `application,properties`
    ```bash

      #Look for this line
        spring.datasource.url=jdbc:mysql://localhost:3306/cafemvpdb
   ```
3. You can configure the application by modifying the application.properties file located in src/main/resources. Here’s an example configuration for a MySQL database:
    ```bash
      spring.datasource.url=jdbc:mysql://localhost:3306/cafedb
      spring.datasource.username=root
      spring.datasource.password=yourpassword
      spring.jpa.hibernate.ddl-auto=update
      spring.jpa.show-sql=true
    ```
4. Build the project:
   ```bash
    mvn clean install
    ```
5. Run the application:
    ```bash
      mvn spring-boot:run
   ```
6. Access the application at:
    ```bash
     http://localhost:8080
   ```

### Loading Initial Data

The application can load an initial menu from a JSON file (menu.json). Ensure that this file is placed in the src/main/resources directory.
