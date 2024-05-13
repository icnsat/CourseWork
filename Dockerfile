FROM openjdk:19-alpine

WORKDIR /app

COPY . /app

EXPOSE 8084

CMD ["java", "-jar", "/app/gradle-build/libs/CourseWork-1.0-SNAPSHOT.jar"]


