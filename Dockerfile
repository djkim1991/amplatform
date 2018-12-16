FROM maven:3.5-jdk-8-alpine as build
ADD pom.xml ./pom.xml
ADD src ./src
RUN mvn package -DskipTests=true

FROM java:8
COPY --from=build /target/amplatform-1.0.0.jar /app.jar
#ADD target/amplatform-1.0.0.jar app.jar
#RUN bash -c 'touch /app.jar'
#VOLUME /tmp
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]