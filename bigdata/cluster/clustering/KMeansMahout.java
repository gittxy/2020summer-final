package com.clustering;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.mahout.clustering.Cluster;
import org.apache.mahout.clustering.canopy.CanopyDriver;
import org.apache.mahout.clustering.conversion.InputDriver;
import org.apache.mahout.clustering.kmeans.KMeansDriver;
import org.apache.mahout.common.HadoopUtil;
import org.apache.mahout.common.distance.EuclideanDistanceMeasure;
import org.apache.mahout.utils.clustering.ClusterDumper;

import com.utils.HdfsUtil;

public class KMeansMahout {

	private static final String HDFS = "hdfs://ecs-hadoop-master:9000/";

	public static void main(String[] args) throws Exception {
		String localFile = "/root/BookShopBI/temp/000000_0.dat";
		//String localFile = "/root/iris.dat";

		// mahout输出至HDFS的目录
		String outputPath = HDFS + "/user/hdfs/kmeans/output";
		// mahout的输入目录
		String inputPath = HDFS + "/user/hdfs/kmeans/input/";

		// canopy算法的t1
		double t1 = 2;
		// canopy算法的t2
		double t2 = 1;
		// 收敛阀值
		double convergenceDelta = 0.5;
		// 最大迭代次数
		int maxIterations = 10;

		Path output = new Path(outputPath);
		Path input = new Path(inputPath);
		Configuration conf = new Configuration();

		HdfsUtil hdfs = new HdfsUtil(HDFS, conf);
		hdfs.rmr(inputPath);
		hdfs.mkdirs(inputPath);
		hdfs.copyFile(localFile, inputPath);
		hdfs.ls(inputPath);

		// 每次执行聚类前，删除掉上一次的输出目录
		HadoopUtil.delete(conf, output);
		// 执行聚类
		run(conf, input, output, new EuclideanDistanceMeasure(), t1, t2, convergenceDelta, maxIterations);
	}

	private static void run(Configuration conf, Path input, Path output,
			EuclideanDistanceMeasure euclideanDistanceMeasure, double t1, double t2, double convergenceDelta,
			int maxIterations) throws Exception {

		Path directoryContainingConvertedInput = new Path(output, "data");

		System.out.println("Preparing Input");
		// 将输入文件序列化，并选取RandomAccessSparseVector作为保存向量的数据结构
		InputDriver.runJob(input, directoryContainingConvertedInput, "org.apache.mahout.math.RandomAccessSparseVector");

		System.out.println("Running Canopy to get initial clusters");
		// 保存canopy的目录
		Path canopyOutput = new Path(output, "canopies");

		// 执行Canopy聚类
		CanopyDriver.run(conf, directoryContainingConvertedInput, canopyOutput, euclideanDistanceMeasure, t1, t2, false,
				0.0, false);

		System.out.println("Running KMeans");
		// 执行k-means聚类，并使用canopy目录
		KMeansDriver.run(conf, directoryContainingConvertedInput,
				new Path(canopyOutput, Cluster.INITIAL_CLUSTERS_DIR + "-final"), output, convergenceDelta,
				maxIterations, true, 0.0, false);

		System.out.println("run clusterdumper");
		// 将聚类的结果输出至HDFS
		ClusterDumper clusterDumper = new ClusterDumper(new Path(output, "clusters-*-final"),
				new Path(output, "clusteredPoints"));
		clusterDumper.printClusters(null);

	}
}
