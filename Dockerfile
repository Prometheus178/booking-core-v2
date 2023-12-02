FROM tomcat:10-jdk17-corretto

EXPOSE 8080

ADD server.xml /usr/local/tomcat/conf/
ADD tomcat-users.xml /usr/local/tomcat/conf/

ADD build/libs/booking-core-1.0-SNAPSHOT.war /usr/local/tomcat/webapps

CMD ["catalina.sh", "run"]