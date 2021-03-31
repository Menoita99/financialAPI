@ECHO OFF

docker pull menoita99/management_api:latest
docker pull menoita99/service_api:latest
docker pull menoita99/security_api:latest

start docker run -t menoita99/management_api:latest
start docker run -t menoita99/service_api:latest
start docker run -t menoita99/security_api:latest