import os

from com.utils.py_env import HADOOP_PATH, PROJECT_LIB_DIR


def recommend_user():
    shell = HADOOP_PATH + "hadoop jar " + PROJECT_LIB_DIR + "userrecommend.jar com.recommender.cf_hadoop.ItemCFHadoop "
    os.system(shell)

