FROM openjdk:11-jdk-buster
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]
ENTRYPOINT ["java","-jar","/app.jar" , "--spring.config.location=file:///etc/config/application.properties"]
