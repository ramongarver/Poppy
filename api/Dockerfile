# Use the Eclipse Temurin (Java) 17 JRE on Alpine Linux as the base image
FROM eclipse-temurin:17-jre-alpine

# Create a new directory named 'app' in the image
RUN mkdir /app

# Create a new group named 'poppy', then create a new user 'poppy' and add it to the 'poppy' group
RUN addgroup -S poppy && adduser -S poppy -G poppy

# Copy the 'poppy-api.jar' file from the host to the 'app' directory in the image
COPY ./target/api-0.0.1-SNAPSHOT.jar /app/api-0.0.1-SNAPSHOT.jar

# Set the working directory for any subsequent instructions to '/app'
WORKDIR /app

# Change the owner of the '/app' directory to the 'poppy' user and group
RUN chown -R poppy:poppy /app

# Switch to the 'poppy' user for any subsequent instructions
USER poppy

# Assign the spring profile to be used when running the container
ARG SPRING_PROFILE
ENV SPRING_PROFILES_ACTIVE=$SPRING_PROFILE

# Run the Java application when a container is launched from the image
CMD ["java", "-jar", "api-0.0.1-SNAPSHOT.jar"]
