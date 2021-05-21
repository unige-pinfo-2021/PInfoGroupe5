
brew install curl

/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install.sh)"

brew cask install docker

brew install kubernetes-cli

brew install terraform

brew install helm

brew install maven

echo "Les versions"
echo "" && echo "Git" && echo ""
git --version
echo "" && echo "DOCKER" && echo ""
docker -v
echo "" && echo "kubernetes" && echo ""
kubeadm version
echo ""
kubectl version
echo "" && echo "Terraform" && echo ""
terraform -version
echo "" && echo "Helm" && echo ""
helm version
echo "" && echo "Maven" && echo ""
mvn -version
