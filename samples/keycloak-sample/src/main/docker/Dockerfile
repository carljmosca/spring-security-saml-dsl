FROM java:8

MAINTAINER Carl J. Mosca <carljmosca@gmail.com>

VOLUME /tmp

ADD keycloak-saml-demo-0.0.1-SNAPSHOT.jar app.jar
ENV JAVA_CACERTS /etc/ssl/certs/java/cacerts

# Add US Courts Private CA certs
ADD certs/US_COURTS_ROOT_CA.crt /etc/ssl/certs
ADD certs/US_COURTS_Private_SSL_CA.crt /etc/ssl/certs
ADD certs/tomcat.secure.crt /etc/ssl/certs
ADD keystore.jks /etc/ssl/certs/java/keystore.jks

RUN keytool -noprompt -storepass changeit -keystore ${JAVA_CACERTS} -import -file /etc/ssl/certs/US_COURTS_ROOT_CA.crt -alias uscourtsroot
RUN keytool -noprompt -storepass changeit -keystore ${JAVA_CACERTS} -import -file /etc/ssl/certs/US_COURTS_Private_SSL_CA.crt -alias uscourtsprivate
RUN keytool -noprompt -storepass changeit -keystore ${JAVA_CACERTS} -import -file /etc/ssl/certs/tomcat.secure.crt -alias tomcat

ENV SERVER_SSL_KEY-STORE=/etc/ssl/certs/java/keystore.jks

RUN groupadd --gid 1001 sboot
RUN useradd --uid 1001 -g sboot sboot

CMD java $JAVA_OPTIONS -Djava.security.egd=file:/dev/./urandom -jar /app.jar

USER 1001
