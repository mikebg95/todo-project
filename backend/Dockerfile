# backend/Dockerfile

# ---------- Build stage ----------
FROM maven:3.9-amazoncorretto-17 AS build
WORKDIR /src

# cache deps first
COPY pom.xml .
RUN mvn -q -B -DskipTests dependency:go-offline

# build the app
COPY src ./src
RUN mvn -q -B -DskipTests package

# ---------- Runtime stage ----------
FROM amazoncorretto:17
WORKDIR /app

# copy the built jar from the build stage
# (matches whatever your artifact name is)
COPY --from=build /src/target/*.jar /app/app.jar

# run as non-root (same as you had)
USER 10001:10001

EXPOSE 8082
ENTRYPOINT ["java","-jar","/app/app.jar"]
