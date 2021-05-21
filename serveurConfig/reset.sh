#! /bin/bash

k8='sudo microk8s kubectl'
${k8} delete rs --all

sudo docker rmi -f khptif/algorythmic_selector:latest
sudo docker rmi -f khptif/film:latest
sudo docker rmi -f khptif/user:latest
sudo docker rmi -f khptif/group:latest
sudo docker rmi -f khptif/web-ui:latest

${k8} create -f selector/RepSetSelector.yml
${k8} create -f film/RepSetFilm.yml
${k8} create -f group/RepSetGroup.yml
${k8} create -f user/RepSetUser.yml
${k8} create -f web-ui/RepSetWebUi.yml


