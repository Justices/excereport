package com.cache;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by liujiaping on 2017/7/1.
 */
public class SystemCache extends ConcurrentHashMap{
    public static SystemCache cacheMap = null;

    private SystemCache(){
        super();
    }

    public static SystemCache getInstance() {
        if(cacheMap==null){
            return new SystemCache();
        }
        return cacheMap;
    }
}
