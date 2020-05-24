FROM maven:3.6.0-jdk-11-slim

COPY ./target/actor-registry-0.0.1-SNAPSHOT.jar actor-registry.jar

EXPOSE 5100

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "actor-registry.jar"]


