
FROM openjdk:17-jdk-alpine

COPY target/websocket-service-0.0.1-SNAPSHOT.jar /app/websocket.jar

# -------------------------------
# Set working directory
# -------------------------------
WORKDIR /app

EXPOSE 8090

ENTRYPOINT ["java", "-jar", "websocket.jar"]
