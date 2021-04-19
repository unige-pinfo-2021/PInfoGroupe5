#! /bin/bash


cd tests
mvn package
docker image build -t testIntegration:latest .
cd ..

sudo snap install microk8s --classic

sudo microk8s start
sudo microk8s enable dns

k8='sudo microk8s kubectl'


#${k8} create -f film/ServiceFilm.yml
#${k8} create -f group/ServiceGroup.yml
${k8} create -f user/ServiceUser.yml
${k8} create -f testIntegration/ServiceTest.yml

#${k8} create -f film/RepSetFilm.yml
#${k8} create -f group/RepSetGroup.yml
${k8} create -f user/RepSetUser.yml
${k8} create -f testIntegration/JobTest.yml
