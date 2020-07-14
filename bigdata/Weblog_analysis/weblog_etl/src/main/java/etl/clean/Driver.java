package etl.clean;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import utils.HdfsUtil;

public class Driver {

    public static void main(String[] args) {
        try {
            Configuration conf = new Configuration();
            String HDFS = "hdfs://master:9000/";
            HdfsUtil hdfsUtil = new HdfsUtil(HDFS, conf);
            // 指定带运行参数的目录为输入输出目录
//			String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
//			if (otherArgs.length != 2) {
//				System.err.println("Usage: Data Deduplication <in> <out>");
//				System.exit(2);
//			}
            String inputPath = HDFS + "weblog/apachelogs/apache_simple.log";
            String outputPath = HDFS + "user/hive/warehouse/testdb.db/clickstream_log/dt=2019-05-28";
            Job job = Job.getInstance(conf);
            job.setJobName("clickstream_etl");
            job.setJarByClass(Driver.class);
            job.setMapperClass(ClickStreamMapper.class);
            job.setReducerClass(ClickStreamReducer.class);
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(Text.class);
            job.setOutputFormatClass(TextOutputFormat.class);
            job.setPartitionerClass(SessionIdPartitioner.class);
            job.setSortComparatorClass(SortComparator.class);
//			FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
//			FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
            hdfsUtil.rmr(outputPath);
            FileInputFormat.setInputPaths(job, new Path(inputPath));
            FileOutputFormat.setOutputPath(job, new Path(outputPath));
            System.exit(job.waitForCompletion(true) ? 0 : 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

// 1、先将Apache日志上传至HDFS的目录下，并按照时间进行存储
// 如：/tmp/apache_log/2017-07-12
//
// 2、将程序打包上传至Hadoop集群
// 如：/home/files/clickstream_etl.jar
//
// 3、如果是第一次执行，则需要先在Hive中创建clickstream_log表
// 如：
// hive> create table clickstream_log(
// > ipaddress string,
// > uniqueid string,
// > url string,
// > sessionid string,
// > sessiontimes int,
// > areaaddress string,
// > localaddress string,
// > browsertype string,
// > operationsys string,
// > referurl string,
// > receicetime bigint,
// > userid string,
// > csvp int)
// > partitioned by (dt string)
// > row format delimited fields terminated by '\t';
//
// 4、执行jar程序，将前一天的点击流日志进行数据清洗并输出至clickstream_log表相对应的HDFS路径下
// 如：hadoop jar /home/files/clickstream_etl.jar com.etl.clean.Driver
// 	  /tmp/apache_log/2017-07-12 /user/hive/warehouse/clickstream_log/dt=2017-07-12
