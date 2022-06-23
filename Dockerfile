FROM openjdk:11
EXPOSE 8080:8080
ADD target/human-7.0.0-SNAPSHOT.jar human-7.0.0-SNAPSHOT.jar
ENTRYPOINT ["java","-Dspring.profiles.active=docker","-jar","/human-7.0.0-SNAPSHOT.jar"]
