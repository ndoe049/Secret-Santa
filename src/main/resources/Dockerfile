FROM openjdk:17-alpine

# Don't run as root
#RUN addgroup -S spring && adduser -S spring -G spring
#USER spring:spring

# The ARG instruction defines a variable that users can pass at build-time to the builder with the docker build
# command using the --build-arg <varname>=<value> flag.
ARG BUILD_DATE
ARG PROJECT_NAME
ARG POM_VERSION

# The LABEL instruction adds metadata to an image.
# Set a bunch of labels. http://label-schema.org/rc1/
LABEL org.label-schema.build-date="${BUILD_DATE}" \
      org.label-schema.name="${PROJECT_NAME}" \
      org.label-schema.schema-version="1.0" \
      org.label-schema.version="${POM_VERSION}"

# Copy jars
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]