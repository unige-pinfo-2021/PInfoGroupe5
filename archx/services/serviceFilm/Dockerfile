FROM khptif/mvnjava:latest

COPY filmChooser /filmChooser
COPY services/serviceFilm /serviceFilm

WORKDIR /filmChooser
RUN mvn package
RUN mvn clean install

WORKDIR /serviceFilm
RUN mvn package

EXPOSE 2000

CMD ["mvn","exec:java"]
