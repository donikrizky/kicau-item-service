FROM openjdk:8-jdk-alpine

COPY target/item-service-0.0.1-SNAPSHOT.jar /app/item-service.jar

CMD ["java", "-jar", "/app/item-service.jar"]