package com.service.file.impl;

import com.document.parser.ExcelParser;
import com.mongodb.BaseDao;
import com.service.file.ExcelFileService;
import com.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created by liujiaping on 2017/7/2.
 */
@Service
public class ExcelFileServiceImpl implements ExcelFileService {

    @Autowired
    private BaseDao baseDao;

    @Override
    public void saveDate(MultipartFile multipartFile) {

        List itemList = ExcelParser.getInstance().fileParser(multipartFile);
        String collectionName = "flyer_".concat(Util.getTodayString());
        if(baseDao.isCollectionExists(collectionName)){
            baseDao.updateCollection(collectionName,itemList);
        }else{
            baseDao.createCollection(collectionName);
            baseDao.updateCollection(collectionName,itemList);
        }
    }
}
