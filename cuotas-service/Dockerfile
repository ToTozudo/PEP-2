FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} cuotas-service.jar
ENTRYPOINT ["java","-jar","/cuotas-service.jar"]