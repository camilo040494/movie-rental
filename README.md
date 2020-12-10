# Movie rental application
## Requirements
	To build this it is required:
	- JDK 11
	- Maven 3.5+
	
## Build
	Run:
	```
	mvn clean package
	```

## Default admin user
	- username: test@correo.com
	- password: admin
	
	Please keep in mind, to perform like, rent and buy operations, it is not requested an user because it will take the local user

## Swagger
	After being authenticated you may access swagger URL (localhost:8080/swagger-ui.html) to see documentation of endpoints.

## Run locally

### Using Maven
	To run this project it is required to have PostgreSQL 11, run the file script.sql inside path src/main/docker in the postgres database
	Then, set the SPRING_DATASOURCE_URL to point the running instance of Postgres
	Finally, run (after build the project)
	```
	mvn spring-boot:run
	```
	The application will be deployed at port 8080
	
### Using Docker

	To build the environment please follow the following commands with **Docker** installed

#### Build the project
	After building the project, please run:
	```
	cp target/movie-rental-0.0.1-SNAPSHOT.jar src/main/docker
	cd src/main/docker
	docker-compose up
	```
	