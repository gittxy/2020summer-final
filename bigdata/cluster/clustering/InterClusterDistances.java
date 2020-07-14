package com.clustering;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Writable;
import org.apache.mahout.clustering.Cluster;
import org.apache.mahout.clustering.iterator.ClusterWritable;
import org.apache.mahout.common.distance.DistanceMeasure;
import org.apache.mahout.common.distance.EuclideanDistanceMeasure;

public class InterClusterDistances {
	private static final String HDFS = "hdfs://10.20.0.10:9000";

	public static void main(String[] args) throws Exception {
		String inputFile = HDFS + "/user/hdfs/kmeans/output";
		System.out.println("聚类结果文件地址：" + inputFile);

		Configuration conf = new Configuration();
		Path path = new Path(inputFile + "/clusters-2-final/part-r-00000");
		System.out.println("Input Path：" + path);

		FileSystem fs = FileSystem.get(path.toUri(), conf);
		List<Cluster> clusters = new ArrayList<Cluster>();

		SequenceFile.Reader reader = new SequenceFile.Reader(fs, path, conf);
		Writable key = (Writable) reader.getKeyClass().newInstance();
		ClusterWritable value = (ClusterWritable) reader.getValueClass().newInstance();

		while (reader.next(key, value)) {
			Cluster cluster = value.getValue();
			clusters.add(cluster);
			value = (ClusterWritable) reader.getValueClass().newInstance();
		}

		System.out.println("Cluster In Total：" + clusters.size());

		DistanceMeasure measure = new EuclideanDistanceMeasure();
		double max = 0;
		double min = Double.MAX_VALUE;
		double sum = 0;
		int count = 0;
		Set<Double> total = new HashSet<Double>();

		// 如果聚类的个数大于1才开始计算
		if (clusters.size() != 1 && clusters.size() != 0) {
			for (int i = 0; i < clusters.size(); i++) {
				for (int j = 0; j < clusters.size(); j++) {
					double d = measure.distance(clusters.get(i).getCenter(), clusters.get(j).getCenter());
					min = Math.min(d, min);
					max = Math.max(d, max);
					total.add(d);
					sum += d;
					count++;
				}
			}

			System.out.println("Maximum Intercluster Distance:" + max);
			System.out.println("Minimum Intercluster Distance:" + min);
			System.out.println("Average Intercluster Distance:" + sum / count);
			for (double d : total) {
				System.out.print("[" + d + "] ");
			}

		} else if (clusters.size() == 1) {
			System.out.println("只有一个类，无法判断聚类质量");
		} else if (clusters.size() == 0) {
			System.out.println("聚类失败");
		}
	}

}
