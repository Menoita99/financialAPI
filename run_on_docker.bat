@ECHO OFF

docker pull menoita99/management_api:latest
docker pull menoita99/service_api:latest
docker pull menoita99/security_api:latest

start docker run -p=8080:8080 -t menoita99/management_api:latest 
start docker run -p=8081:8081 -t menoita99/service_api:latest
start docker run -p=8082:8082 -t menoita99/security_api:latest