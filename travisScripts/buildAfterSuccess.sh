#!/bin/bash

echo ${DockerPassword} | docker login --username ${DockerUsername} --password-stdin

docker tag api/user khptif/user:latest 
docker tag api/film khptif/film:latest 

docker push khptif/user:latest
docker push khptif/film:latest

sudo apt-get install openssh-server
sudo apt-get install sshpass


echo ${privateKey} > key.txt
chmod 400 key.txt


sshpass -p "" ssh -o "StrictHostKeyChecking no" sudo ./serveurConfig/reset.sh

echo success
