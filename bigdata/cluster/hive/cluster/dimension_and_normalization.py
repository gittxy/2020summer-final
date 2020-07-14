#from com.utils import HiveUtil
import os

from com.utils.HiveUtil import HiveUtil
from com.utils.py_env import PROJECT_TMP_DIR


def prepare_normaliz(start_time, end_time):
    start = str(start_time).split("/")
    st = start[0] + " " + start[1]
    s = start[0].split("-")
    start = s[0] + s[1] + s[2]

    end = str(end_time).split("/")
    en = end[0] + " " + end[1]
    e = end[0].split("-")
    end = e[0] + e[1] + e[2]

    print(start)
    print(end)
    print(s)
    print(e)

    # 提取数据维度
    hql_1 = "\"insert overwrite table recommend.user_dimension select t1.user_id,t1.avg,t2.ordercount,t3.sessioncount from " \
            "(select user_id,avg(payment) avg from recommend.orders where update_time<= '" + st + "' and update_time>= '" + en + "' group by user_id) t1 " \
            "join (select user_id,count(order_id) ordercount from recommend.orders where update_time<= '"+st+"' and update_time>= '"+en+"' group by user_id) t2 on t1.user_id=t2.user_id " \
            "join (select userId,count(sessionId) sessioncount from recommend.clickstream_log where dt<= '"+start+"' and dt>= '"+end+"' group by userId) t3 on t1.user_id=t3.userId\"";
    HiveUtil.execute_shell(hql_1)

    # 将user_dimension数据导入到cluster_input中，去掉CustomerId
    hql_2 = "\"insert overwrite table recommend.cluster_input select subTotal,ordersCount,sessionCount from recommend.user_dimension\""
    HiveUtil.execute_shell(hql_2)

    # 归一化
    hql_3 = "\"insert overwrite table recommend.cluster_input " \
            "select ((subTotal-avg_subTotal)/std_subTotal),((ordersCount-avg_ordersCount)/std_ordersCount),((sessionCount-avg_sessionCount)/std_sessionCount) from recommend.cluster_input " \
            "join (select std(subTotal) std_subTotal,std(ordersCount) std_ordersCount,std(sessionCount) std_sessionCount from recommend.cluster_input) t1 on 1=1 " \
            "join (select avg(subTotal) avg_subTotal,avg(ordersCount) avg_ordersCount,avg(sessionCount) avg_sessionCount from recommend.cluster_input) t2 on 1=1\"";
    HiveUtil.execute_shell(hql_3)
    print("success3")

    # 输出聚类文件
    hql_5 = "\"INSERT OVERWRITE LOCAL DIRECTORY '" + PROJECT_TMP_DIR + "'"\
            "ROW format delimited fields terminated BY '\t'" \
            "SELECT *from recommend.cluster_input;\""
    HiveUtil.execute_shell(hql_5)
    print("success5")

    with open(PROJECT_TMP_DIR + "000000_0") as lines:
        for line in lines:
            line_data = line.split("\t")
            line_con = str(line_data[0]) + " " + str(line_data[1]) + " " + str(line_data[2])
            print(line_con)
            with open(PROJECT_TMP_DIR + "000000_1",'a') as file_obj:
                file_obj.write(str(line_con))

    os.rename(PROJECT_TMP_DIR + "000000_1", PROJECT_TMP_DIR + "000000_0.dat")
    print("change success")

    #计算相关系数矩阵
    hql_4="\"select " \
          "(avg(subTotal*subTotal)-avg(subTotal)*avg(subTotal))/(std(subTotal)*std(subTotal))," \
          "(avg(subTotal*ordersCount)-avg(subTotal)*avg(ordersCount))/(std(subTotal)*std(ordersCount))," \
          "(avg(subTotal*sessionCount)-avg(subTotal)*avg(sessionCount))/(std(subTotal)*std(sessionCount))," \
          "(avg(ordersCount*subTotal)-avg(ordersCount)*avg(subTotal))/(std(ordersCount)*std(subTotal))," \
          "(avg(ordersCount*ordersCount)-avg(ordersCount)*avg(ordersCount))/(std(ordersCount)*std(ordersCount))," \
          "(avg(ordersCount*sessionCount)-avg(ordersCount)*avg(sessionCount))/(std(ordersCount)*std(sessionCount))," \
          "(avg(sessionCount*subTotal)-avg(sessionCount)*avg(subTotal))/(std(sessionCount)*std(subTotal))," \
          "(avg(sessionCount*ordersCount)-avg(sessionCount)*avg(ordersCount))/(std(sessionCount)*std(ordersCount))," \
          "(avg(sessionCount*sessionCount)-avg(sessionCount)*avg(sessionCount))/(std(sessionCount)*std(sessionCount)) " \
          "from recommend.cluster_input\"";
    HiveUtil.execute_shell(hql_4)

    """
    insert overwrite table user_dimension select t1.user_id,t1.avg,t2.ordercount,t3.sessioncount from 
    (select user_id,avg(payment) avg from orders where update_time<= '2019-06-22 00:00:00.0' and update_time>= '2019-06-12 00:00:00.0' group by user_id) t1
    join (select user_id,count(order_id) ordercount from orders where update_time<= '2019-06-22 00:00:00.0' and update_time>= '2019-06-12 00:00:00.0' group by user_id) t2 on t1.user_id=t2.user_id
    join (select userId,count(sessionId) sessioncount from clickstream_log where dt<= '20190622' and dt>= '20190612' group by userId) t3 on t1.user_id=t3.userId;

    insert overwrite table cluster_input
    select (subTotal-avg_subTotal)/std_subTotal,(ordersCount-avg_ordersCount)/std_ordersCount,(sessionCount-avg_sessionCount)/std_sessionCount from cluster_input
    join (select std(subTotal) std_subTotal,std(ordersCount) std_ordersCount,std(sessionCount) std_sessionCount from cluster_input) t1 on 1=1
    join (select avg(subTotal) avg_subTotal,avg(ordersCount) avg_ordersCount,avg(sessionCount) avg_sessionCount from cluster_input) t2 on 1=1;

    insert overwrite table cluster_input
    select cast(((subTotal-avg_subTotal)/std_subTotal)as decimal(10,3)),cast(((ordersCount-avg_ordersCount)/std_ordersCount)as decimal(10,2)),cast(((sessionCount-avg_sessionCount)/std_sessionCount) as decimal(10,2)) from cluster_input
    join (select std(subTotal) std_subTotal,std(ordersCount) std_ordersCount,std(sessionCount) std_sessionCount from cluster_input) t1 on 1=1
    join (select avg(subTotal) avg_subTotal,avg(ordersCount) avg_ordersCount,avg(sessionCount) avg_sessionCount from cluster_input) t2 on 1=1;


    INSERT OVERWRITE LOCAL DIRECTORY '/root/files' 
    ROW format delimited fields terminated BY '\t' 
    SELECT *from cluster_input;


    select
    (avg(subTotal*subTotal)-avg(subTotal)*avg(subTotal))/(std(subTotal)*std(subTotal)),
    (avg(subTotal*ordersCount)-avg(subTotal)*avg(ordersCount))/(std(subTotal)*std(ordersCount)),
    (avg(subTotal*sessionCount)-avg(subTotal)*avg(sessionCount))/(std(subTotal)*std(sessionCount)),
    (avg(ordersCount*subTotal)-avg(ordersCount)*avg(subTotal))/(std(ordersCount)*std(subTotal)),
    (avg(ordersCount*ordersCount)-avg(ordersCount)*avg(ordersCount))/(std(ordersCount)*std(ordersCount)),
    (avg(ordersCount*sessionCount)-avg(ordersCount)*avg(sessionCount))/(std(ordersCount)*std(sessionCount)),
    (avg(sessionCount*subTotal)-avg(sessionCount)*avg(subTotal))/(std(sessionCount)*std(subTotal)),
    (avg(sessionCount*ordersCount)-avg(sessionCount)*avg(ordersCount))/(std(sessionCount)*std(ordersCount)),
    (avg(sessionCount*sessionCount)-avg(sessionCount)*avg(sessionCount))/(std(sessionCount)*std(sessionCount))
    from cluster_input;
    """
