# import correct jre (17)
FROM amazoncorretto:17

# create working directory
WORKDIR /app

# copy fat jar file to docker container working directory
COPY target/todo-app-0.0.1-SNAPSHOT.jar app.jar

# run as non-root user (for security)
USER 10001:10001

# expose the app's port
EXPOSE 8082

# startup command
ENTRYPOINT ["java", "-jar", "/app/app.jar"]