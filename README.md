# Notes

```
hdfs dfs -put /home/local/mdac073/enron20.seq enron/enron20.seq

# no maven
cd src/main/java/com/mycompany/app/
mkdir q1_classes
javac -classpath /usr/hdp/2.6.3.0-235/hadoop/hadoop-common.jar:/usr/hdp/2.6.3.0-235/hadoop/client/hadoop-mapreduce-client-core.jar:/usr/hdp/2.6.3.0-235/hadoop/lib/commons-cli-1.2.jar:/home/local/mdac073/commons-text-1.2.jar -d q1_classes *.java
 jar -cvf q1.jar -C q1_classes/ .
hadoop jar q1.jar com.mycompany.app.MailReader enron/enron20.seq enron_output

# with maven
mvn package
hadoop jar target/enron-project-1.0-SNAPSHOT.jar enron/enron20.seq enron_output
hdfs dfs -cat enron_output/part-r-00000
```
