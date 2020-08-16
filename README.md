# GlobalLogic - technical exercise

Simple spring-boot project as a GlobalLogic test, for user management

# Built With

* Gradle - Dependency Management
* JDK - Java™ Platform, Standard Edition Development Kit.
* Spring Boot - Framework to ease the bootstrapping and development of new Spring Applications.
* Spring Security - provides support for using Spring Security with OAuth (1a) and OAuth2 using standard Spring and Spring Security programming models and configuration idioms.
* H2 - Database embedded GUI console for browsing the contents of a database and running SQL queries. 
* git - Free and Open-Source distributed version control system.
* Lombok - Never write another getter or equals method again, with one annotation your class has a fully featured builder, Automate your logging variables, and much more.

# Running the application locally

* Open Command Prompt and Change directory (cd) to folder containing build.gradle.
* Run the following command to install the dependencies.

gradle

* Run the following command to run the application.

gradle bootRun

* With an application that allows the creation of rest requests such as Postman or SoapUI, 
  execute the following request, to be able to log in as a System Administrator user to be able 
  to generate an access jwt to be able to access the other urls.

url: http://localhost:8080/oauth/token

Authorization 
	Basic Auth 
		Username: application
		Password: anyApplication
		
Header
	Content-Type: application/x-www-form-urlencoded
		
Body
	username: admin@system.com
	password: Administrador20
	grant_type: password
	

Result: {
		"access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1OTc2MTg0ODYsInVzZXJfbmFtZSI6ImFkbWluQHN5c3RlbS5jb20iLCJhdXRob3JpdGllcyI6WyJST0xFX0FETUlOIl0sImp0aSI6IjdkOWUzZDkyLTdjZDEtNGE2MC04ZTM3LWIxMDJkZmI2OWI1ZiIsImNsaWVudF9pZCI6ImFwcGxpY2F0aW9uIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl19.gF4gsBlsJkYf9qYSZYqo9GVHmBl83DR3nzJOlLUmFd0"
	}
	
* The application exposes all the enpoint for the management of users, as listed below:

Authorization
	Bearer: access_token previously generated

GET http://localhost:8080/users List all users
GET http://localhost:8080/users/{userId} Find user by id
POST http://localhost:8080/users Create a user
PUT http://localhost:8080/users/{userId} Update a user
DELETE http://localhost:8080/users/{userId} Remove a user

# Running unit tests locally

* As all the endpoind of the application are protected, in order to execute the implemented unit tests
  it is necessary to modify the security configuration of the urls.
 
  In the ResourceServerConfig class, found in the following package com.app.userservice.security, 
  de-document line 26 and comment on lines 27 and 28. Once the unit tests have been verified, 
  return the class as it was before so that the application work as expected.

