FROM maven:3.6.3-ibmjava-8-alpine
WORKDIR /app
COPY pom.xml .
RUN mvn clean package -Dmaven.main.skip -Dmaven.test.skip && rm -r target
COPY src ./src
RUN mvn clean package -Dmaven.test.skip

FROM openjdk:8-jdk-alpine
RUN apk add jq
COPY --from=0 /app/target/*.jar app.jar
COPY entrypoint.sh entrypoint.sh
RUN chmod a+x entrypoint.sh
ENTRYPOINT ./entrypoint.sh
