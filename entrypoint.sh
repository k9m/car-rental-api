#!/bin/sh
APP_JAR=$(ls *.jar)
command="java -jar $APP_JAR --spring.profiles.active=docker --spring.security.ouath2.client.secret=$KEYCLOAK_SECRET"
echo $command
exec $command