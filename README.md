# Upgraded-SpringMVC-Library-DATA-JPA

This application is the upgraded version of first Library App in Spring MVC. 
The main features of the library application include:

User Management: The application allows the creation, deletion, and editing of user information as well as adding or removing books. Each user can own zero or more books.

Book Management: The application allows for the creation, deletion, and editing of books in the library system. Each book can be assigned to one person.

Moreover, the application provides now a qualitative and tested pagination of books integrated in the user interface and a search bar, that allows to search for specific books using a key word.

The connection between the application and database is performed through Spring Data JPA, which is a high-level interaction based on Hibernate. This feature allows the developer to create a big quantity 
of repository methods in just few lines by not having to write the queries himself.

The used database is MySql. To run the application, you will need to have Java, MySQL and an application server installed on your computer.

I used Tomcat Server 9.0. Be sure that you do not use any version above 10.0, because the application is based on Spring 5. The 6th version of Spring Framework can be run on Tomcat Server 10.0 or newer.

To get started with the application, simply clone the repository from GitHub, adjust the application.properties file to your prefered settings and add the mysql file placed in the branch to your database management program.
