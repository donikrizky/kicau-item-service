FROM maven:3.6-jdk-8 as maven
WORKDIR /app
COPY ./pom.xml ./pom.xml
RUN mvn dependency:go-offline -B
COPY ./src ./src

# TODO: jollof-* should be replaced with the proper prefix
RUN mvn package && cp target/item-service-0.0.1-SNAPSHOT.jar app.jar

# Rely on Docker's multi-stage build to get a smaller image based on JRE
FROM openjdk:8-jre-alpine
WORKDIR /app
COPY --from=maven /app/app.jar ./app.jar