# Notes

```
# without maven
cd src/main/java/com/mycompany/app/
mkdir q3_classes
javac -classpath /usr/hdp/2.6.3.0-235/hadoop/hadoop-common.jar:/usr/hdp/2.6.3.0-235/hadoop/client/hadoop-mapreduce-client-core.jar:/usr/hdp/2.6.3.0-235/hadoop/lib/commons-cli-1.2.jar:/home/local/mdac073/commons-text-1.2.jar:/home/local/mdac073/commons-csv-1.5.jar -d q3_classes *.java
jar -cvf q3.jar -C q3_classes/ .
hadoop jar q3.jar com.mycompany.app.MailReader -files ~/LSDP/enron-project/src/main/resources/full-positions.csv -libjars /home/local/mdac073/commons-text-1.2.jar,/home/local/mdac073/commons-csv-1.5.jar enron/enron-seq-dataset enron_output
hdfs dfs -cat enron_output/byMonth/2001-10-r-00000.csv |  head


# with maven
mvn package
hadoop jar target/enron-project-3.0-SNAPSHOT.jar -files src/main/resources/full-positions.csv enron/enron-seq-dataset enron_output
hdfs dfs -cat enron_output/byMonth/2001-10-r-00000.csv |  head
```

# q3

This was mostly straightforward to write, however a bug I introduced by trying to compare Date objects instead of Calendar objects meant that all the records were being filtered. This was extremely confusing as the job ran without error but the reduce task did not run at all as there were no inputs. Once this was identified the setup of multiple outputs was fairly straightforward.
