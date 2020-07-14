package etl.simple;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import utils.HdfsUtil;

public class WebLogDriver {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        String HDFS = "hdfs://master:9000/";
        HdfsUtil hdfsUtil = new HdfsUtil(HDFS, conf);

//		String[] otherArgs = null;
//		try {
//			otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
//			if (otherArgs.length != 2) {
//				System.err.println("Usage:wordcount<in><out>");
//				System.exit(1);
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

//        conf.addResource("classpath:/core-site.xml");
//        conf.addResource("classpath:/hdfs-site.xml");
//        conf.addResource("classpath:/mapred-site.xml");
//        conf.addResource("classpath:/yarn-site.xml");

        try{
            Job job = Job.getInstance(conf);
            job.setJarByClass(WebLogDriver.class);
            job.setJobName("WebLog ETL");
            job.setMapperClass(WebLogMapper.class);
            job.setReducerClass(WebLogReducer.class);
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(Text.class);

//			FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
//			FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
            String inputPath = HDFS + "weblog/coollogs/in/coolshell_20140212.log";
            String outputPath = HDFS +"weblog/coollogs/out/";
            hdfsUtil.rmr(outputPath);
            FileInputFormat.addInputPath(job, new Path(inputPath));
            FileOutputFormat.setOutputPath(job, new Path(outputPath));
            System.exit(job.waitForCompletion(true) ? 0 : 1);
//            job.submit();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
