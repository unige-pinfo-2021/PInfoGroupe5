#!/bin/bash

echo ${DockerPassword} | docker login --username ${DockerUsername} --password-stdin

docker tag api/user khptif/user:latest
docker tag api/user khptif/group:latest
docker tag api/film khptif/film:latest 
docker tag selector/algorythmic_selector khptif/algorythmic_selector:latest

docker push khptif/user:latest
docker push khptif/group:latest
docker push khptif/film:latest
docker push khptif/algorythmic_selector:latest

sudo apt-get install openssh-server
sudo apt-get install sshpass


echo ${privateKey} > key.txt
chmod 600 key.txt

#eval "$(ssh-agent -s)" # Start ssh-agent cache
#sshpass -p "" ssh-add key.txt # Add the private key to SSH

#ssh ${server} sudo ./serveurConfig/reset.sh

sshpass -p ${passwd} ssh -o StrictHostKeyChecking=no ${server} sudo ./serveurConfig/reset.sh

echo success
