# 1. Use an official Java 17 runtime as the base image
FROM eclipse-temurin:17-jre

# 2. Set the working directory inside the container
WORKDIR /app

# 3. Copy the compiled JAR file from the target directory into the container
# Maven usually puts the built jar in the target/ folder
COPY target/*.jar app.jar

# 4. Expose the port your Spring app runs on (default is usually 8080)
EXPOSE 8080

# 5. Tell Docker how to run your application
ENTRYPOINT ["java", "-jar", "app.jar"]
