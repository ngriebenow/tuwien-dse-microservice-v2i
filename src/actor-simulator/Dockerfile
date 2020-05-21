FROM maven:3.6.0-jdk-11-slim

COPY ./target/actor-simulator-0.0.1-SNAPSHOT.jar actor-simulator.jar

EXPOSE 5200

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "actor-simulator.jar"]


