FROM anapsix/alpine-java:8_server-jre_unlimited
ENV JAVA_OPTS="-Xms64m -Xmx512m -Djava.net.preferIPv4Stack=true"
COPY ./build/libs/*.jar ./
COPY entrypoint.sh ./
EXPOSE 8080
ENTRYPOINT ["/bin/bash", "/entrypoint.sh"]