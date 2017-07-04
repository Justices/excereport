package com.demo;

import com.mongodb.MongoClient;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;


/**
 * Created by liujiaping on 2017/6/30.
 */
public class MongoDBJDBC {

//    public static void main(String[] args) {
//        try{
//            MongoClient client = new MongoClient("localhost",27017);
//            MongoDatabase database = client.getDatabase("mudan");
//            System.out.println("connect to data base successfully");
//            MongoCollection collection = database.getCollection("mudan201701715");
//            System.out.println("获取集合成功");
////            Document document = new Document("title","mongoDB");
////
////            document.append("desc","database")
////                    .append("likes",100);
////            List<Document> documentList = new ArrayList<>();
////            documentList.add(document);
////            collection.insertMany(documentList);
////            System.out.println("插入文档成功");
////
////            /***
////             * 检查所有文档
////             * 1.get iterator  FindIteratable<Document>
////             * 2.get the cursor MongoCursor<Document>
////             * 3.query document from cursor
////             * */
////
////            FindIterable<Document> findIterable = collection.find();
////            MongoCursor<Document> mongoCursor = findIterable.iterator();
////            while (mongoCursor.hasNext()){
////                System.out.println(mongoCursor.next());
////            }
////            collection.updateMany(Filters.eq("likes",100),new Document("$set",new Document("likes",200)));
////            FindIterable findIterable = collection.find();
////            MongoCursor updateCoursor = findIterable.iterator();
////            while (updateCoursor.hasNext()) {
////                System.out.println(updateCoursor.next());
////            }
//            deleteCollection(collection);
//        }catch (Exception e){
//            System.out.println(e.getClass().getName() + ":"+e.getMessage());
//        }
//    }

    public static void deleteCollection(MongoCollection collection) {
        collection.deleteOne(Filters.eq("likes",200));
        collection.deleteMany(Filters.eq("likes",200));

        FindIterable iterable = collection.find();
        MongoCursor<Document> cursor = iterable.iterator();
        while(cursor.hasNext()){
            System.out.println(cursor.next());
        }

    }

}
