package com.conversion.mr;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class ConversionComparator extends WritableComparator {
    protected ConversionComparator() {
        super(Text.class, true);
    }

    public static final String SEPARATOR = "@";

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        String[] comp1 = a.toString().split(SEPARATOR);
        String[] comp2 = b.toString().split(SEPARATOR);

        long result = 1;

        if (comp1 != null && comp2 != null) {
            //比较SessionId
            result = comp1[0].compareTo(comp2[0]);
            //如果SessionId相同，则比较csvp
            if (result == 0 && comp1.length > 1 && comp2.length > 1) {
                long csvp1 = 0;
                long csvp2 = 0;

                try {
                    //获取csvp
                    csvp1 = Long.parseLong(comp1[1]);
                    csvp2 = Long.parseLong(comp2[1]);
                    result = csvp1 - csvp2;
                    if (result == 0) {
                        //如果csvp相等，则返回0
                        return 0;
                    } else {
                        //如果a的csvp大，则返回1，否则返回-1
                        return result > 0 ? 1 : -1;
                    }
                } catch (Exception e) {
                    return 1;
                }
            }
            return result > 0 ? 1 : -1;
        }
        return 1;
    }
}
