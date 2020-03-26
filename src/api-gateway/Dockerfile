FROM maven:3.6.0-jdk-11-slim
COPY src src
COPY pom.xml pom.xml

EXPOSE 8080
CMD mvn clean compile spring-boot:run -Dspring-boot.run.profiles=prod


