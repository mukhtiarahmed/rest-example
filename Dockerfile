FROM openjdk:8-jdk-alpine

VOLUME /tmp
EXPOSE 8080
COPY target/assignment.jar app.jar
RUN sh -c 'touch /app.jar'
ENTRYPOINT java  -Djava.security.egd=file:/dev/./urandom -DPAGE_SIZE=10  -DDATABASE_URL=jdbc:postgresql://assignment_db:5432/assignment_local -DDATABASE_USER=postgres  -DDATABASE_SEC=docker -DLOG_PATH=./log  -jar app.jar

