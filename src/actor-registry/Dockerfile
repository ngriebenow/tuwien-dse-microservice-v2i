#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim
COPY src src
COPY pom.xml pom.xml


EXPOSE 8080
RUN mvn clean compile spring-boot:run

#
# Package stage
#
#FROM openjdk:11-jre-slim
#COPY --from=build /home/app/target/demo-0.0.1-SNAPSHOT.jar /usr/local/lib/demo.jar
#EXPOSE 8080
#ENTRYPOINT ["java","-jar","/usr/local/lib/demo.jar"]


