package etl.simple;

import org.apache.hadoop.mapreduce.Reducer;
import utils.WebLogUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.Text;

public class WebLogReducer extends Reducer<Text, Text, Text, Text> {
    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        List<String> loginfoGroup = new ArrayList<>();
        try {
            values.forEach(value -> {
                loginfoGroup.add(value.toString());
            });
            List<String> loginfoBySession = WebLogUtil.distinctLogInfoBySession(loginfoGroup);
            for (String valueout : loginfoBySession) {
                context.write(new Text(""), new Text(valueout));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
