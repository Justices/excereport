package com.service.report.impl;

import com.document.to.RetailTo;
import com.mongodb.*;
import com.service.report.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;
import org.springframework.data.mongodb.core.aggregation.Field;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        List<RetailTo>  retailToList=  baseDao.findDataByCollectionName(new Query().limit(100),RetailTo.class,collectionName);
        return retailToList;

    }

    /**
     *  db.flyer_20170703.group({
     *      key:{"shopNo":true,"productNo":true},
     *      cond:{},initial:{"totalStandardAmount":0,totalDealAmount:0},
     *      reduce: function(doc,prev) {
     *      prev.totalStandardAmount = prev.totalStandardAmount + doc.standardAmount;
     *      prev.totalDealAmount = prev.totalDealAmount + doc.dealAmount ;
     *  },
     *  finalize: function(out) {
     *      return out;
     *  } })
     * */
    @Override
    public List getShopAmount(String collectionName) {
        DBObject groupKey = new BasicDBObject();
        groupKey.put("shopNo",true);
        groupKey.put("productNo",true);

        DBObject indexObject = new BasicDBObject();
        indexObject.put("totalStandardAmount",0.0f);
        indexObject.put("totalDealAmount",0.0f);

        DBObject initObject  = new BasicDBObject();
        initObject.put("totalObject",indexObject);


        String reduce = "function(doc, out) {" +
                " out.totalObject.totalStandardAmount = out.totalObject.totalStandardAmount + doc.standardAmount;"
                +"out.totalObject.totalDealAmount = out.totalObject.totalDealAmount + doc.dealAmount ;" +
                "}";

        DBCollection dbCollection = baseDao.getCollection(collectionName);

        BasicDBList resultList = (BasicDBList) dbCollection.group(groupKey,new BasicDBObject(),initObject,reduce);
        return resultList;

    }
}
