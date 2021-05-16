FROM openjdk:17-ea-10-jdk-alpine3.13
ENV APP_HOME /opt
EXPOSE 8081
COPY target/*.jar $APP_HOME/app.jar
WORKDIR $APP_HOME
ENTRYPOINT ["sh", "-c"]
CMD ["exec java -jar app.jar"]