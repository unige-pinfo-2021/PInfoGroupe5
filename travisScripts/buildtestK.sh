#!/bin/bash

# construit les images des différents services
# docker image build -t servicefilm -f services/serviceFilm/Dockerfile .
# docker image build -t servicegroupe -f services/serviceGroupe/Dockerfile .
# docker image build -t serviceuser -f services/serviceUser/Dockerfile .

mvn install -Ppackage-docker-image

# construit le web-ui

cd web-ui

apt-get update
apt-get install nodejs

npm install
npm update
npm run-script build --prod

docker image build -t web-ui .

cd ..
    
# connection au docker hub
echo ${DockerPassword} | docker login --username ${DockerUsername} --password-stdin


# change les noms des images et les envoies au docker hub
#docker tag servicefilm khptif/servicefilm:latest
#docker tag servicegroupe khptif/servicegroupe:latest
#docker tag serviceuser khptif/serviceuser:latest
#docker tag web-ui khptif/web-ui:latest 

#docker image ls

docker tag api/user khptif/user:testintegration
docker tag api/film khptif/film:testintegration

#docker push khptif/servicefilm:latest
#docker push khptif/servicegroupe:latest
#docker push khptif/serviceuser:latest
#docker push khptif/web-ui:latest

docker push khptif/user:testintegration
docker push khptif/film:testintegration


