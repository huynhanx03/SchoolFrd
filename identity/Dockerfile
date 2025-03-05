FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/identity.jar identity.jar
EXPOSE 8080
CMD ["java", "-jar", "identity.jar"]