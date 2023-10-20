FROM bellsoft/liberica-openjdk-alpine
COPY target/spring-HW17-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 9092
ENTRYPOINT ["java", "-jar", "app/app.jar"]