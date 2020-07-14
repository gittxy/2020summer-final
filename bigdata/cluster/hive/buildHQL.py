# -*- coding:UTF-8 -*-
from com.utils.py_env import PROJECT_CONF_DIR

import xml.etree.ElementTree as ET


def resolve_conf(type, dt):
    # 获取配置文件路径
    confFile = PROJECT_CONF_DIR + "hive_job.xml"

    # 解析配置文件
    xmlTree = ET.parse(confFile)

    # 获取job元素
    jobs = xmlTree.findall('./job')

    # 用来保存hql
    hqls = []

    # 遍历所有job的子元素，获得所需参数
    for job in jobs:
        # 如果job的type是需要执行的type
        if job.attrib["type"] == type:
            for hql in job.getchildren():

                # 获取hql标签的text值，并去掉两边可能出现的空字符
                hql = hql.text.strip()

                # 检查hql有效性，无效则抛出异常
                if len(hql) == 0 or hql == "" or hql == None:
                    raise Exception("hql语法不正确")
                else:
                    # 将$t替换为时间
                    hql = hql.replace("\$dt", "< '" + dt + "'")
                    hql = hql.replace("$dt", "'" + dt + "'")

                    hqls.append(hql)

    return hqls
