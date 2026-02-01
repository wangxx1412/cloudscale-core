# Step 1: Build the application using Maven
FROM maven:3.8.5-openjdk-17 AS build
COPY . /app
WORKDIR /app
# Speed up builds by skipping tests during the image creation
RUN mvn clean package -DskipTests

# Step 2: Run the application
# Using a more compatible base image for Apple Silicon
FROM eclipse-temurin:17-jre
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
# Standard entrypoint for Spring Boot
ENTRYPOINT ["java", "-jar", "/app.jar"]