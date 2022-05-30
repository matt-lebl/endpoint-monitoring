FROM maven:3.8.5-openjdk-17 as build
COPY src /src
COPY pom.xml pom.xml
RUN mvn clean package -Dmaven.test.skip

FROM eclipse-temurin:17-jre-alpine
WORKDIR /monitoring/
COPY --from=build /target/endpoint-monitoring.jar .
ENTRYPOINT ["java", "-jar", "endpoint-monitoring.jar"]
