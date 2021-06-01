FROM khptif/mvnjava:latest

COPY filmChooser /filmChooser
COPY services/serviceGroupe /serviceGroupe

WORKDIR /filmChooser
RUN mvn package
RUN mvn clean install

WORKDIR /serviceGroupe
RUN mvn package

EXPOSE 2000

CMD ["mvn","exec:java"]
