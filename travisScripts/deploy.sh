  #! /bin/bash
  
  
  sudo snap install microk8s --classic
  sudo apt-get install sshpass
  sshpass -p ${serverPassword} ssh -q -o StrictHostKeyChecking=no "echo ${serverPassword} | sudo -S ./serveurConfig/reset.sh
