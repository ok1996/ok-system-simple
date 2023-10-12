FROM openjdk:17-oracle
RUN sh -c 'touch /app.jar'
COPY @project.artifactId@-exec.jar app.jar
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]