# Using the maven 3.9.1 image that includes Eclipse Temurin (Java) 17 on Alpine Linux as the base image
FROM maven:3.9.1-eclipse-temurin-17-alpine

# Create a new directory named 'project' in the image
RUN mkdir /project

# Copy all files from the current directory on the host to the 'project' directory in the image
COPY . /project

# Set the working directory for any subsequent instructions to '/project'
WORKDIR /project

# Run 'mvn clean package' command to build the project and skip the tests
RUN mvn clean package -DskipTests


# Start a new build stage and use the Eclipse Temurin (Java) 17 JRE on Alpine Linux as the base image
FROM eclipse-temurin:17-jre-alpine

# Create a new directory named 'app' in the image
RUN mkdir /app

# Create a new group named 'poppy', then create a new user 'poppy' and add it to the 'poppy' group
RUN addgroup -S poppy && adduser -S poppy -G poppy

# Copy the 'poppy-api.jar' file from the 'target' directory in the first stage to the 'app' directory in the image
COPY --from=0 /project/target/api-0.0.1-SNAPSHOT.jar /app/api-0.0.1-SNAPSHOT.jar

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
