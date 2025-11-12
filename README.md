🔨 Online Auction Portal

An Online Auction Portal application built with Spring Boot designed to facilitate time-bound, competitive bidding for products. This system allows Admins to create the auction environment, Sellers to list products, and Users (Buyers) to place bids, with the highest bidder winning the product.

✨ Features and Workflow

This application implements a dynamic, multi-user auction workflow managed across three core user roles:

Admin Functionality (Auction Creation & Management):

Admins log in to the portal to manage the auction ecosystem.

They are responsible for creating the main auction events or categories that govern the bidding process.

Admins ensure the smooth operation and finalization of auctions.

Seller Functionality (Product Submission):

Sellers can register and log in to the portal.

They can submit their products for auction, including images, descriptions, and a starting price or reserve price.

The product listing becomes visible to buyers once approved and the auction time begins.

User/Buyer Functionality (Bidding & Purchase):

Users (Buyers) can register and log in to the portal.

They can browse all products submitted by sellers that are currently available for bidding.

Users can quote their price (place a bid) against any product they are interested in.

The Buyer who quotes the highest price when the auction time expires will automatically win that product.

Technical Features: Built on Spring Boot for robust backend logic and a clean, responsive front-end framework.

🚀 Getting Started

Follow these steps to set up and run the project on your local machine. You will be using Spring Tool Suite (STS) for development.

📋 Prerequisites

Ensure the following software is installed on your system:

Java Development Kit (JDK) 17+

Apache Maven (for dependency management and building)

MySQL Database (e.g., MySQL Workbench or a similar client)

Key Project Dependencies (Added via STS Initializer):
The project is built using the following core Spring Boot dependencies, which are typically included when creating the project in Spring Tool Suite (STS) or using the Spring Initializr:

Spring Data JPA: Used for interacting with the MySQL database via ORM (Object-Relational Mapping).

Spring Web (MVC): Enables the creation of the web application, handling requests, and defining RESTful endpoints.

MySQL Driver (Connector/J): The JDBC driver necessary to connect the Spring application to the MySQL database.

Spring Boot DevTools: Provides development-time features like automatic application restarts upon code changes.

⚙️ Installation and Setup

1. Database Setup

The first step is to create the necessary database and prepare the schema using MySQL.

Open your MySQL Workbench or command-line client.

Create the required database as specified for this project:

CREATE DATABASE online_auction_portal;













2. Configuration Update

You need to update the database credentials in the Spring Boot configuration file.

Navigate to the configuration file:

src/main/resources/application.properties













Find the following lines related to MySQL connection and update them to match your local setup. Ensure the database name matches online_auction_portal.

# Database Configuration Properties
spring.datasource.url=jdbc:mysql://localhost:3306/online_auction_portal
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD_HERE













3. Build and Run

With the database configured, you can now build and launch the application using Spring Tool Suite (STS).

Open Spring Tool Suite (STS):

Import the project as an existing Maven project.

Run the Application:

Right-click the project in the Project Explorer.

Select Run As -> Spring Boot App.

The application will now start, typically accessible at http://localhost:8080 unless configured otherwise.
