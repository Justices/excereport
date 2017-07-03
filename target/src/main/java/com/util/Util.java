package com.util;

import com.cache.SystemCache;
import com.property.PropertiesLoader;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by liujiaping on 2017/7/1.
 */
public class Util {

    public static String getTodayString() {
        Date today = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        return simpleDateFormat.format(today);
    }

    public static String getSheetKey() {
        String sheetKey = "sheet_";
        String daySuffix = getTodayString();
        return sheetKey.concat(daySuffix);
    }



    public static void main(String[] args ) throws IOException {
        PropertiesLoader loader = new PropertiesLoader();
        loader.loadProperties();
        System.out.println(SystemCache.getInstance());

    }
}
