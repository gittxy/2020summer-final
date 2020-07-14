package etl.simple;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import utils.WebLogUtil;

import java.io.IOException;

public class WebLogMapper extends Mapper<LongWritable, Text, Text, Text> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String logLine = value.toString();
        try {
            String valueout = WebLogUtil.weblogParser(logLine);
            String keyout = valueout.split("\\|")[0];

            context.write(new Text(keyout), new Text(valueout));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
