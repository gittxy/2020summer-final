package etl.clean;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import utils.IpParser;

public class ClickStreamMapper extends Mapper<LongWritable, Text, Text, Text> {

    public static final String NONE = "none";

    private String ipAddress = NONE;
    private String uniqueId = NONE;
    private String url = NONE;
    private String sessionId = NONE;
    private String sessionTimes = NONE;
    private String areaAddress = NONE;
    private String localAddress = NONE;
    private String browserType = NONE;
    private String operationSys = NONE;
    private String referUrl = NONE;
    private String receiveTime = NONE;
    private String userId = NONE;

    // Apache日志的正则表达式
    private static String[] items = {
            "(?<ip>\\S+)",                          // 远端主机
            "(?<loginname>[\\w.-]+)",               // 远端登录名
            "(?<username>[\\w.-]+)",                // 远程用户名
            "(?<receivetime>\\[[^\\[\\]]+\\])",     // 服务器接收时间
            "(?<url>\"((?:[^\"]|\")+)\")",        // 请求的路径*
            "(?<status>[[0-9].-]+)",                   // 最后请求的状态
            "(?<size>[[0-9].-]+)",                      // 以CLF格式显示的除HTTP头以外传送的字节数
            "(?<referurl>\"((?:[^\"]|\")+)\")",   // 上一个访问页面
            "(?<agent>\"((?:[^\"]|\")+)\")",      // 用户浏览器信息
            "(?<cookie>\"(.+)\")",                  // Cookie信息
            "(?<getsize>.*)",                       // 接收的字节数
            "(?<postsize>.*)",                      // 发送的字节数
            "(?<forwarded>.*)",                     // %{X-Forwarded-For}i
            "(?<host>.+)",                          // 访问主机的地址（域名）
            "(?<time>.*)"                           // 服务器处理本请求所用的时间，以微秒为单位
    };

    public static String APACHE_LOG_REGEX = StringUtils.join(items, "\\s");

    @Override
    protected void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {
        String log = value.toString();

        // 使用正则表达式解析某一条Apache日志记录
        Pattern pattern = Pattern.compile(APACHE_LOG_REGEX);
        Matcher matcher = pattern.matcher(log);

        String ipStr = null;
        String receiveTimeStr = null;
        String urlStr = null;
        String referUrlStr = null;
        String userAgentStr = null;
        String cookieStr = null;
        String hostNameStr = null;

        if (matcher.find()) {
            ipStr = matcher.group("ip");
            receiveTimeStr = matcher.group("receivetime");
            urlStr = matcher.group("url");
            referUrlStr = matcher.group("referurl");
            userAgentStr = matcher.group("agent");
            cookieStr = matcher.group("cookie");
            hostNameStr = matcher.group("host");

            // 保存IP地址
            ipAddress = ipStr;
            try {
                // 根据IP地址找出所在区域
                areaAddress = IpParser.getLocal(ipStr);
                localAddress = IpParser.getCountry(ipStr);

            } catch (Exception e) {
                e.printStackTrace();
            }

            DateFormat df = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss", Locale.US);
            try {
                receiveTimeStr = receiveTimeStr.split(" ")[0].substring(1);
                Date date = df.parse(receiveTimeStr);
                receiveTime = Long.toString(date.getTime());
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 将url中的无效字符串去掉
            urlStr = urlStr.substring(5);
            // 重新接着成url字符串
            url = "\"" + hostNameStr + urlStr;


            // 用户浏览器信息的正则表达式
            String userAgentRegex = "^(?<h1>.+)\\s(?<h2>\\(.+\\))\\s(?<h3>.+)\\s(?<h4>\\(.+\\))\\s(?<h5>.+)\\s(?<h6>.+)\\s(?<h7>.+)$";
            pattern = Pattern.compile(userAgentRegex);
            matcher = pattern.matcher(userAgentStr);
            matcher.find();
            // System.out.println(matcher.find());

            // 获取浏览器类型
            browserType = NONE;//matcher.group("h5");
            // 获取操作系统类型
            operationSys = NONE;//matcher.group("h2");

            // 保存上一个页面url
            referUrl = referUrlStr;

            // 使用Hashmap保存cookie信息
            HashMap<String, String> cookies = new HashMap<String, String>();
            String[] strs = cookieStr.substring(1, cookieStr.length() - 1).split(";");
            for (int i = 0; i < strs.length; i++) {
                if (strs[i].contains("=")) {
                    String[] kv = strs[i].split("=");
                    String keyStr = kv[0];
                    String valStr = kv[1];
                    cookies.put(keyStr, valStr);
                } else {

                }
            }

            // 获取uuid信息
            uniqueId = cookies.get("uuid");
            // 获取账号信息
            userId = cookies.get("userId");
            // 如果没有获取到账号信息，说明用户没有登录
            if (userId == null) {
                userId = "unlogin";
            }

            // 获取sessionTimes
            sessionTimes = cookies.get("st");
            // 拼装成sessionId
            sessionId = uniqueId + "|" + sessionTimes;

            // 使用session和receiveTime组成新的key
            String mapOutKey = sessionId + "&" + receiveTime;
            // 按照clickstream_log表的顺序重新组合value
            String mapOutValue = ipAddress + "\t" + uniqueId + "\t" + url + "\t" + sessionId + "\t" + sessionTimes
                    + "\t" + areaAddress + "\t" + localAddress + "\t" + browserType + "\t" + operationSys + "\t"
                    + referUrl + "\t" + receiveTime + "\t" + userId;

            context.write(new Text(mapOutKey), new Text(mapOutValue));
        } else {
            return;
        }
//		context.write(new Text(key.toString()), new Text(value.toString()));
    }
}
