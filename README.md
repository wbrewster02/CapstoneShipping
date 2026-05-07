# Capstone Shipping Application

This project is part of a capstone course to simulate a shipping and order fulfillment system.

Team Members:
- William Brewster
- Daniel Munoz
- Mikenzie Adkins

Description:
Handles order fulfillment, shipment tracking, and delivery status updates using a shared database.
Provides tools for updating order and shipping statuses, maintaining history records, generating shipping labels, and exporting data for analytics, ensuring accurate tracking of orders from fulfillment through delivery.

File Structure:
- shipping\src\main\java\com\capstoneshipping, Contains main files.
- capstoneshipping\DataBase package, Contains Logic pertaining to DataBase connection, data acquisition, logging, etc.
- capstoneshipping\Graphics package, Contains Logic pertaining to GUI Elements.
- capstoneshipping\model package, Contains Data models that reflects Data from the Mysql Database.
- capstoneshipping\app.java, Entry point that initializes and launches the application.
- capstoneshipping\util package, Contains utility classes for PDF generation, data export, and webhook communication.Contains utility classes for PDF generation, data export, and webhook communication.

Environment:
    - JDK 26  
Source: https://www.oracle.com/java/technologies/downloads/

Dependencies:
    - Password Hashing and Decrpyting.
Source: https://mvnrepository.com/artifact/org.mindrot/jbcrypt
    
    - Json Accessor/Parser for Password Storage.
Source: https://mvnrepository.com/artifact/tools.jackson.core/jackson-core

    - Json Accessor/Parser for Password Storage.
Source: https://mvnrepository.com/artifact/tools.jackson.core/jackson-databind

    - Mysql Jdbc
Source: https://mvnrepository.com/artifact/com.mysql/mysql-connector-j

    - JavaFX UI Dependency
Source: https://mvnrepository.com/artifact/org.openjfx/javafx

    - JavaFX UI Dependency
Source: https://mvnrepository.com/artifact/org.openjfx/javafx

    - JavaFX UI Dependency
Source: https://mvnrepository.com/artifact/org.openjfx/javafx

    - JavaFX UI Dependency
Source: https://mvnrepository.com/artifact/org.openjfx/javafx-controls

    - Apache PDFBox (PDF Generation)
Source: https://mvnrepository.com/artifact/org.apache.pdfbox/pdfbox/2.0.30
