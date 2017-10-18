# cmt-web-api
Cassandra monitoring tool integrated with spring boot
Provides basic node information (similar to nodetool status) via JMX.

Start with -Dconf=path\to\config\cmt.yaml
Use POST request to / with json payload {"token":"tokenFromConfigFile"}

Tested with apache cassandra 3.7

How to enable JMX connection on cassandra: 
https://docs.datastax.com/en/cassandra/2.1/cassandra/security/secureJmxAuthentication.html

//TO-DO
- add automated repairs
- more information about cluster
- improve overall project structure
- add tests
