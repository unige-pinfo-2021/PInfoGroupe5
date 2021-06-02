FROM khptif/mvnjava:latest

COPY filmChooser /filmChooser
COPY services/serviceUser /serviceUser

WORKDIR /filmChooser
RUN mvn package
RUN mvn clean install

WORKDIR /serviceUser
RUN mvn package

EXPOSE 2000

CMD ["mvn","exec:java"]
