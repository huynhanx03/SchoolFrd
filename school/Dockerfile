FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/school.jar school.jar
EXPOSE 8085
CMD ["java", "-jar", "school.jar"]