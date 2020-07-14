import os

from com.utils.HiveUtil import HiveUtil
from com.utils.py_env import PROJECT_LIB_DIR, HADOOP_PATH, PROJECT_TMP_DIR


def cluster_output():
    clusterOutputPath = "/user/hadoop/clusterOutput"
    t1 = "100"
    t2 = "10"
    convergenceDelta = "0.5"
    maxIterations = "10"

    # 执行聚类
    shell = HADOOP_PATH + "hadoop jar " + PROJECT_LIB_DIR + "usercluster.jar com.clustering.KMeansMahout " + clusterOutputPath \
            + " /user/hive/warehouse/cluster_input " + t1 + " " + t2 + " " + convergenceDelta + " " + maxIterations
    os.system(shell)

    # 解析聚类结果文件并输出至本地
    shell = HADOOP_PATH + "hadoop jar " + PROJECT_LIB_DIR + "usercluster.jar com.clustering.ClusterOutput " + clusterOutputPath \
            + " " + PROJECT_TMP_DIR + "result"
    os.system(shell)

    # 将本地的结果文件加载到Hive的cluster_result表中
    hql = "\"load data local inpath '" + PROJECT_TMP_DIR + "result' overwrite into table recommend.cluster_result\""
    HiveUtil.execute_shell(hql)


def get_finalresult():
    hql = "\"insert overwrite table recommend.final_result select t2.customerId,t1.* from " \
          "(select clusterId,subTotal,ordersCount,sessionCount from recommend.cluster_result group by clusterId,subTotal,ordersCount,sessionCount) t1 " \
          "join (select customerId,((subTotal-avg_subTotal)/std_subTotal) subTotal,((ordersCount-avg_ordersCount)/std_ordersCount) ordersCount," \
          "((sessionCount-avg_sessionCount)/std_sessionCount) sessionCount from recommend.user_dimension " \
          "join (select std(subTotal) std_subTotal,std(ordersCount) std_ordersCount,std(sessionCount) std_sessionCount from recommend.user_dimension) t1 on 1=1 " \
          "join (select avg(subTotal) avg_subTotal,avg(ordersCount) avg_ordersCount,avg(sessionCount) avg_sessionCount from recommend.user_dimension) t2 on 1=1) t2 " \
          "on t1.subTotal=t2.subTotal and t1.ordersCount=t2.ordersCount and t1.sessionCount=t2.sessionCount\""
    HiveUtil.execute_shell(hql)

