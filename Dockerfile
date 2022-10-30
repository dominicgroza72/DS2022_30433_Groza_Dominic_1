FROM gradle:4.7.0-jdk17-alpine AS build
COPY ./src/ /root/src
COPY ./build.gradle /root/

WORKDIR /root
RUN gradle build --no-daemon

FROM openjdk:17.0.5-jre

EXPOSE 8088

ENV DB_PORT = 3306
ENV DB_USER = root
ENV DB_PASSOWRD=''
ENV DB_DBNAME='assignment1_dsd'

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/*.jar /app/spring-boot-application.jar

ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-Djava.security.egd=file:/dev/./urandom","-jar","/app/spring-boot-application.jar"]