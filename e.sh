#!/bin/sh

n=0
until [ $n -ge 5 ]
do
    ((r=$(wget -nv http://user:password124@eureka:8761/actuator/health -O-) && (echo "$r" | jq --exit-status '.status == "UP"')) || exit 1) && break
    n=$((n+1))
    sleep 15
done
java -Xmx300m -Xss512k -XX:+UseContainerSupport -jar /app.jar
