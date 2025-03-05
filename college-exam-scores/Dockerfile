FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/college-exam-scores.jar college-exam-scores.jar
EXPOSE 8084
CMD ["java", "-jar", "college-exam-scores.jar"]