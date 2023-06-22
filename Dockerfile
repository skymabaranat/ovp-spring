# syntax=docker/dockerfile:1
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY . .

RUN ./gradlew clean build --no-daemon

CMD ["./gradlew", "bootRun"]
