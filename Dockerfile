#FROM maven:3.6.3-jdk-11-slim AS build
#RUN mkdir /project
#COPY . /project
#WORKDIR /project
#RUN mvn clean package -DskipTests
 
#FROM adoptopenjdk/openjdk11:jre-11.0.9.1_1-alpine
#RUN apk add dumb-init
#RUN mkdir /app
#RUN addgroup --system javauser && adduser -S -s /bin/false -G javauser javauser
#COPY --from=build /project/target/challenge-backend-api.jar /app/java-application.jar
#WORKDIR /app
#RUN chown -R javauser:javauser /app
#USER javauser
#CMD "dumb-init" "java" "-jar" "java-application.jar"


FROM openjdk:19-alpine3.16

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw clean package -DskipTests

RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

WORKDIR /app/target

ENTRYPOINT java -jar challenge-backend-api.jar

