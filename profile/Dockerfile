FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/profile.jar profile.jar
EXPOSE 8081
CMD ["java", "-jar", "profile.jar"]