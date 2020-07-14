import os

from com.utils.HiveUtil import HiveUtil
from com.utils.py_env import PROJECT_TMP_DIR


def recommend_data(clusterid):
    hql_1 = "\"insert overwrite table recommend.recommend_input select t1.userid,t2.order_id,t2.order_mount from" \
            "(select userid from recommend.final_result where clusterid = '" + clusterid + "') t1 " \
            "join (select user_id,order_id,order_mount from recommend.orders) t2 on t1.userid = t2.user_id\""
    HiveUtil.execute_shell(hql_1)

    hql_2 = "\"insert overwrite table recommend.recommend_input select distinct *from recommend.recommend_input;\""
    HiveUtil.execute_shell(hql_2)

    hql_3 = "\"insert overwrite table recommend.recommend_input select t1.userid,t2.book_id,t2.mount from" \
        "(select userid, bookid from recommend.recommend_input) t1 " \
        "join (select order_id, book_id, mount from recommend.order_detail) t2 on t1.bookid = t2.order_id\""
    HiveUtil.execute_shell(hql_3)

    hql_4 = "\"INSERT OVERWRITE LOCAL DIRECTORY '" + PROJECT_TMP_DIR + "recommend/'" \
            "ROW format delimited fields terminated BY ','" \
            "SELECT *from recommend.recommend_input;\""
    HiveUtil.execute_shell(hql_4)

    os.rename(PROJECT_TMP_DIR + "recommend/000000_0", PROJECT_TMP_DIR + "recommend/recommend_data.csv")
    """
    CREATE TABLE recommend_input(
    userid INT,
    bookid STRING,
    mount INT
    ) ROW FORMAT DELIMITED FIELDS TERMINATED BY ' ';

    insert overwrite table recommend.recommend_input select t1.userid,t2.order_id,t2.order_mount from
    (select userid from recommend.final_result where clusterid = (select clusterid from recommend.final_result where userid = 300100)) t1
    join (select user_id,order_id,order_mount from recommend.orders) t2 on t1.userid = t2.user_id;
    
    insert overwrite table recommend.recommend_input select distinct *from recommend_input;

    insert overwrite table recommend.recommend_input select t1.userid,t2.book_id,t2.mount from
    (select userid, bookid from recommend.recommend_input) t1
    join (select order_id, book_id, mount from recommend.order_detail) t2 on t1.bookid = t2.order_id;
    """


