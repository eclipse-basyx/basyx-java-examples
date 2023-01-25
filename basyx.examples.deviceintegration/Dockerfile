FROM openjdk:11-slim-bullseye

# Install dependency for wait-for-it-env.sh & wget for health check
RUN apt update && apt install -y jq && apt clean
RUN apt install -y wget

# Copy built jar to image using the jar name specified in the pom.xml (JAR_FILE)
ARG JAR_FILE

COPY target/basyx.examples.deviceintegration-0.0.1-SNAPSHOT.jar /usr/share/basyxExecutable.jar
COPY target/lib /usr/share/lib

EXPOSE 8082

# Start the jar
CMD java -jar "/usr/share/basyxExecutable.jar"