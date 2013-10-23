#!/bin/bash
mvn clean
mvn -o -Dmaven.test.skip=true package
