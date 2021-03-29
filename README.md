[![verify](https://github.com/bootique-examples/bootique-linkmove-demo/actions/workflows/verify.yml/badge.svg)](https://github.com/bootique-examples/bootique-linkmove-demo/actions/workflows/verify.yml)

# bootique-linkmove-demo

Here you can find some examples of [LinkMove](https://github.com/nhl/link-move) usage
in [Bootique](http://bootique.io/) app: 
* [sync-files-database](https://github.com/bootique-examples/bootique-linkmove-demo/tree/master/sync-files-database) - an example of data synchronization from files' data sources into a database;
* [sync-database](https://github.com/bootique-examples/bootique-linkmove-demo/tree/master/sync-database) - an example of data synchronization from one database into another.

# Basic points

* Both examples work with the same related models to be synchronized:
![Alt text](resources/bootique-linkmove-demo.png?raw=true)

* Run [sourcedb.sql](https://github.com/ebondareva/bootique-linkmove-demo/blob/master/resources/sourcedb.sql)
and [targetdb.sql](https://github.com/ebondareva/bootique-linkmove-demo/blob/master/resources/targetdb.sql) 
to create schemas from scratch ([MySQL](https://www.mysql.com/) database is used).

* In the examples data is synchronized via jobs executed periodically.
To do that, the dependency on *bootique-job* is added into the pom.xml:
```xml   
<dependency>
    <groupId>io.bootique.job</groupId>
    <artifactId>bootique-job</artifactId>
</dependency>
```

* Source and target are described in a *config.yml*. 
E.g. [sync-database/config.yml](https://github.com/ebondareva/bootique-linkmove-demo/blob/master/sync-database/config.yml): 
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
  extractorsDir: classpath:etl
  connectorFactories:
    - type: jdbc
```
* Extractor XML format is described by a formal schema: http://linkmove.io/xsd/extractor_config_2.xsd.
An example using JDBC connector for the source:
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

To to fill in the source database, run **sync-files-database** example first:

    java -jar sync-files-database/target/sync-files-database-1.0-SNAPSHOT.jar --config=sync-files-database/config.yml --exec --job=sync

Then run **sync-database**:

    java -jar sync-database/target/sync-database-1.0-SNAPSHOT.jar --config=sync-database/config.yml --exec --job=sync

   
**Note:** more detailed explanations you can find in the README.md of the examples.
