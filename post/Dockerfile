FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/post.jar post.jar
EXPOSE 8083
CMD ["java", "-jar", "post.jar"]