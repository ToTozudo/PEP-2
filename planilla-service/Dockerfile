FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} planilla-service.jar
ENTRYPOINT ["java","-jar","/planilla-service.jar"]