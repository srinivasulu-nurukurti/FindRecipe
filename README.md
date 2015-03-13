# FindRecipe
This application provides a lightweight RESTful API using the Java API for RESTful Web Services (JAX-RS).

#### Technologies used
1. JDK 8
2. Tomcat 8
3. Jersey - Restful Webservice
4. OpenCSV - to parse csv format text
5. JUnit4 - for testcases
6. Maven4


#### How to run application in local
1. Build application using maven as
     mvn package 

     As a result war file "FindRecipe-0.0.1-SNAPSHOT.war" is generated 
2. Rename above war file to "FindRecipe" and deploy to tomcat
3. launche application as http://localhost:8080/FindRecipe (assuming port as 8080)
