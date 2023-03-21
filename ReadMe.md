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
    <li>
        <a href="#usage">Usage</a>
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
* [![Swagger][Swagger-img]][Swagger-url]
* [![JUnit][JUnit-img]][JUnit-url]

Part of the frontend is written with

* [![React][React-img]][React-url]
* [![Thymeleaf][Thymeleaf-img]][Thymeleaf-url] 
* [![Bootstrap][Bootstrap-img]][Bootstrap-url]

This project was created with help next tools:

* [![IntelliJ][IntelliJ-img]][IntelliJ-url]
* [![Maven][Maven-img]][Maven-url]
* [![VSCode][VSCode-img]][VSCode-url]

<p align="right"><a href="#about-the-project">back to top</a></p>



<!-- GETTING STARTED -->
## Getting Started

In order to run the application on local machine, you must perform the following steps:

1. Clone the repo
   ```sh
   git clone https://github.com/mike-scythian/StoreManagement.git
   ```
   You have also to install PostgreSQL.
   
2. Make sure all dependencies from pom.xml are loaded. Here are all versions of dependencies that You need:
```xml

		<java.version>19</java.version>
		<spring-boot-starter-data-jpa-version>3.0.1</spring-boot-starter-data-jpa-version>
		<spring-boot-starter-security-version>3.0.1</spring-boot-starter-security-version>
		<spring-boot-starter-thymeleaf-version>3.0.1</spring-boot-starter-thymeleaf-version>
		<spring-boot-starter-web-version>3.0.1</spring-boot-starter-web-version>
		<spring-boot-starter-test-version>3.0.1</spring-boot-starter-test-version>
		<thymeleaf-extras-sprinsecurity6-version>3.1.1.RELEASE</thymeleaf-extras-sprinsecurity6-version>
		<commons-validator-version>1.7</commons-validator-version>
		<liquibase-version>4.18.0</liquibase-version>
		<mapstruct-version>1.5.3.Final</mapstruct-version>
		<mapstruct-processor-version>1.5.3.Final</mapstruct-processor-version>
		<open-api-version>2.0.2</open-api-version>
		<hibernate-validator-version>8.0.0.Final</hibernate-validator-version>
		<h2-version>2.1.214</h2-version>

```

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

<strong>HIGHLY RECOMMENDED TO CHANGE PASSWORD AFTER FIRST SIGNIN</strong>

Notice that only user with ADMIN_ROLE can create new users.

<p align="right"><a href="#about-the-project">back to top</a></p>


## Usage

With application You can interact as client two ways:

1)   You can send requests to endpoints with SwaggerUI:
  ```sh
     /swagger-ui/index.html
  ```
2)    If you want to use simple web client app from project, you need start React application and replace folders "public" and "src" in project root. 
         This is describe how start React app possible. Notice that Your local root host will be "localhost:3000"
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
[Java-img]: https://img.shields.io/badge/OpenJDK-FF9E0F?style=for-the-badge&logo=openjdk&logoColor=black
[Java-url]: https://openjdk.org/projects/jdk/19/
[Spring-img]: https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=spring&logoColor=white
[Spring-url]: https://spring.io
[PostgreSQL-img]: https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=PostgreSQL&logoColor=black
[PostgreSQL-url]: https://www.postgresql.org
[Swagger-img]: https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black
[Swagger-url]: https://swagger.io/specification/
[JUnit-img]: https://img.shields.io/badge/JUnit5-25A162?style=for-the-badge&logo=junit5&logoColor=red
[JUnit-url]: https://junit.org/junit5/
[React-img]: https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB
[React-url]: https://reactjs.org
[Thymeleaf-img]: https://img.shields.io/badge/Thymeleaf-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white
[Thymeleaf-url]: https://www.thymeleaf.org
[Maven-img]: https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=black
[Maven-url]: https://maven.apache.org
[Bootstrap-img]: https://img.shields.io/badge/Bootstrap-563D7C?style=for-the-badge&logo=bootstrap&logoColor=white
[Bootstrap-url]: https://getbootstrap.com/docs/5.0/getting-started/introduction/
[IntelliJ-img]: https://img.shields.io/badge/IntelliJIDEA-000000?style=for-the-badge&logo=intellijidea&logoColor=white
[IntelliJ-url]: https://www.jetbrains.com/idea/
[VSCode-img]: https://img.shields.io/badge/VSCode-007ACC?style=for-the-badge&logo=visualstudiocode&logoColor=white
[VSCode-url]: https://code.visualstudio.com/
