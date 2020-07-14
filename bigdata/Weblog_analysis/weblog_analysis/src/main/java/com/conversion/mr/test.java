package com.conversion.mr;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test {
    public static final String SEPARATOR = "@";
    public static boolean regex(String value, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(value);
        return m.find();
    }

    public static void main(String[] args) {
//        String urls="[http://]?114.116.28.88/bookstore/book/info/ [http://]?114.116.28.88/bookstore/order/ [http://]?114.116.28.88/bookstore/payment/ ";
//        String url="114.116.28.88/bookstore/book/info/ 114.116.28.88/bookstore/order/ 114.116.28.88/bookstore/payment/ ";
//        String[] desUrlsRegex = urls.split(" ");
//        System.out.println(desUrlsRegex[0]);

        String x="lll hhh [http://]?114.116.28.88/bookstore/book/info/ [http://]?114.116.28.88/bookstore/order/ [http://]?114.116.28.88/bookstore/payment/";
        String[] a=x.split(" ");
        ArrayList<String> hoppers = new ArrayList<String>();
        for (int i = 2; i < a.length; i++) {
            hoppers.add(a[i]);
        }
        System.out.println(hoppers);
        String urls = "";
        for (int i = 0; i < hoppers.size(); i++) {
            urls += hoppers.get(i);
            if (i != hoppers.size() - 1) {
                urls += SEPARATOR;
            }
        }
        System.out.println(urls);
        String[] desUrlsRegex = urls.split(SEPARATOR);
        System.out.println(desUrlsRegex[2]);
//        int flag = 0;
//        for (int i = 0; i < desUrlsRegex.length; i++) {
//            if (regex("114.116.28.88/bookstore/book/info/16784532", desUrlsRegex[i])) {
//                break;
//            } else {
//                flag += 1;
//            }
//        }
//        if (flag == desUrlsRegex.length) {
//            return;
//        }

    }
}
