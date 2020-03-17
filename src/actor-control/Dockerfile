FROM maven:3.6.0-jdk-11-slim
COPY src src
COPY pom.xml pom.xml

EXPOSE 5000
CMD mvn clean compile spring-boot:run


