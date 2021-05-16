@ECHO OFF

call mvn clean package

del lib\microservice-service-0.0.1-SNAPSHOT.jar
copy target\microservice-service-0.0.1-SNAPSHOT.jar lib

docker build -t menoita99/service_api:latest .

docker run -p 8081:8081 menoita99/service_api:latest

