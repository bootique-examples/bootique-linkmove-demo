# Synchronization between databases

An example of data synchronization from one database into another. 

*For additional help/questions about this example send a message to
[Bootique forum](https://groups.google.com/forum/#!forum/bootique-user).*

## Prerequisites

* Java 1.8 or newer.
* Apache Maven.

## Build the Demo

Here is how to build it:

	git clone git@github.com:bootique-examples/bootique-linkmove-demo.git
	cd bootique-linkmove-demo
	mvn package

## Run the Demo

Check the options available in your app:

    java -jar sync-database/target/sync-database-2.0.jar

    OPTIONS
      -c yaml_location, --config=yaml_location
           Specifies YAML config location, which can be a file path or a URL.

      -e, --exec
           Executes one or more jobs. Jobs are specified with '--job' options

      -h, --help
           Prints this message.

      -H, --help-config
           Prints information about application modules and their configuration options.

      -j job_name, --job=job_name
           Specifies the name of the job to run with '--exec'. Available job names can be viewed using '--list' command.

      -l, --list
           Lists all jobs available in the app

      --schedule
           Schedules and executes jobs according to configuration. Waits indefinitely on the foreground.

      --serial
           Enforces sequential execution of the jobs, specified with '--job' options.

Define source and target databases in *config.yml*:
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

[Cayenne](https://cayenne.apache.org) is non-separable part of LinkMove as an ORM for the target database.  
  
*cayenne-project.xml*:
```xml       
<?xml version="1.0" encoding="utf-8"?>
<domain project-version="9">
    <map name="datamap"/>
    <!--skip data node declaration - config.yml is used-->
<!--
    <node name="datanode"
         factory="org.apache.cayenne.configuration.server.XMLPoolingDataSourceFactory"
         schema-update-strategy="org.apache.cayenne.access.dbsync.SkipSchemaUpdateStrategy"
        >
        <map-ref name="datamap"/>
        <data-source>
            <driver value="com.mysql.jdbc.Driver"/>
            <url value="jdbc:mysql://localhost:3306/targetdb?connectTimeout=0&amp;autoReconnect=true"/>
            <connectionPool min="1" max="1"/>
            <login userName="root"/>
        </data-source>
    </node>-->
</domain>
```

Transforming data is performed by extractors described in XML (e.g. [domain-extractor.xml](https://github.com/bootique-examples/bootique-linkmove-demo/blob/master/sync-database/domain-extractor.xml)). 

Run the job:
    
    java -jar sync-database/target/sync-database-2.0.jar --config=sync-database/config.yml --exec --job=sync

Result report:

    ...
    Domains:{Task=CreateOrUpdateTask:<ExtractorName: domain-extractor.xml.default_extractor>, Status=finished, Duration=137, Extracted=3, Created=3, Updated=0, Deleted=0}
    Articles:{Task=CreateOrUpdateTask:<ExtractorName: article-extractor.xml.default_extractor>, Status=finished, Duration=39, Extracted=3, Created=3, Updated=0, Deleted=0}
    Tags:{Task=CreateOrUpdateTask:<ExtractorName: tag-extractor.xml.default_extractor>, Status=finished, Duration=20, Extracted=3, Created=3, Updated=0, Deleted=0}
        