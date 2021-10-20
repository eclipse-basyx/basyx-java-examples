# Add java runtime environment for execution
FROM java:8-jdk-alpine 

# Copy built jar to image using the jar name specified in the pom.xml (JAR_FILE)
ARG JAR_FILE
COPY target/${JAR_FILE} /usr/share/basyxExecutable.jar
COPY target/lib /usr/share/lib
COPY src/main/resources/context.properties /usr/share/config/context.properties
COPY src/main/resources/wrapper.properties /usr/share/config/wrapper.properties

# Expose the appropriate port. In case of Tomcat, this is 8080.
ARG PORT
EXPOSE ${PORT}

# Set the path for the context configuration file
ARG CONTEXT_CONFIG_KEY
ENV ${CONTEXT_CONFIG_KEY} "/usr/share/config/context.properties"

# Set the path for the context configuration file
ENV BASYX_WRAPPER "/usr/share/config/wrapper.properties"
 
# Start the jar
CMD java -jar "/usr/share/basyxExecutable.jar"