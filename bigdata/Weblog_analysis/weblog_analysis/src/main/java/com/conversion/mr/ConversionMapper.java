package com.conversion.mr;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConversionMapper extends Mapper<LongWritable, Text, Text, Text> {
    public static final String SEPARATOR = "@";

    //用正则表达式指定字符串是否满足表达式
    public static boolean regex(String value, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(value);
        return m.find();
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //从Context中获取表示漏斗的url正则表达式
        String[] desUrlsRegex = context.getConfiguration().get("urls").split(SEPARATOR);

        //如果表示漏斗的url为空，则返回
        if (desUrlsRegex == null) {
            return;
        }

        String[] logInfos = value.toString().split("\t");
        String url = logInfos[0];

        //如果该记录未访问目标地址，则丢弃
        int flag = 0;
        for (int i = 0; i < desUrlsRegex.length; i++) {
            if (regex(url, desUrlsRegex[i])) {
                break;
            } else {
                flag += 1;
            }
        }
        if (flag == desUrlsRegex.length) {
            return;
        }

        //获取uuid
        String uuid = logInfos[1];
        //获取SessionId
        String sessionId = logInfos[2];
        try {
            //获取csvp
            int csvp = Integer.parseInt(logInfos[3]);
            //将SessionId和csvp组成新的key
            String newKey = sessionId + SEPARATOR + csvp;
            //剩下的部分作为新的value
            String newValue = uuid + SEPARATOR + url;

            context.write(new Text(newKey), new Text(newValue));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
