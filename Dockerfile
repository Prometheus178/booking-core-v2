FROM openjdk:17-jdk-slim

WORKDIR /opt/app

ARG JAR_FILE=booking-core-1.1-SNAPSHOT.jar
ARG JAR_FILE_SOURCE=build/libs/${JAR_FILE}

COPY ${JAR_FILE_SOURCE}  ${JAR_FILE}

ENTRYPOINT ["java","-jar","booking-core-1.1-SNAPSHOT.jar"]