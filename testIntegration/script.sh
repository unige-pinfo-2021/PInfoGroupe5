#! /bin/bash


cd tests
mvn package
docker image build -t testintegration:latest .
cd ..

sudo snap install microk8s --classic --channel=1.21

sudo microk8s start
sudo microk8s enable dns

k8='sudo microk8s kubectl'

${k8} delete rs --all
${k8} delete svc --all

${k8} create -f film/ServiceFilm.yml
#${k8} create -f group/ServiceGroup.yml
${k8} create -f user/ServiceUser.yml


${k8} create -f film/RepSetFilm.yml
#${k8} create -f group/RepSetGroup.yml
${k8} create -f user/RepSetUser.yml

sleep 30

${k8} get pods
cd tests/target/classes

java integration/App
