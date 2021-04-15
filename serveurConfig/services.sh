#! /bin/bash

k8='sudo microk8s kubectl'


${k8} create -f film/ServiceFilm.yml
${k8} create -f group/ServiceGroup.yml
${k8} create -f user/ServiceUser.yml
${k8} create -f web-ui/ServiceWebUi.yml

