@ECHO OFF
SET dir=%cd%

start java -jar "%dir%"\microservice-management\target\microservice-management-0.0.1.jar
start java -jar "%dir%"\microservice-service\target\microservice-service-0.0.1-SNAPSHOT.jar
start java -jar "%dir%"\microservice-security\target\microservice-security-0.0.1-SNAPSHOT.jar