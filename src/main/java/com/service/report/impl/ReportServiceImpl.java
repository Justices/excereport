package com.service.report.impl;

import com.document.to.RetailTo;
import com.mongodb.BaseDao;
import com.service.report.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liujiaping on 2017/7/6.
 */
@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private BaseDao<RetailTo> baseDao;

    @Override
    public List<RetailTo> getSourceData(String collectionName) {
        List<RetailTo>  retailToList=  baseDao.findDataByCollectionName(new Query(),RetailTo.class,collectionName);
        return retailToList;

    }
}
