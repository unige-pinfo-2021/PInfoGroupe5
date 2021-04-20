#!/bin/bash

echo ${DockerPassword} | docker login --username ${DockerUsername} --password-stdin

docker tag api/user khptif/user:latest 
docker tag api/film khptif/film:latest 

docker push khptif/user:latest
docker push khptif/film:latest

$ sudo apt-get install sshpass
$ sshpass -p ${serverPassword} ssh -t ${server} "cd serveurConfig; sudo ./reset.sh"
