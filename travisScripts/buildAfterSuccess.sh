#!/bin/bash

echo ${DockerPassword} | docker login --username ${DockerUsername} --password-stdin

docker tag api/user khptif/user:latest 
docker tag api/film khptif/film:latest 

docker push khptif/user:latest
docker push khptif/film:latest

sudo apt-get install openssh-server
sudo apt-get install sshpass
#sshpass -p ${serverPassword}
cd
echo ${privateKey} > ./ssh/id_rsa
echo ${publicKey} > ./ssh/id_rsa.pub

echo yes | ssh ${server} sudo ./serveurConfig/reset.sh

echo success
