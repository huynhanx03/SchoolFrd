FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/monitor-admin.jar monitor-admin.jar
EXPOSE 9000
CMD ["java", "-jar", "monitor-admin.jar"]