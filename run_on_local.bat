@ECHO OFF
SET dir=%cd%

start java -jar "%dir%"\microservice-management\lib\microservice-management-0.0.1.jar
start java -jar "%dir%"\microservice-service\lib\microservice-service-0.0.1-SNAPSHOT.jar
start java -jar "%dir%"\microservice-security\lib\microservice-security-0.0.1-SNAPSHOT.jar