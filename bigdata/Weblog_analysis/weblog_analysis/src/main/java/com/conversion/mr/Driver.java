package com.conversion.mr;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.util.ArrayList;

public class Driver {
    public static final String SEPARATOR = "@";

    public static void main(String[] args) throws Exception {
        Configuration configuration = new Configuration();
        if (args.length <= 2) {
            System.out.println("参数不完整，参数1：输入路径；参数2：输出路径；参数3：漏斗目标url");
            return;
        }

        String inputPath = args[0];
        String outputPath = args[1];

        //保存表示漏斗的url的正则表达式
        ArrayList<String> hoppers = new ArrayList<String>();
        for (int i = 2; i < args.length; i++) {
            hoppers.add(args[i]);
        }
        String urls = "";
        for (int i = 0; i < hoppers.size(); i++) {
            urls += hoppers.get(i);
            if (i != hoppers.size() - 1) {
                urls += SEPARATOR;
            }
        }

        //将漏斗URL保存到configuration对象中，供所有Map任务和Reduce任务使用
        configuration.set("urls", urls);

        Job job = Job.getInstance(configuration, "conversion");
        job.setJarByClass(Driver.class);
        FileInputFormat.addInputPath(job, new Path(inputPath));
        FileOutputFormat.setOutputPath(job, new Path(outputPath));
        job.setMapperClass(ConversionMapper.class);
        job.setReducerClass(ConversionReducer.class);
        //手动设置Reducer个数，需根据集群计算能力综合考虑
        job.setNumReduceTasks(4);
        job.setOutputFormatClass(TextOutputFormat.class);
        job.setPartitionerClass(ConversionPartitioner.class);
        job.setSortComparatorClass(ConversionComparator.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
