package com.mongodb;


import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liujiaping on 2017/7/2.
 */
@Repository
public class BaseDao<T>{

    @Autowired
    private MongoTemplate mongoTemplate;

    public <T> List<T> find(Query query, Class<T> type){
        return  mongoTemplate.find(query,type);
    }

    public void createCollection(String collection) {
        mongoTemplate.createCollection(collection);
    }

    public void updateCollection(String collection, List documents){
        mongoTemplate.insert(documents,collection);
    }

    public boolean isCollectionExists(String collectionName) {
        return mongoTemplate.collectionExists(collectionName);
    }

    public <T> List<T> findDataByCollectionName(Query query, Class<T> type, String collectionName){
        return mongoTemplate.find(query,type,collectionName);
    }

    public DBCollection getCollection(String collectionName) {
       return mongoTemplate.getCollection(collectionName);
    }
}
