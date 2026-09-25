# Build stage
FROM maven:3.9.9-eclipse-temurin-21-alpine AS build
WORKDIR /app
# Copiamos la configuracion aislada y el POM
COPY pom.xml maven-central-settings.xml ./
RUN mvn dependency:go-offline -s maven-central-settings.xml -B

# Copiamos el codigo fuente y construimos el JAR
COPY src ./src
RUN mvn clean package -s maven-central-settings.xml -DskipTests

# Run stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/price-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
