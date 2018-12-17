FROM rabbitmq:3-management
ENV RABBITMQ_DEFAULT_VHOST /springboot
EXPOSE 5672
EXPOSE 15672

FROM redis
ENV RABBITMQ_DEFAULT_VHOST /springboot
EXPOSE 6379

FROM postgres
ENV POSTGRES_DB springboot
ENV POSTGRES_USER postgres
ENV POSTGRES_PASS pass
EXPOSE 5432

FROM maven:3.5-jdk-8-alpine as build
ADD pom.xml ./pom.xml
ADD src ./src
RUN mvn package -DskipTests=true


FROM java:8
COPY --from=build /target/amplatform-1.0.0.jar /app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]