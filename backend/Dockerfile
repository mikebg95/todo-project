# ---------- Build stage ----------
FROM maven:3.9-eclipse-temurin-25 AS build
WORKDIR /src

# cache dependencies first
COPY pom.xml .
RUN mvn -q -B -DskipTests dependency:go-offline

# build the app
COPY src ./src
RUN mvn -q -B -DskipTests package

# ---------- Runtime stage ----------
FROM eclipse-temurin:25-jre
WORKDIR /app

# copy the built jar from the build stage
COPY --from=build /src/target/*.jar /app/app.jar

# run as non-root
USER 10001:10001

EXPOSE 8082
ENTRYPOINT ["sh","-c","java -Dserver.port=${PORT:-8082} -jar /app/app.jar"]