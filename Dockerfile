FROM openjdk:17-jdk-alpine as backend
COPY target/Phone_Book_Full_Stack_Application-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT exec java -server -jar /app.jar

FROM node
WORKDIR /app
COPY src/main/webapp/phone_book_react_ui/package.json .
RUN npm install
COPY src/main/webapp/phone_book_react_ui .
EXPOSE 3000
CMD ["npm","start"]
