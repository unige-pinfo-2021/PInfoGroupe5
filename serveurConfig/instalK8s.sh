#! /bin/bash

sudo snap install microk8s --classic

sudo microk8s start
sudo microk8s enable dns

