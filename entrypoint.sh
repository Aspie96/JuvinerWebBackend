#!/bin/sh

if [[ -z "${IS_HEROKU}" ]]
then
    n=0
    while [ true ]
    do
        ((r=$(wget -nv http://user:password124@eureka:8761/actuator/health -O-) && (echo "$r" | jq --exit-status '.status == "UP"')) || exit 1) && break
        n=$((n+1))
        if [ $n -ge 5 ]
        then
            exit 1
        else
            sleep 15
        fi
    done
fi
java -Xmx300m -Xss512k -XX:+UseContainerSupport -jar /app.jar
