FROM openjdk:17-jdk-slim as builder

# Get gradle distribution
COPY *.gradle gradle.* gradlew /application/
COPY gradle /application/gradle
WORKDIR /application
RUN ./gradlew --version

COPY . .
RUN ./gradlew --no-daemon clean bootJar -x test

FROM gcr.io/distroless/java17-debian11

COPY --from=builder /application/build/libs/isab-1.0-SNAPSHOT.jar /bin/runner/run.jar
WORKDIR /bin/runner

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "run.jar"]