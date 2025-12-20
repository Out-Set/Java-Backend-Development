# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the packaged JAR file into the container
COPY target/springboot-profiles.war /app/app.war

# Expose the port your application will run on
EXPOSE 8080

# Set an environment variable for Spring profiles (can be overridden at runtime)
ENV SPRING_PROFILES_ACTIVE=master

# Run the application with the active profiles
ENTRYPOINT ["java", "-jar", "/app/app.war"]

# The CMD allows passing dynamic profiles (will be overridden by Docker Compose if set there)
CMD ["--spring.profiles.active=${SPRING_PROFILES_ACTIVE}"]


# Note: (.)dot represents current directory
# Create-Docker-Image: docker build -t your-image-name .
# Example: docker build -t springboot-profiles .