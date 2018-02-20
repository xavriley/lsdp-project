package com.mycompany.app;

import java.io.IOException;
import java.util.TimeZone;
import java.util.Date;
import java.text.SimpleDateFormat;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.mapreduce.Reducer;

class MailReaderReducer extends Reducer<EdgeWritable, NullWritable, NullWritable, Text> {
	// You can put instance variables here to store state between iterations of
	// the reduce task.

	private final Text out = new Text();
	private final NullWritable noval = NullWritable.get();
	private final SimpleDateFormat dateOutputKey = new SimpleDateFormat("yyyy-MM");
	private MultipleOutputs<NullWritable, Text> multipleOutputs;

	// The setup method. Anything in here will be run exactly once before the
	// beginning of the reduce task.
	public void setup(Context context) throws IOException, InterruptedException {
		multipleOutputs = new MultipleOutputs<NullWritable, Text>(context);
	}

	// The reducer will emit the edges (email, email, timestamp)
	// with timestamp being formatted as a normal date string
	// relative to the UTC time zone.
	public void reduce(EdgeWritable key, Iterable<NullWritable> values, Context context)
			throws IOException, InterruptedException {
		dateOutputKey.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
		
		String dateKey = dateOutputKey.format(key.getTS());
		out.set(key.get(0) + "," + key.get(1));
		multipleOutputs.write(noval, out, "byMonth/" + dateKey);
	}

	// The cleanup method. Anything in here will be run exactly once after the
	// end of the reduce task.
	public void cleanup(Context context) throws IOException, InterruptedException {
		multipleOutputs.close();
	}
}
