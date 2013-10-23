#!/bin/bash
rm /d/server/tomcat-7.0.35/webapps/p11y.war
rm -rf /d/server/tomcat-7.0.35/webapps/p11y
cp target/p11y.war /d/server/tomcat-7.0.35/webapps/