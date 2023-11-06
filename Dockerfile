FROM tomcat
COPY build/libs/booking-core-1.0-SNAPSHOT.war /usr/local/tomcat/webapps
CMD ["catalina.sh", "run"]
