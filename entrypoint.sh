#!/bin/sh
APP_JAR=$(ls *.jar)
command="java -jar $APP_JAR --spring.profiles.active=mock --spring.security.ouath2.client.secret=$KEYCLOAK_SECRET"
echo $command
exec $command