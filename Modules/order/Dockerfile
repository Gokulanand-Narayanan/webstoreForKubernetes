#FROM tomcat:8-jre8-alpine

#ADD target/order.war /usr/local/tomcat/webapps/ 

#CMD ["catalina.sh", "run"] 

FROM tomcat:8-jre8-alpine

COPY egbtm /opt/egbtm

ENV JAVA_OPTS="-javaagent:/opt/egbtm/eg_btm.jar"

ADD target/order.war /usr/local/tomcat/webapps/ 

CMD ["catalina.sh", "run"] 
