# Use the JDK 7 image as the base
FROM jboss/base-jdk:7

MAINTAINER vpereira@redhat.com

# Install JBoss EAP
ENV EAP_VERSION 6.3.0
ENV EAP_MINOR_VERSION 6.3

# Add the JBoss EAP distribution to /opt, and make jboss the owner of the extracted tar content
# Remember to fetch and place the jboss eap distribution in the same directory as the docker file.
ADD ./jboss-eap-$EAP_VERSION.zip /opt/jboss/
USER root
# Install the MySQL driver
RUN yum install -y mysql-connector-java
RUN chown jboss /opt/jboss/jboss-eap-$EAP_VERSION.zip
USER jboss
WORKDIR /opt/jboss
RUN unzip jboss-eap-$EAP_VERSION.zip && mv jboss-eap-$EAP_MINOR_VERSION jboss-eap && rm jboss-eap-$EAP_VERSION.zip

# Add the start script
ADD customization/ /opt/jboss/jboss-eap/
USER root
RUN chmod +x /opt/jboss/jboss-eap/start.sh
RUN chmod +x /opt/jboss/jboss-eap/create-mysql-module.sh
USER jboss

# Set the JBOSS_HOME env variable
ENV JBOSS_HOME /opt/jboss/jboss-eap

RUN /opt/jboss/jboss-eap/create-mysql-module.sh

# Expose the ports we're interested in
EXPOSE 8080 9990

# Add the TicketMonster WAR to the deployments directory of JBoss EAP.
# Remember to place the TicketMonster WAR into the root directory before running the Docker build
# You can build it and place it in the root dir, using:
# mvn -f ../demo clean package && cp ../demo/target/ticket-monster.war .
ADD ./ticket-monster.war /opt/jboss/jboss-eap/standalone/deployments/

# Set the default command to run on boot
# This will boot JBoss EAP in the standalone mode with ha profile and bind to the local interface
CMD ["/opt/jboss/jboss-eap/start.sh"]