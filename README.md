# Notes

```
hdfs dfs -put /home/local/mdac073/enron20.seq enron/enron20.seq

mvn package
hadoop jar target/enron-project-1.0-SNAPSHOT.jar enron/enron20.seq enron_output
hdfs dfs -cat enron_output/part-r-00000
```
