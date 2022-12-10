FROM openjdk:17-alpine

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw install -DskipTests

RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

WORKDIR /app/target

ENTRYPOINT java -jar challenge-backend-api.jar

