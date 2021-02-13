FROM maven:3.6.3-ibmjava-8-alpine
WORKDIR /app
COPY pom.xml .
RUN mvn clean package -Dmaven.main.skip -Dmaven.test.skip && rm -r target
COPY src ./src
RUN mvn clean package -Dmaven.test.skip

FROM openjdk:8-jdk-alpine
CMD apk get jq
COPY --from=0 /app/target/*.jar app.jar
ENTRYPOINT ["java","-Xmx300m","-Xss512k","-XX:+UseContainerSupport","-jar","/app.jar"]
