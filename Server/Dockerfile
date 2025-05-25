FROM maven:3.9.9-sapmachine-21 AS builder

WORKDIR /app
COPY . /app
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim AS runtime

WORKDIR /app
COPY --from=builder /app/target/app-0.0.1-SNAPSHOT.jar /app

ENV DB_HOST=
ENV DB_PORT=
ENV DB_NAME=
ENV DB_USER=
ENV DB_PASSWORD=
ENV PORT=80
ENV IMAGE_URL_PREFIX=
ENV MINIO_HOST=
ENV MINIO_PORT=
ENV MINIO_KEY=
ENV MINIO_SECRET=

CMD ["java", "-jar", "-Dspring.profiles.active=container", "/app/app-0.0.1-SNAPSHOT.jar"]