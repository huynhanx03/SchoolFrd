FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/eureka.jar eureka.jar
EXPOSE 8761
CMD ["java", "-jar", "eureka.jar"]