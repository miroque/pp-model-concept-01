###
#    Build Layer
###
FROM openjdk:17 as build

WORKDIR /workspace/app

COPY mvnw .
COPY .mvn/ .mvn
COPY pom.xml .
COPY src src
RUN ./mvnw package -DskipTests


###
#    Quarkus Layer
###
FROM registry.access.redhat.com/ubi8/openjdk-17:1.14

ENV LANG='en_US.UTF-8' LANGUAGE='en_US:en'

ARG BLD=/workspace/app

ARG USER_ID

USER root
RUN usermod -u $USER_ID -o jboss
RUN chown -R jboss:root /usr/local/s2i/
RUN chown -R jboss:root /deployments/

# # We make four distinct layers so if there are application changes the library layers can be re-used
COPY --from=build --chown=jboss ${BLD}/target/quarkus-app/lib/ /deployments/lib/
COPY --from=build --chown=jboss ${BLD}/target/quarkus-app/*.jar /deployments/
COPY --from=build --chown=jboss ${BLD}/target/quarkus-app/app/ /deployments/app/
COPY --from=build --chown=jboss ${BLD}/target/quarkus-app/quarkus/ /deployments/quarkus/

EXPOSE 8080

USER jboss

ENV JAVA_OPTS="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"
ENV JAVA_APP_JAR="/deployments/quarkus-run.jar"
