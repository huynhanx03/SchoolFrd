FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/config-server.jar config-server.jar
EXPOSE 9999
CMD ["java", "-jar", "config-server.jar"]