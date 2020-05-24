FROM maven:3.6.0-jdk-11-slim

COPY ./target/status-tracking-0.0.1-SNAPSHOT.jar status-tracking.jar

EXPOSE 5300

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "status-tracking.jar"]



