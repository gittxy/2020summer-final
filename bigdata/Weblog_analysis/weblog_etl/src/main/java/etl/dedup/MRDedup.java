package etl.dedup;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class MRDedup {
	// map将输入中的value复制到输出数据的key上，并直接输出
	public static class Map extends Mapper<Object, Text, Text, Text> {
		private static Text line = new Text();

		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			line = value;
			context.write(line, new Text(""));
		}
	}

	// reduce将输入中的key复制到输出数据的key上，并直接输出，这正是数据去重的思想
	public static class Reduce extends Reducer<Text, Text, Text, Text> {
		// 实现reduce函数
		public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
			context.write(key, new Text(""));
		}
	}

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		// 指定带运行参数的目录为输入输出目录
		// String[] otherArgs = new GenericOptionsParser(conf, args)
		// .getRemainingArgs();
		// if (otherArgs.length != 2) {
		// System.err.println("Usage: Data Deduplication <in> <out>");
		// System.exit(2);
		// }
		String inputPath = "hdfs://192.168.244.21:9000/user/hive/warehouse/bx_orders/dt=2017-07-11";
		String outputPath = "hdfs://192.168.244.21:9000/data/warehouse/orders";
		Job job = Job.getInstance(conf);
		job.setJobName("Data Deduplication");
		job.setJarByClass(MRDedup.class);
		job.setMapperClass(Map.class);
		job.setCombinerClass(Reduce.class);
		job.setReducerClass(Reduce.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		// FileInputFormat.setInputPaths(job, new Path(otherArgs[0]));
		// FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
		FileInputFormat.setInputPaths(job, new Path(inputPath));
		FileOutputFormat.setOutputPath(job, new Path(outputPath));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}

}
