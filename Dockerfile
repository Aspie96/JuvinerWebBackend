FROM openjdk:8-jdk-alpine
RUN apk add jq
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
