# Build Stage
FROM maven:3.9.8-eclipse-temurin-21 AS build
# Current directory is /backend-build
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean package -DskipTests

# Runtime Stage
FROM openjdk:21-jdk-slim
# Define the params, environment variables
ARG PROFILE=dev
ARG APP_VERSION=1.0.1

# Current directory is /app
WORKDIR /app
# Copy the jar file from the build stage
COPY --from=build /build/target/book-network-*.jar /app/

#Expose the port
EXPOSE 9000

ENV DB_URL=jdbc:mysql://mysql-sql-bsn:3306/book_social_network

# Set the profile as an environment variable
ENV JAR_VERSION=${APP_VERSION}
ENV ACTIVE_PROFILE=${PROFILE}

CMD ["java", "-jar", "-Dspring.profiles.active=${ACTIVE_PROFILE}", "-Dspring.datasource.url=${DB_URL}", "book-network-1.0.1.jar"]
