FROM openjdk:11-jdk AS app-build

ENV GRADLE_OPTS -Dorg.gradle.daemon=false
COPY . /build
WORKDIR /build
RUN ./gradlew build