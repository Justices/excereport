package com.service.file.impl;

import com.mongodb.BaseDao;
import com.service.file.ExcelFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liujiaping on 2017/7/2.
 */
@Service
public class ExcelFileServiceImpl implements ExcelFileService {

    @Autowired
    private BaseDao baseDao;

    @Override
    public void saveDate(String collectionName, List data) {
        if(baseDao.isCollectionExists(collectionName)){
            baseDao.updateCollection(collectionName,data);
        }else{
            baseDao.createCollection(collectionName);
            baseDao.updateCollection(collectionName,data);
        }
    }
}
