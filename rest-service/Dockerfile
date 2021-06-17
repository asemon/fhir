FROM jboss/wildfly:15.0.0.Final

COPY ./src/main/resources/jboss/modules/ /opt/jboss/wildfly/modules/
COPY ./src/main/resources/jboss/standalone.xml /opt/jboss/wildfly/standalone/configuration/
ADD /target/restservice.war /opt/jboss/wildfly/standalone/deployments/
RUN /opt/jboss/wildfly/bin/add-user.sh admin pass --silent