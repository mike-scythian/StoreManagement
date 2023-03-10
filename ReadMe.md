<!-- TABLE OF CONTENTS -->
<details>
  <summary>Content</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
    </li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

This project was written as part of the "Java Developer" course from the collaboration A-Level and Nix.Education.
The idea of the project theme is taken from the real problem of effective management of a small network of retail outlets.
The project consists of two parts: server and client. Implemented basic functionality that can be supplemented.


### Built With

The server part is a monolithic REST application. List of technologies and tools used:

* [![Java][Java-img]][Java-url]
* [![Spring Boot][Spring-img]][Spring-url]
* [![Postgres][PostgreSQL-img]][PostgreSQL-url]
* [![Lombok][lombok-img]][Lombok-url]
* [![Swagger][Swagger-img]][Swagger-url]
* [![JUnit][JUnit-img]][JUnit-url] + [![Mockito][Mockito-img]][Mockito-url]
* [![Maven][Maven-img]][Maven-url]

Part of the frontend is written with

* [![React][React-img]][React-url]
* [![Thymeleaf][Thymeleaf-img]][Thymeleaf-url] 
* [![Bootstrap][Bootstrap-img]][Bootstrap-url]


<p align="right"><a href="#about-the-project">back to top</a></p>



<!-- GETTING STARTED -->
## Getting Started

In order to run the application on local machine, you must perform the following steps:

1. Clone the repo
   ```sh
   git clone https://github.com/mike-scythian/StoreManagement.git
   ```
2. Make sure all dependencies from pom.xml are loaded

3. Setup all variables:<br><br>
  RUN-PORT<br>
  PGHOST<br>
  PGDATABASE<br>
  PGSCHEMA<br>
  USER<br>
  PSSWRD<br>
 
4. Run the project with IDE or with maven command line
   ```sh
   mvn install
   ```
First sign make with credentials:<br><br>
As admin<br>
email : admin@admin.com<br>
password : admin<br>

As user<br>
email : user@use.com<br>
password : user<br>

You can send requests to endpoints with SwaggerUI:
 ```sh
/swagger-ui/index.html
 ```
 
 If you want to use simple frontend from project, you need start React application. This is describe how it possible
 
 ```sh
https://reactjs.org/docs/create-a-new-react-app.html
 ```
 
<p align="right"><a href="#about-the-project">back to top</a></p>


<!-- LICENSE -->
## License

Distributed under the MIT License.
<p align="right"><a href="#about-the-project">back to top</a></p>



<!-- CONTACT -->
## Contact

Mike Nehanov<br>
[LinkedIn](https://www.linkedin.com/in/mykhailo-nehanov-b53a391b3/) <br>
m.nehanov@gmail.com

Project Link: [https://github.com/mike-scythian/StoreManagement](https://github.com/mike-scythian/StoreManagement)

<p align="right"><a href="#about-the-project">back to top</a></p>



<!-- MARKDOWN LINKS & IMAGES -->
[Java-img]: https://img.shields.io/badge/Java-19-orange
[Java-url]: https://openjdk.org/projects/jdk/19/
[Spring-img]: https://img.shields.io/badge/Spring%20Boot-3.0-green
[Spring-url]: https://spring.io
[PostgreSQL-img]: https://img.shields.io/badge/PostgreSQL-15-blue
[PostgreSQL-url]: https://www.postgresql.org
[Lombok-img]: https://img.shields.io/badge/Project-Lombok-red
[Lombok-url]: https://projectlombok.org
[Swagger-img]: https://img.shields.io/badge/Swagger%20%2B%20Open%20API-3.1-green
[Swagger-url]: https://swagger.io/specification/
[JUnit-img]: https://img.shields.io/badge/JUnit-5-red
[JUnit-url]: https://junit.org/junit5/
[Mockito-img]: https://img.shields.io/badge/Mockito-5.2-green
[Mockito-url]: https://site.mockito.org
[React-img]: https://img.shields.io/badge/React-18.2-blue
[React-url]: https://reactjs.org
[Thymeleaf-img]: https://img.shields.io/badge/Thymeleaf-3.1.1-green
[Thymeleaf-url]: https://www.thymeleaf.org
[Maven-img]: https://img.shields.io/badge/Maven-4.0-green
[Maven-url]: https://maven.apache.org
[Bootstrap-img]: https://img.shields.io/badge/Bootstrap-5.0-blue
[Bootstrap-url]: https://getbootstrap.com/docs/5.0/getting-started/introduction/
