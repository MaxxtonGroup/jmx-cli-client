jmx-cli-client
==============

A simple command line based JMX client. Fetches specific JMX attribute data from a JMX server and prints that to the default output.

## steps for Java client
- Compile and package with maven: mvn package
- Enable JMX on Tomcat in case you're using that
- Run for example against tomcat: java -cp ./jmx-cli-client.jar com.maxxton.jmx.client.SimpleJMXClient -t java.lang:type=Memory -a HeapMemoryUsage -c used -u service:jmx:rmi:///jndi/rmi://localhost:1096/jmxrmi
- You'll get an output in bytes like: JMXValue 3817865216

See our other repository for some sample scripts and templates to use this client to feed Zabbix.