package com.conversion.mr;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConversionReducer extends Reducer<Text, Text, NullWritable, Text> {
    public static final String SEPARATOR = "@";
    //表示前一条记录的SessionId
    public static String preSessionId = "not set";
    //表示漏斗的步骤，如，1表示漏斗的第一步
    public static int process = 0;

    //用正则表达式指定字符串是否满足表达式
    public static boolean regex(String value, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(value);
        return m.find();
    }

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        //从Context中获取表示漏斗的url正则表达式
        String[] desUrls = context.getConfiguration().get("urls").split(SEPARATOR);
        //获取SessionId
        String sessionId = key.toString().split(SEPARATOR)[0];
        String value = values.iterator().next().toString();
        //获取url
        String url = value.split(SEPARATOR)[1];
        //获取uuid
        String uuid = value.split(SEPARATOR)[0];

        //如果是第一次执行reduce函数
        if (preSessionId.equals("not set")) {
            //记录当前SessionId
            preSessionId = sessionId;
            //初始化进度
            process = 0;
            if (regex(url, desUrls[0])) {
                process = 1;
                String result = sessionId + "\t" + uuid + "\t" + process;
                context.write(NullWritable.get(), new Text(result));
            } else {
                return;
            }
        } else {
            //当presession-session时，说明正在进行漏斗的比较中
            if (preSessionId.equals(sessionId)) {
                //一个漏斗比较完成
                if (process == desUrls.length) {
                    //开始新的漏斗比较
                    process = 0;
                    //当前进度为0时，只需比较第一个漏斗
                    if (regex(url, desUrls[0])) {
                        process = 1;
                        String result = sessionId + "\t" + uuid + "\t" + process;
                        context.write(NullWritable.get(), new Text(result));
                    } else {
                        return;
                    }
                    return;
                } else {
                    if (regex(url, desUrls[process])) {
                        //更新进度
                        process++;
                        String result = sessionId + "\t" + uuid + "\t" + process;
                        context.write(NullWritable.get(), new Text(result));
                    }
                }
            } else {//如果是一个新的SessionId
                preSessionId = sessionId;
                process = 0;
                if (regex(url, desUrls[0])) {
                    process = 1;
                    String result = sessionId + "\t" + uuid + "\t" + process;
                    context.write(NullWritable.get(), new Text(result));
                } else {
                    return;
                }
            }
        }
    }
}