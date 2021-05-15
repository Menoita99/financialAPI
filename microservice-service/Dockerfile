# FROM openjdk:17-ea-10-jdk-alpine3.13
FROM ubuntu:18.04
EXPOSE 8081

RUN apt-get update -y && \
  apt-get install -y python3.8 && \
  apt-get install -y python3-pip python3-dev && \
  apt-get install -y openjdk-11-jdk && \
  apt-get --reinstall install bash

WORKDIR /opt/python

COPY ./dataScience/requirements.txt /opt/python/requirements.txt

RUN pip3 install -r requirements.txt

COPY ./dataScience /opt/python

#COPY ./start.sh /opt
#RUN chmod +x /opt/start.sh

COPY target/*.jar /opt/java/app.jar

WORKDIR /opt/java

ENTRYPOINT ["sh", "-c"]
CMD ["cd /opt/python && (python3 main.py &) &&  cd /opt/java && java -jar app.jar"]
#CMD ["exec java -jar /opt/java/app.jar"]

