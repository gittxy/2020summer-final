import com.sun.istack.internal.NotNull;
import org.apache.commons.lang3.StringUtils;
import utils.WebLogUtil;

import java.io.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebLogUtilTest {
    public static void main(String[] args) throws Exception {
        String logLine = "46.51.220.242``2014-02-12 03:08:26`GET /feed HTTP/1.1`200`-``";

        String logInfo = WebLogUtil.weblogParser(logLine);
        System.out.println(logInfo.split("\\|")[2]);
        long lastRequestTime = WebLogUtil.sdf_standard.parse(logInfo.split("\\|")[2]).getTime();
        System.out.println(lastRequestTime);
//        Date curDate = new Date();
//        InputStream in = WebLogUtil.class.getClassLoader().getResourceAsStream("weblog.log");
//        InputStreamReader isr = new InputStreamReader(in);
//        BufferedReader br = new BufferedReader(isr);
//
//        File outfile = new File(WebLogUtil.class.getClassLoader().getResource("weblog_output_01.log").getPath());
//        FileOutputStream fos = new FileOutputStream(outfile);
//        OutputStreamWriter osw = new OutputStreamWriter(fos);
//
//        String line2 = "";
//        while ((line2 = br.readLine()) != null) {
//
//        }


//        //将log信息变为(IP,log信息)的tuple格式,也就是按IP地址将log分组
//        val logLinesKVMapRDD = logFileSource.map(line = > (line.split(" ") (0), line)).groupByKey();
//        //对每个(IP[String],log信息[Iterator])中的日志按时间的升序排序
//        // (其实这一步没有必要,本来Nginx的日志信息就是按访问先后顺序记录的,这一步只是为了演示如何在Scala语境下进行自定义排序)
//        // 排完序后(IP[String],log信息[Iterator])的格式变为log信息[Iterator]
//        val sortedLogRDD = logLinesKVMapRDD.map(_._2.toList.sortWith((A, B) = > WebLogSession.dateComparator(A, B)))
//        //将每一个IP的日志信息按30分钟的session分类并拼上session信息
//        val logInfoBySessionRDD = sortedLogRDD.map(WebLogSession.distinctLogInfoBySession(_))
//        //将List中的日志信息拆分成单条日志信息输出
//        val logInfoWithSessionRDD = logInfoBySessionRDD.flatMap(line = > line).
//        saveAsTextFile("hdfs://master:9000/spark_clickstream/session_log/" + WebLogSession.sdf_hdfsfolder.format(curDate))
    }
}
