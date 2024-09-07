FROM openjdk:17-jdk-slim-buster
WORKDIR /app
COPY target/GymApp.war /app/app.war
ENTRYPOINT ["java", "-jar", "/app/app.war"]

