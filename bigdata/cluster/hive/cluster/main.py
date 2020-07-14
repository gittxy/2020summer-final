import sys
from com.cluster.dimension_and_normalization import prepare_normaliz
from com.cluster.user_cluster import cluster_output, get_finalresult
from com.utils.py_env import PROJECT_LIB_DIR, HADOOP_PATH, PROJECT_TMP_DIR

if __name__ == '__main__':

    print(PROJECT_LIB_DIR)
    start = sys.argv[1]
    end = sys.argv[2]

    # 准备数据并做数据归一化
    prepare_normaliz(start, end)

    # 聚类并输出
    cluster_output()

    # 得到聚类结果
    get_finalresult()
