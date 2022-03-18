FROM openjdk:11-jdk-stretch

ENV HOME=/backend

USER root

WORKDIR $HOME

COPY . $HOME

RUN ./gradlew clean bootJar --no-daemon

EXPOSE 8080