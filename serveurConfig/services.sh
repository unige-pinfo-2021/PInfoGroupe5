#! /bin/bash

k8='sudo microk8s kubectl'

${k8} create -f selector/ServiceSelector.yml
${k8} create -f film/ServiceFilm.yml
${k8} create -f group/ServiceGroup.yml
${k8} create -f user/ServiceUser.yml
${k8} create -f web-ui/ServiceWebUi.yml

${k8} create -f selector/RepSetSelector.yml
${k8} create -f film/RepSetFilm.yml
${k8} create -f group/RepSetGroup.yml
${k8} create -f user/RepSetUser.yml
${k8} create -f web-ui/RepSetWebUi.yml

