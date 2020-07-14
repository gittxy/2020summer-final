import os

from com.recommend.recommend_hive import recommend_data
from com.recommend.user_recommender import recommend_user
from com.utils.HiveUtil import HiveUtil
from com.utils.py_env import PROJECT_TMP_DIR

if __name__ == '__main__':
    hqlCommand = "hive -S -e \"select distinct clusterid from recommend.final_result\""
    maxValue = os.popen(hqlCommand).read()
    clusterid = maxValue.split("\n")
    print("clusterid   ", clusterid)
    #user_id = '300100'

    for i in range(len(clusterid)-1):
        print("clusterid  ", clusterid[i])
        recommend_data(clusterid[i])
        print("cluser=",clusterid[i],"jar")
        recommend_user()
        os.rename(PROJECT_TMP_DIR + "recommend/part-r-00000", PROJECT_TMP_DIR + "recommend-result/"+"recommend_data" + str(i))
    #清空文件
    f=open(PROJECT_TMP_DIR + "recommend-result/recommend-final",'w')
    f.truncate()
    for i in range(16):
        with open(PROJECT_TMP_DIR + "recommend-result/"+"recommend_data" + str(i)) as file:
            list = file.readlines()
            content = ""
            for j in range(len(list)):
                book = list[j].split("\t")[1].split("[")[1].split("]")[0]
                bookAll = book.split(",")
                for m in range(len(bookAll)):
                    bookid = bookAll[m].split(":")[0]
                    content = content + str(i) + "\t" + list[j].split("\t")[0] + "\t" + bookid + "\n"

        with open(PROJECT_TMP_DIR + "recommend-result/recommend-final", 'a') as file_object:
            file_object.write(content)

    """
    create table recommend(
    "clusterid" int 
    "user_id" int
    "book_id" bigint(21)
    )
    """
