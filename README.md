# Nasa API Implementation 🚀

![](img/home-page.png)
<hr style="width:75%">

## Introduction
Welcome to the Nasa API Implementation project! This **full-stack web application** utilizes Nasa's API key to provide users with captivating Astronomy Picture Of The Day (APOD) and Mars Rover Photos. The project seamlessly integrates various technologies to deliver a robust and user-friendly experience.


Discover the NASA Astronomy picture of the day(apod) and Rover images from the mars explorations. You can choose...
* one of three different mars rovers 
  * Curiosity
  * Opportunity 
  * Spirit
* multiple different cameras, e.g.
  * Navigation Camera
  * Panoramic Camera
  * Front Hazard Avoidance Camera
  * ...
* a specific day (sol = mars days) since the mars landing 

<hr style="width:75%">

#### Technology Stack And Features

Astronomy Picture Of The Day (APOD) and Mars Rover Photos:-
Fetching and displaying stunning images from Nasa's API using **Spring Boot**.

Backend:-
Utilizes **MySQL** database for data storage.

Spring Framework:-
The project is developed using the robust and efficient **Spring Boot 3** and **Spring Framework 6**, ensuring a scalable and maintainable codebase. The project showcases a sophisticated integration of cutting-edge technologies, emphasizing security, performance, and user experience.

Restful Web Services:-
**RESTful web services** have been meticulously crafted to facilitate seamless access to NASA's Astronomy Picture of the Day and Mars Rover Photos, offering a streamlined experience for users and developers. A comprehensive **REST CRUD API** has been implemented for managing Astronomy Pictures of the Day. Leveraging Spring Security, the API restricts access to authorized administrators, ensuring secure and controlled data operations.

Frontend:-
The front-end is designed using the **Model-View-Controller (MVC) architecture**, emphasizing modularity and separation of concerns. **Thymeleaf**, a modern server-side Java template engine, is employed for dynamic and elegant server-side rendering of HTML templates. Basic **HTML5, CSS, and Bootstrap** form the core of the front-end technologies. Bootstrap, in particular, enhances the user interface with **responsive design** elements, ensuring a visually appealing and **user-friendly experience**.

Security:-
**Spring Security** has been employed to fortify the application, enabling **custom login pages** and safeguarding endpoints. The implementation extends further to incorporate **JWT with OAuth2 resource server** for enhanced user **authentication and authorization**.

Admin Page:-
Provides an **Admin Page** for performing CRUD operations for APOD, giving administrators control over the content.

Swagger Documentation:-
Utilizes **Swagger** for comprehensive documentation of the REST APIs, making it easy for developers to understand and integrate.

Exception Handling:-
The project demonstrates a commitment to reliability by incorporating custom **exception handling for endpoints**. This ensures graceful handling of errors, enhancing the overall resilience of the application.

Deployment:-
For deployment, the project leverages **Docker and AWS Elastic Beanstalk**, showcasing an infrastructure that is not only scalable but also ensures ease of management and deployment in the cloud.

Testing:-
Conducts **JUnit and Mockito and Integration Tests with Test Containers** on all layers of the project, ensuring reliability and robustness.

<hr style="width:75%">


## Demo
[**Try it out** 🌎](http://nasa-webapp-env.eba-bpm6gg2n.ap-south-1.elasticbeanstalk.com/nasa/home-page)

#### Home-Page
![](img/home-page.png)

#### Index-Page
![](img/index-page.png)

#### Astronomy Picture Of The Day( A P O D )
![](img/apod-page.png)

#### Mars Rover Page
![](img/mars-rover-page.png)

#### Admin Page
![](img/admin-page.png)

#### Swagger Documentation Page
![](img/swagger-doc-page.png)

#### Logout Page
![](img/logout-page.png)


<hr style="width:75%">

## Getting Started

To get started with the Nasa API Implementation project, follow these steps:--

Step-1:-
Download (git clone) the repository.

Step-2:--
Run MySQL as Docker container. An sql script must be executed during docker container and can be done with **docker-entrypoint-initdb.d.**
This sql script is for custom user and roles( authorities ) tables.
The table is used for logging into the application.( User name and password ).
**Sql scripts are there in the /nasaSqlScripts folder.**

`docker run --detach --env MYSQL_ROOT_PASSWORD=yourpassword --env MYSQL_USER=yourusername --env MYSQL_PASSWORD=yourpassword --env MYSQL_DATABASE=yourdatabaseName --name mysql --publish 3306:3306 --volume your-sql-script-path:/docker-entrypoint-initdb.d mysql:8-oracle`

Make sure to replace yourpassword with your desired user password.
Make sure to replace yourusername with your desired user name.
Make sure to replace yourdatabasename with your desired database schema name.
Make sure to replace your-sql-script-path where the 01.sql file is located.

`Example:- docker run --detach --env MYSQL_ROOT_PASSWORD=springbeta --env MYSQL_USER=spring --env MYSQL_PASSWORD=springbeta --env MYSQL_DATABASE=nasa-db --name mysql --publish 3306:3306 --volume D:\nasaSqlScripts:/docker-entrypoint-initdb.d mysql:8-oracle`


Step-3:--

Make changes for MySQL Connection in applications.properties file.
`spring.datasource.url=jdbc:mysql://localhost:3306/your-database-schema-name`.
Make sure to replace your-database-schema name with the database name you have used for MySQL Docker container.


Start the main spring-boot application 
( OR )
 `mvn clean package`
 `mvn spring-boot:run` 

Access the home-page in your browser:--
http://localhost:5000/nasa/home-page

Use the following credentials for logging in.
For guest users:--
`Username:-  guest`
`Password:-  guest@123`

For Admin user:--
`Username:- admin`
`Password: admin@123`

**NOTE:- 
You can login to the application only if the sql script which contains custom users and roles has been executed during running of docker MySQL container.**

<hr style="width:75%">

## SAMPLE Querying Through Endpoints
Mars Rover Endpoint:--
`http://localhost:5000/api/rover/{roverName}/{earthDate}/{roverCamera}`.

This endpoint will fetch the Nasa's Mars Rover Photos. 

Details are given below( have a look ) 

A.) There are 3 Mars Rovers 

   1.) Curiosity 
   
   2.) Spirit 
   
   3.) Opportunity 
   
B.) There are 9 Cameras For These Rovers 

   1.) FHAZ-> Front Hazard Avoidance Camera 
   
   2.) RHAZ-> Rear Hazard Avoidance Camera 
   
   3.) MAST-> Mast Camera 
   
   4.) CHEMCAM-> Chemistry and Camera Complex 
   
   5.) MAHLI-> Mars Hand Lens Imager 
   
   6.) MARDI-> Mars Descent Imager 
   
   7.) NAVCAM-> Navigation Camera
   
   8.) PANCAM-> Panoramic Camera 
   
   9.) MINITES-> Miniature Thermal Emission Spectrometer (Mini-TES) 
   
C.) Earth Date Is In The Form Of YYYY/MM/DD 

D.) You can fetch Mars photos based on the rovername, earthdate, rovercamera. 

   Ex:- `/rover/curiosity/2015-06-03/fhaz`
   

## Documentation

Explore the Swagger documentation to understand and integrate with the REST APIs. Access the documentation at `http://localhost:5000/swaggerdoc.html` .

#### NOTE
**If you access /swaggerdoc.html DIRECTLY without logging in, you can use the bearer token( JWT Authentication ).
To get JWT Bearer token, use the endpoint:--
`/get-token`
Ex:--
`http://localhost:5000/get-token`.
Alternatively you can also use normal credentials to access `http://localhost:5000/swaggerdoc.html`.**

Feel free to contribute, report issues, or provide feedback to make the Nasa API Implementation project even better! Happy coding! 🚀🌌
