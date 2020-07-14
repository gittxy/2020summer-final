package etl.dedup;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

/**
 * Hive自定义函数，用于数据去重使用
 * 
 * @author GaoLei
 *
 */
public class HiveDedupUDF extends UDF {
	public static String signature = "-";
	public static int order = -1;
	public int evaluate(Text text) {
		if (text != null) {
			// 分组排序的依据，列名，通常为主键
			String colName = text.toString();
			// 处理第一条数据
			if (signature == "-") {
				// 记下分组排序的字段:主键,并将rownum设为1
				signature = colName;
				order = 1;
			} else {
				// 首先比对是否和上一条的主键相同
				if (signature.equals(colName)) {
					order++;
				} else {
					signature = colName;
					order = 1;
				}
			}
		}
		return order;
	}
}

// 在传统数据库如Oracle、SQL Server等数据库中都提供了rownum()函数,它代表了某条记录在结果集中的位置,例如:
// ID MODIFYDATE ROWNUM
// 1 2017-07-10 2
// 1 2017-07-11 1
// 2 2017-07-11 1
// 3 2017-07-10 2
// 3 2017-07-11 1
// 此时,只需对整个结果集加上限制条件ROWNUM=1,即可实现重复数据被过滤掉.然后再将新的结果集导入到原表分区即可.
// 尽管rownum函数在实际开发中使用频率非常高,但是Hive本身并没有提供rownum函数或类似的功能.所以只能通过编写Hive UDF来实现.
// 代码如上所示.
// 编写完成后,需要将其导出为一个jar包,并在Hive中注册,才可以使用.
// hive> add jar /home/hadoop/rownum.jar;
// Added /home/hadoop/rownum.jar to class path
// Added resource: /home/hadoop/rownum.jar
// hive> create temporary function rownum as 'com.udf.RowNumUDF';
//
// 在Hive中使用RowNumUDF实现数据去重:
// select * from (
// select * from (
// select * from bx_orders where dt='2017-07-12'
// union all
// select * from bx_orders where dt<'2017-07-12') t1 distribute by orderid sort
// by orderid,orderdate) t2
// where rownum(orderid) = 1
