FROM openjdk:8-jdk-alpine
COPY target/gmail-service.jar gmail-service.jar
ENTRYPOINT ["java", "-jar", "gmail-service.jar"]