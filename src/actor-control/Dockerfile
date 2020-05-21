FROM maven:3.6.0-jdk-11-slim


COPY ./target/actor-control-0.0.1-SNAPSHOT.jar actor-control.jar

EXPOSE 5000

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "actor-control.jar"]



