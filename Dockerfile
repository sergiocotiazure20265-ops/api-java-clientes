FROM maven:3.9-eclipse-temurin-25 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn -B dependency:go-offline

COPY src ./src
RUN mvn -B clean package && cp target/*.jar app.jar

FROM eclipse-temurin:25-jre

WORKDIR /app

RUN groupadd --system spring && useradd --system --gid spring spring

COPY --from=build /app/app.jar app.jar

EXPOSE 8081

USER spring

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
