FROM java:openjdk-8-jdk
EXPOSE 8080
COPY maven /
ENTRYPOINT java -Djava.net.preferIPv4Stack=true -jar \
						   /opt/user-thorntail.jar
