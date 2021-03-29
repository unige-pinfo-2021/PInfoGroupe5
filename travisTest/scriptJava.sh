#!/bin/bash

adresse=travisTest

javac ./$(adresse)/hello1.java
javac ./$(adresse)/hello2.java

java ./$(adresse)/hello1
java ./$(adresse)/hello2
