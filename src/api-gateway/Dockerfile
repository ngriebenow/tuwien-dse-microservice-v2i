FROM maven:3.6.0-jdk-11-slim
COPY src src
COPY pom.xml pom.xml

EXPOSE 80
CMD mvn clean compile spring-boot:run -Dspring.profiles.active=prod


