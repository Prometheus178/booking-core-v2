FROM tomcat:jdk11

EXPOSE 8080

ADD server.xml /usr/local/tomcat/conf/
ADD tomcat-users.xml /usr/local/tomcat/conf/

ADD build/libs/booking-core-1.1-SNAPSHOT.war /usr/local/tomcat/webapps


CMD ["bin/catalina.sh", "run"]