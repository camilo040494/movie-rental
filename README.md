# Movie rental application
## Requirements
	To build this it is required:
	- JDK 11
	- Maven 3.5+

## Run this project
	To run this project it is required to have PostgreSQL 11

## Default admin user
	- username: test@correo.com
	- password: admin

## Swagger
	After being authenticated you may access swagger URL (localhost:8080/swagger-ui.html) to see documentation of endpoints.

## Dockerize

	To build the environment please follow the following commands with **Docker** installed

### Build the project
	```
	mvn clean package
	cp target/movie-rental-0.0.1-SNAPSHOT.jar src/main/docker
	cd src/main/docker
	docker-compose up
	```
	