# Spring Boot, Spring Security,  JPA, and Docker  

## Prerequisites:
* Docker 
* JDK 1.8 
* Maven 3.*


## Install and run the project 
1. download/clone the project 
2. Build the project using following maven command from project root folder where pom.xml file place.
  * `mvn clean package`
3. Create docker image from following command 
  * `docker build -t assignment_image .`
4. Run the docker-compose using the following command   
  * `docker-compose up`
  
     
5. Let's authentication and get JWT token 
   * `curl -X POST http://localhost:8080/authenticate  -H 'Content-Type: application/json'  -d '{	"userName" : "ahmed",	"password" : "password"}'`
6. you can use following user account to login the application.
   * Username = ahmed, password = password
   * Username = mukhtiar, password = password
   * Username = admin, password = password

> **Note:** Dockerfile and docker-compose.yml files are in project root dir.


