FROM maven:3.6.0-jdk-11-slim

COPY ./target/api-gateway-0.0.1-SNAPSHOT.jar api-gateway.jar

EXPOSE 80

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "api-gateway.jar"]




