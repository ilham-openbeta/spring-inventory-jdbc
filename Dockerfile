FROM openjdk:8-jdk-buster as build
COPY . /
RUN chmod +x mvnw
RUN ./mvnw clean install

FROM openjdk:8-jre-buster
COPY --from=build /target/inventory-api-0.0.3-SNAPSHOT.jar /app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]