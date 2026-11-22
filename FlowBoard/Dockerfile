FROM gradle:9.9.1-jdk25-noble AS build
WORKDIR /app

COPY . .

RUN gradle build -x test --no-daemon

FROM eclipse-temurin:25-jre
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]