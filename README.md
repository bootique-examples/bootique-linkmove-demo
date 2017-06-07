[![Build Status](https://travis-ci.org/bootique-examples/bootique-linkmove-demo.svg)](https://travis-ci.org/bootique-examples/bootique-linkmove-demo)

# bootique-linkmove-demo

An example of data synchronization with [Linkmove](https://github.com/nhl/link-move) built on [Bootique](http://bootique.io).

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

Check the options available in your app dependent on *bootique-linkmove*:

    java -jar target/bootique.linkmove.demo-1.0-SNAPSHOT.jar

    OPTIONS
      -c yaml_location, --config=yaml_location
           Specifies YAML config location, which can be a file path or a URL.

      -h, --help
           Prints this message.

      -H, --help-config
           Prints information about application modules and their configuration
           options.
           
Usual way to synchronize data is jobs executed periodically. To put batch of tasks into a job and run it add dependency on *bootique-job*:
  
    <dependency>
        <groupId>io.bootique.job</groupId>
        <artifactId>bootique-job</artifactId>
    </dependency>

Check the options now:

    java -jar target/bootique.linkmove.demo-1.0-SNAPSHOT.jar
    
    OPTIONS
      -c yaml_location, --config=yaml_location
           Specifies YAML config location, which can be a file path or a URL.

      -e, --exec
           Executes one or more jobs. Jobs are specified with '--job' options

      -h, --help
           Prints this message.

      -H, --help-config
           Prints information about application modules and their configuration
           options.

      -j job_name, --job=job_name
           Specifies the name of the job to run with '--exec'. Available job
           names can be viewed using '--list' command.

      -l, --list
           Lists all jobs available in the app

      --schedule
           Schedules and executes jobs according to configuration. Waits
           indefinitely on the foreground.

      --serial
           Enforces sequential execution of the jobs, specified with '--job'
           options.  


In a few words things happen in the way: 
* [Cayenne](https://cayenne.apache.org) ORM is used for the target database. 
* Transforming data with extractors described in XML (e.g. [domain-extractor.xml](https://github.com/bootique-examples/bootique-linkmove-demo/blob/master/domain-extractor.xml) ) 
and listeners (e.g. [ArticleListener](https://github.com/bootique-examples/bootique-linkmove-demo/blob/master/src/main/java/io/bootique/linkmove/demo/SyncJob.java)).
* YAML configuration file defines data sources and target source. To extend LinkMove stack LinkMoveBuilderCallback can be used, 
e.g. articles connector is provided by callback 
(see [ArticleCallBack](https://github.com/bootique-examples/bootique-linkmove-demo/blob/master/src/main/java/io/bootique/linkmove/demo/ArticleCallBack.java)).

YAML configuration:

    jdbc:
      derby:
        url: jdbc:derby:target/demodb;create=true
        driverClassName: org.apache.derby.jdbc.EmbeddedDriver
        initialSize: 1
    
    cayenne:
      datasource: derby
      createSchema: true
    
    linkmove:
      connectorFactories:
        - type: jdbc
        - type: uri
          connectors:
            domainSourceConnector: file:///Users/elenabondareva/bootique-linkmove-demo/domain.json
            tagSourceConnector: file:///Users/elenabondareva/bootique-linkmove-demo/tag.csv

Run the job:
    
    java -jar target/bootique.linkmove.demo-1.0-SNAPSHOT.jar --config=config.yml --exec --job=sync

Result report:

    ...
        Domains:{Task=CreateOrUpdateTask:<ExtractorName: domain-extractor.xml.default_extractor>, Status=finished, Duration=386, Extracted=3, Created=3, Updated=0, Deleted=0}
        Articles:{Task=CreateOrUpdateTask:<ExtractorName: article-extractor.xml.default_extractor>, Status=finished, Duration=42, Extracted=3, Created=3, Updated=0, Deleted=0}
        Tags:{Task=CreateOrUpdateTask:<ExtractorName: tag-extractor.xml.default_extractor>, Status=finished, Duration=29, Extracted=3, Created=3, Updated=0, Deleted=0}
