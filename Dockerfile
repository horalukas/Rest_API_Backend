FROM openjdk:14-jdk AS app-build

RUN chmod -R 777 ./gradlew
ENV GRADLE_OPTS -Dorg.gradle.daemon=false
COPY . /build
WORKDIR /build
RUN ./gradlew build