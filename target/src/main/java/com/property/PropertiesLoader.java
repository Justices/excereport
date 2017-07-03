package com.property;

import com.cache.SystemCache;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by liujiaping on 2017/7/1.
 */
public class PropertiesLoader {
    private static  Properties properties;

    static {
        properties = new Properties();
        try {
            InputStreamReader inputStreamReader =  new InputStreamReader(PropertiesLoader.class.getClassLoader().getResourceAsStream("columnMap.properties"),"utf-8");
            properties.load(inputStreamReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PropertiesLoader() {
    }

    public void loadProperties() throws IOException {
        SystemCache cache = SystemCache.getInstance();
        properties.entrySet().stream().forEach(entry->cache.put(entry.getKey(),entry.getValue()));
    }

    public String getProperty(String propertyName) {
        return (String) properties.get(propertyName);
    }
}
