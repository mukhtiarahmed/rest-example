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
  * `docker-compose up -d` 
  
     
5. Let's authentication and get JWT token 

   ``` 
   curl -X POST http://localhost:8080/authenticate  -H 'Content-Type: application/json'  -d '{	"userName" : "ahmed",	"password" : "password"}' 
   ```
    
   You will get following format Json response. data field has the JWT token. It requires for authorization to call rest api.
   
    ```
        {
           "status": "SUCCESS",
           "message": "Authenticate successfully",
           "data": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhaG1lZCIsInJvbGUiOiJST0xFX0FETUlOIiwidXNlck5hbWUiOiJhaG1lZCIsImV4cCI6MTU3OTgzMTk1NywiaWF0IjoxNTc5NzQ1NTU3fQ.wFc6ORT_ttfJHZRqOySrFp3YqMHPPLUkNoM47NI9ru-uhURhkdDHyfwIaAoNgeSOZf0m_EojUx1UkqkP_r8R-g"
       }
       
     ```

6. you can use following user account to login the application.
   * Username = ahmed, password = password, Role = Admin
   * Username = mukhtiar, password = password, Role = User
   * Username = admin, password = admin, Role = Admin

7. Get All Active Hobby  resource.
     ``` 
    curl -X GET http://localhost:8080/api/1.0/hobby/  -H 'Authorization: Bearer <JWT TOkEN>'
    
     ```

   You will get following format Json response.
   
 ```
 
  {
      "status": "SUCCESS",
      "message": null,
      "data": [
          {
              "id": "0033306d-f6d1-4e46-a3f9-7ee7a12ecbab",
              "name": "magician"
          },
          {
              "id": "0076c010-7570-4538-a024-77cf2a7435f2",
              "name": "Church/church activities"
          },
          ...
      ]
  }   
  
        
  ```
  
8. Get All Active Colour  resource.
 ```
     curl -X GET http://localhost:8080/api/1.0/colour/  -H 'Authorization: Bearer <JWT TOkEN>' 
     
 ```


   You will get following format Json response.
   
 ```
  {
      "status": "SUCCESS",
      "message": null,
      "data": [
           {
                      "id": "00298aa3-17ff-4f4a-a647-1fd06e4caab7",
                      "name": "Magenta",
                      "hex": null
                  },
                  {
                      "id": "029dc82b-568a-447f-ad76-d0c2c187bed0",
                      "name": "Dark Orchid",
                      "hex": null
                  },
          ...
      ]
  }  
   
  ```
  
9. Create Person resource.


  ``` 
     curl -X POST   http://localhost:8080/api/1.0/person -H 'Authorization: Bearer  <JWT TOKEN>'   \
    
      -d '{
    	 "lastName": "Keynes",
    	 "firstName" : "John",
    	 "age" : 29,
    	 "colourId" : "0b3db7e0-9c4c-4936-98e0-24ef27fd5824",
    	 "hobbies" : ["010d961a-d6fc-40d2-8cfd-350b6ff1e34f", "0215299c-b300-4bf1-a60c-f108d531b1db","05645502-4a47-4d18-9dc9-c058d6712061"]
         }'
         
  ``` 
     
10. Get All Active Person 

    ``` 
    curl -X GET   'http://localhost:8080/api/1.0/person/pagination?page=0&pageSize=20' -H 'Authorization: Bearer  <JWT TOKEN>'
     ``` 

> **Note:** Dockerfile and docker-compose.yml files are in project root dir.


