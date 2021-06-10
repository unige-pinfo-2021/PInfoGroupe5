#!/bin/bash



javac travisTest/hello1.java
javac travisTest/hello2.java

java travisTest/hello1
java travisTest/hello2

docker image build -t testjava1:1 travisTest
docker container run -ti testjava1:1


