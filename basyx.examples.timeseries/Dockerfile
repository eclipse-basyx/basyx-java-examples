FROM maven:3-openjdk-17 AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN cd /usr/src/app/ && \
    mvn clean install

FROM gcr.io/distroless/java17-debian11
COPY --from=build /usr/src/app/target/timeseriessubmodel.jar /usr/app/timeseriessubmodel.jar
COPY --from=build /usr/src/app/target/lib /usr/app/lib
ENTRYPOINT ["java","-jar","/usr/app/timeseriessubmodel.jar"]
