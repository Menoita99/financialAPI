@ECHO OFF

echo Don't forget to run "docker login"
echo Starting processing microservice-management

cd microservice-management
call mvn clean compile package
del lib\microservice-management-0.0.1.jar
copy target\microservice-management-0.0.1.jar lib
echo Building image microservice-management
docker build -t menoita99/management_api:latest .
echo Pushing image microservice-management
docker push menoita99/management_api:latest
echo Finished microservice-management



cd ../microservice-service
echo Building image microservice-service
call mvn clean compile package
del lib\microservice-service-0.0.1-SNAPSHOT.jar
copy target\microservice-service-0.0.1-SNAPSHOT.jar lib
docker build -t menoita99/service_api:latest .
echo Pushing image microservice-service
docker push menoita99/service_api:latest
echo Finished microservice-service



cd ../microservice-security
echo Building image microservice-security
call mvn clean compile package
del lib\microservice-security-0.0.1-SNAPSHOT.jar
copy target\microservice-security-0.0.1-SNAPSHOT.jar lib
docker build -t menoita99/security_api:latest .
echo Pushing image microservice-security
docker push menoita99/security_api:latest
echo Finished microservice-security


pause