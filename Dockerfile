FROM gradle:jdk17 as builder

COPY --chown=gradle:gradle . /home/gradle/src

WORKDIR /home/gradle/src

RUN gradle clean build -x test

FROM openjdk:17 as finalApp

ENTRYPOINT ["sudo", "mkdir", "/app/"]

WORKDIR app

COPY --from=builder /home/gradle/src/build/libs/Groza_Dominic-0.0.1-SNAPSHOT.jar /app

EXPOSE 8090

ENTRYPOINT ["java", "-jar", "/app/Groza_Dominic-0.0.1-SNAPSHOT.jar"]
