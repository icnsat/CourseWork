FROM openjdk:19-alpine

WORKDIR /app

COPY . /app

EXPOSE 8084

ENTRYPOINT ["java", "-jar", "/app/build/libs/CourseWork-1.0-SNAPSHOT.jar"]

