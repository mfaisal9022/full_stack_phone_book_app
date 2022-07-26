FROM openjdk:17-jdk-alpine as backend
COPY target/Phone_Book_Full_Stack_Application-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT exec java -server -jar /app.jar