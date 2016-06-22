#!/bin/bash
#
`cd /var/lib/tomcat7/webapps/fabflix_test/source_XML_parsing/`
`sudo chmod 777 /var/lib/tomcat7/webapps/fabflix_test/source_XML_parsing/mysql-connector-java-5.0.8-bin.jar`
`javac -classpath .:./mysql-connector-java-5.0.8-bin.jar MovieXMLParse.java`
`javac -classpath .:./mysql-connector-java-5.0.8-bin.jar ActorsSaxHandler.java`
`javac -classpath .:./mysql-connector-java-5.0.8-bin.jar CastsSaxParser.java`
`java -classpath .:./mysql-connector-java-5.0.8-bin.jar  MovieXMLParse`
`java -classpath .:./mysql-connector-java-5.0.8-bin.jar ActorsSaxHandler`
`java -classpath .:./mysql-connector-java-5.0.8-bin.jar CastsSaxParser`