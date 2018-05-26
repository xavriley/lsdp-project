# Notes

```
hdfs dfs -put /home/local/mdac073/enron20.seq enron/enron20.seq

# without maven
wget http://central.maven.org/maven2/org/apache/commons/commons-csv/1.5/commons-csv-1.5.jar # note location for later
cd src/main/java/com/mycompany/app/
mkdir q2_classes
javac -classpath /usr/hdp/2.6.3.0-235/hadoop/hadoop-common.jar:/usr/hdp/2.6.3.0-235/hadoop/client/hadoop-mapreduce-client-core.jar:/usr/hdp/2.6.3.0-235/hadoop/lib/commons-cli-1.2.jar:/home/local/mdac073/commons-text-1.2.jar:/home/local/mdac073/commons-csv-1.5.jar -d q2_classes *.java
jar -cvf q2.jar -C q2_classes/ .
hadoop jar q2.jar com.mycompany.app.MailReader -files ~/LSDP/enron-project/src/main/resources/full-positions.csv -libjars /home/local/mdac073/commons-text-1.2.jar,/home/local/mdac073/commons-csv-1.5.jar enron/enron20.seq enron_output


mvn package
hadoop jar target/enron-project-1.0-SNAPSHOT.jar enron/enron20.seq enron_output
hdfs dfs -cat enron_output/part-r-00000
```

# q2

Ran into issue when running on cluster as nullPointer exception was thrown.

Noticed that the code only validated from address as being enron.com

Eyeballed the sample data email addresses with this grep command

    $ grep -a -E -o "\b[a-zA-Z0-9.-]+@[a-zA-Z0-9.-]+\.[a-zA-Z0-9.-]+\b" src/test/java/resources/enron20.seq

Showed that `Francois.Badenhorst@eskom.co.za` was a non-enron recipient. Added test to check for
return value of that lookup and restructured map task to make the checks more explicit.

## verifying test run

Running the task on the sample of 20 emails succeeded but only outputted 22 edges. Given that there 
were 286 email addresses in the full-positions.csv file and 33 distinct email addresses in the sample enron20.seq file I had doubts about whether 22 edges was the correct number.

Checking the email addresses in common between the two files showed only 5 results

```
$ join <(grep -a -E -o "\b[a-zA-Z0-9.-]+@[a-zA-Z0-9.-]+\.[a-zA-Z0-9.-]+\b" src/test/java/resources/full-positions.csv | sort) <(grep -a -E -o "\b[a-zA-Z0-9.-]+@[a-zA-Z0-9.-]+\.[a-zA-Z0-9.-]+\b" src/test/java/resources/enron20.seq | sort ) | uniq
george.mcclellan@enron.com
jeffrey.shankman@enron.com
mark.rodriguez@enron.com
mike.mcconnell@enron.com
stuart.staley@enron.com
```

Cross referencing these with the employee ids from the output for the task gave me more confidence that this was indeed the correct output.
