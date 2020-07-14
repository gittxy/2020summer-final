package etl.clean;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class SessionIdPartitioner extends Partitioner<Text, Text> {

	@Override
	public int getPartition(Text key, Text value, int parts) {
		String sessionId = "-";
		if (key != null) {
			sessionId = key.toString().split("&")[0];
		}
		int num = (sessionId.hashCode() & Integer.MAX_VALUE) % parts;
		return num;
	}

}
