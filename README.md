# Notes

```
mvn package
hadoop jar target/enron-project-3.0-SNAPSHOT.jar -files src/main/resources/full-positions.csv enron/enron-seq-dataset enron_output
hdfs dfs -cat enron_output/byMonth/2001-10-r-00000.csv |  head
```

# q3

This was mostly straightforward to write, however a bug I introduced by trying to compare Date objects instead of Calendar objects meant that all the records were being filtered. This was extremely confusing as the job ran without error but the reduce task did not run at all as there were no inputs. Once this was identified the setup of multiple outputs was fairly straightforward.
