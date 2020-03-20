FROM maven:3-jdk-11-openj9
MAINTAINER Torsten Spieldenner <torsten.spieldenner@dfki.de>

LABEL Description="This image is used to start a Sparql Wrapping service from the locally provided sourcefiles"

COPY ./ /home/sparql-wrapper 
RUN cd /home/sparql-wrapper \
    && mvn clean install org.springframework.boot:spring-boot-maven-plugin:repackage

EXPOSE 8080

WORKDIR /home/sparql-wrapper/target/
CMD java -jar sparql-api-wrapper-1.0-SNAPSHOT.jar

