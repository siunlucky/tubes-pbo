FROM openjdk:23-ea-jdk

EXPOSE 8080

ADD target/tubes-0.0.1-SNAPSHOT.jar tubes-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","/tubes-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=sync"]