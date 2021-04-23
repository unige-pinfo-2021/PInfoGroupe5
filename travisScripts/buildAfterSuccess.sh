#!/bin/bash

echo ${DockerPassword} | docker login --username ${DockerUsername} --password-stdin

docker tag api/user khptif/user:latest 
docker tag api/film khptif/film:latest 

docker push khptif/user:latest
docker push khptif/film:latest

sudo apt-get install openssh-server
sudo apt-get install sshpass


echo ${privateKey} > key.txt
chmod 600 key.txt

eval "$(ssh-agent -s)" # Start ssh-agent cache
ssh-add key.txt # Add the private key to SSH


ssh -o "StrictHostKeyChecking no" ${server} sudo ./serveurConfig/reset.sh

echo success
