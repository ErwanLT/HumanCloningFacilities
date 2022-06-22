FROM openjdk:11
EXPOSE 8080:8080
ADD target/human-6.0.0-SNAPSHOT.jar human-6.0.0-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/human-6.0.0-SNAPSHOT.jar"]
