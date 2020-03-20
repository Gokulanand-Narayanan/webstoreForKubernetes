FROM store/oracle/serverjre:8

COPY target/gateway.jar /app/
WORKDIR /app
ENTRYPOINT ["java", "-jar", "gateway.jar"]
