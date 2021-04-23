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
#ssh-add key.txt # Add the private key to SSH
echo ${privateKey} | tr -d '\r' | ssh-add -

mkdir -p ~/.ssh
chmod 700 ~/.ssh
ssh-keyscan ${server} >> ~/.ssh/known_hosts
chmod 644 ~/.ssh/known_hosts

#ssh ${server} sudo ./serveurConfig/reset.sh

echo success
