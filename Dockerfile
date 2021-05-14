FROM openjdk:11-jre-slim-buster
ENTRYPOINT java -jar /deploy/ged-0.0.1-SNAPSHOT.jar
EXPOSE 8080