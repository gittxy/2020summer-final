package com.conversion.mr;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class ConversionPartitioner extends Partitioner<Text, Text> {
    public static final String SEPARATOR = "@";

    public int getPartition(Text key, Text value, int parts) {
        String sessionId = "-";
        if (key != null) {
            sessionId = key.toString().split(SEPARATOR)[0];
        }

        //将SessionId从0到Integer的最大值进行散列
        int reducerNum = (sessionId.hashCode() & Integer.MAX_VALUE) % parts;
        return reducerNum;
    }
}
