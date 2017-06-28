[![Build Status](https://travis-ci.org/bootique-examples/bootique-linkmove-demo.svg)](https://travis-ci.org/bootique-examples/bootique-linkmove-demo)

# bootique-linkmove-demo

* [sync-database](https://github.com/bootique-examples/bootique-linkmove-demo/tree/master/sync-database) - an example of data synchronization from one database into another
* [sync-files-database](https://github.com/bootique-examples/bootique-linkmove-demo/tree/master/sync-files-database) - an example of data synchronization from files' data sources into a database

# Basic points

LinkMove is used to connect data models together in a flexible way. Here the following models are considered: 
source and target ones.
![Alt text](resources/bootique-linkmove-demo.png?raw=true)

*Note:* for the example **sync-files-database** source model is replaced by CSV and JSON files.

Usual way to synchronize data is jobs executed periodically. In order to put a batch of tasks into a job, add dependency on 
*bootique-job* into pom.xml:
```xml   
<dependency>
    <groupId>io.bootique.job</groupId>
    <artifactId>bootique-job</artifactId>
</dependency>
```

Describe source and targets in *config.yml*:
```yaml
jdbc:
  sourcedb:
    url: jdbc:mysql://localhost:3306/sourcedb?connectTimeout=0&autoReconnect=true
    driverClassName: com.mysql.jdbc.Driver
    initialSize: 1
    username: root
    password:
  targetdb:
      url: jdbc:mysql://localhost:3306/targetdb?connectTimeout=0&autoReconnect=true
      driverClassName: com.mysql.jdbc.Driver
      initialSize: 1
      username: root
      password:

cayenne:
  datasource: targetdb
  createSchema: true

linkmove:
  extractorsDir: /Users/your_user/bootique-linkmove-demo/sync-database #use absolute path
  connectorFactories: #name of source connector is taken from extractor.xml <connectorId>sourcedb</connectorId>
    - type: jdbc
```

Extractor XML format is described by a formal schema: http://linkmove.io/xsd/extractor_config_2.xsd

An example using JDBC connector for the source data:
```xml   
<?xml version="1.0" encoding="utf-8"?>
<config xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://linkmove.io/xsd/extractor_config_2.xsd"
        xmlns="http://linkmove.io/xsd/extractor_config_2.xsd">
    <type>jdbc</type>
    <connectorId>sourcedb</connectorId>
    <extractor>
        <attributes>
            <attribute>
                <type>java.lang.String</type>
                <source>id</source>
                <target>db:id</target>
            </attribute>
            <attribute>
                <type>java.lang.String</type>
                <source>name</source>
                <target>name</target>
            </attribute>
            <attribute>
                <type>java.lang.String</type>
                <source>domain_host</source>
                <target>vhost</target>
            </attribute>
        </attributes>
        <properties>
            <!--
             Query to run against the source. Supports full Cayenne
                             SQLTemplate syntax, including parameters and directives.
            -->
            <extractor.jdbc.sqltemplate>SELECT id, name, domain_host FROM s_domain</extractor.jdbc.sqltemplate>
            <extractor.jdbc.sqltemplate.caps>LOWER</extractor.jdbc.sqltemplate.caps>
        </properties>
    </extractor>
</config>
```
    
## Prerequisites

* Java 1.8 or newer.
* Apache Maven.

## Build the Demo

Here is how to build it:

	git clone git@github.com:bootique-examples/bootique-linkmove-demo.git
	cd bootique-linkmove-demo
	mvn package

## Run the Demo

Run **sync-files-database**:

    java -jar sync-files-database/target/sync-files-database-1.0-SNAPSHOT.jar --config=sync-files-database/config.yml --exec --job=sync

Run **sync-database**:

    java -jar sync-database/target/sync-database-1.0-SNAPSHOT.jar --config=sync-database/config.yml --exec --job=sync

   
For additional details, please, take a peek at README.md of each of these examples.
