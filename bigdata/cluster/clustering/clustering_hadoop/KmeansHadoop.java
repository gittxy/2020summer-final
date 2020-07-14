package com.clustering.clustering_hadoop;

import java.util.Iterator;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.JobConf;
import org.apache.mahout.clustering.classify.WeightedVectorWritable;
import org.apache.mahout.clustering.conversion.InputDriver;
import org.apache.mahout.clustering.kmeans.KMeansDriver;
import org.apache.mahout.clustering.kmeans.RandomSeedGenerator;
import org.apache.mahout.common.distance.DistanceMeasure;
import org.apache.mahout.common.distance.EuclideanDistanceMeasure;
import org.apache.mahout.math.Vector;
import org.apache.mahout.utils.clustering.ClusterDumper;

import com.utils.HdfsUtil;

public class KmeansHadoop {
	private static final String HDFS = "hdfs://ecs-hadoop-master:9000/";

	public static void main(String[] args) throws Exception {
		String localFile = "/root/iris.dat";
		String inPath = HDFS + "/user/hdfs/mix_data";
		String seqFile = inPath + "/seqfile";
		String seeds = inPath + "/seeds";
		String outPath = inPath + "/result/";
		String clusteredPoints = outPath + "/clusteredPoints";

		JobConf conf = config();
		HdfsUtil hdfs = new HdfsUtil(HDFS, conf);
		hdfs.rmr(inPath);
		hdfs.mkdirs(inPath);
		hdfs.copyFile(localFile, inPath);
		hdfs.ls(inPath);
		//它用于将原始数据文件转换成 Mahout进行计算所需格式的文件 SequenceFile，它是Hadoop API提供的一种二进制文件支持。这种二进制文件直接将<key, value>对序列化到文件中。
		InputDriver.runJob(new Path(inPath), new Path(seqFile), "org.apache.mahout.math.RandomAccessSparseVector");
		//指定聚类的个数
		int k = 3;
		///指定输入路径，如前面介绍的一样，基于 Hadoop 的实现就是通过指定输入输出的文件路径来指定数据源的。
		Path seqFilePath = new Path(seqFile);
		Path clustersSeeds = new Path(seeds);
		//声明一个计算距离的方法，这里选择了欧几里德距离
		DistanceMeasure measure = new EuclideanDistanceMeasure();
		//随机的选择 k 个作为簇的中心
		clustersSeeds = RandomSeedGenerator.buildRandom(conf, seqFilePath, clustersSeeds, k, measure);
		//调用 KMeansDriver.run 方法执行 K 均值聚类算法
		KMeansDriver.run(conf, seqFilePath, clustersSeeds, new Path(outPath), 0.01, 10, true, 0.01, false);

		Path outGlobPath = new Path(outPath, "clusters-*-final");
		Path clusteredPointsPath = new Path(clusteredPoints);
		System.out.printf("Dumping out clusters from clusters: %s and clusteredPoints: %s\n", outGlobPath,
				clusteredPointsPath);
		//调用 ClusterDumper 的 printClusters 方法将聚类结果打印出来。
		ClusterDumper clusterDumper = new ClusterDumper(outGlobPath, clusteredPointsPath);
		clusterDumper.printClusters(null);
	}

	public static JobConf config() {
		JobConf conf = new JobConf(KmeansHadoop.class);
		conf.setJobName("KmeansHadoop");
		conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
		conf.addResource("classpath:/resources/core-site.xml");
		conf.addResource("classpath:/resources/hdfs-site.xml");
		conf.addResource("classpath:/resources/mapred-site.xml");
		return conf;
	}

	public static void displayCluster(ClusterDumper clusterDumper) {
		Iterator<Integer> keys = clusterDumper.getClusterIdToPoints().keySet().iterator();
		while (keys.hasNext()) {
			Integer center = keys.next();
			System.out.println("Center:" + center);
			for (WeightedVectorWritable point : clusterDumper.getClusterIdToPoints().get(center)) {
				Vector v = point.getVector();
				System.out.println(v.get(0) + "" + v.get(1));
			}
		}
	}
}
