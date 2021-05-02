#! /bin/bash



cd tests
mvn package
cd ..
sudo apt update -y
sudo apt-get install -y apt-utils
sudo apt install snapd
sudo snap install core snapd
sudo snap refresh 
sudo snap install microk8s --classic 

sudo microk8s start
sudo microk8s enable dns

k8='sudo microk8s kubectl'

${k8} delete rs --all
${k8} delete svc --all

${k8} create -f film/ServiceFilm.yml
${k8} create -f group/ServiceGroup.yml
${k8} create -f user/ServiceUser.yml


${k8} create -f film/RepSetFilm.yml
${k8} create -f group/RepSetGroup.yml
${k8} create -f user/RepSetUser.yml

sleep 60

${k8} get pods
cd tests

javac App.java
java App
cd ..
