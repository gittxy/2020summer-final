package com.clustering;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.mahout.clustering.classify.WeightedPropertyVectorWritable;
import org.apache.mahout.clustering.classify.WeightedVectorWritable;
import org.apache.mahout.math.RandomAccessSparseVector;
import org.apache.mahout.math.Vector;

public class ClusterOutput {
	private static final String HDFS = "hdfs://ecs-hadoop-master:9000/";

	public static void main(String[] args) {

		try {
			// 需要被解析的mahout的输出文件
			String clusterOutputPath = "/user/hdfs/kmeans/output";
			// 解析后的聚类结果，将输出至本地磁盘
			String resultPath = "/root/BookShopBI/temp/result";

			BufferedWriter bw;
			Configuration conf = new Configuration();
			conf.set("fs.default.name", HDFS);
			FileSystem fs = FileSystem.get(conf);

			SequenceFile.Reader reader = null;
			reader = new SequenceFile.Reader(fs, new Path(clusterOutputPath + "/clusteredPoints/part-m-00000"), conf);
			bw = new BufferedWriter(new FileWriter(new File(resultPath)));

			// key为聚簇中心ID
			IntWritable key = new IntWritable();
			WeightedPropertyVectorWritable value = new WeightedPropertyVectorWritable ();

			while (reader.next(key, value)) {
				// 得到向量
				Vector vector =value.getVector();
				String vectorValue = "";

				// 将向量各个维度拼接成一行，用\t分隔
				for (int i = 0; i < vector.size(); i++) {
					if (i == vector.size() - 1) {
						vectorValue += vector.get(i);
					} else {
						vectorValue += vector.get(i) + "\t";
					}
				}

				bw.write(key.toString() + "\t" + vectorValue + "\n");
			}

			bw.flush();
			reader.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
