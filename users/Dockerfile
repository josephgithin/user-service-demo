FROM adoptopenjdk/openjdk11:x86_64-alpine-jdk-11.0.14.1_1-slim
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]