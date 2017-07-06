package com.demo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.PageModel;
import com.mongodb.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import sun.jvm.hotspot.debugger.Page;

import java.util.List;

/**
 * Created by liujiaping on 2017/7/6.
 */
public class BasicDBBsonDemo {


    @Autowired
    private MongoTemplate template;

    //demo for Basic Query,basic query
    private void testbasciQuery() {
        DBObject object = new BasicDBObject();
        //db.collection.find({'_id': '595a192da3f9353f6bd6fbb7'})
        object.put("_id", "595a192da3f9353f6bd6fbb7");
        Query query = new BasicQuery(object);
        template.find(query,String.class);

    }

    //tests and
    private void testand (){
        //db.collection.find({$and : [{shortType: "002"},{"itemNo":"adkfjdl"}]})
        Query  query = new Query(Criteria.where("shortType").is("002").and("itemNo").is("adkfjdl"));
        template.find(query,String.class);
    }

    private void testQueryBuilder() {
        //select onNumber,cname from collection where onNumber = 'o02' or cnmae = 'zcy1'
        QueryBuilder queryBuilder = new QueryBuilder();
        queryBuilder.or(new BasicDBObject("onNumber","002"),new BasicDBObject("cname","zcy1"));
        BasicDBObject object = new BasicDBObject();
        object.put("onnumber",1);
        object.put("cname",1);
        Query query = new BasicQuery(queryBuilder.get(),object);
        template.find(query,String.class);
    }

    /**
     *db.collection.update(<query>,<update>,<upsert>:<boolean>, multi:<boolean>)
     * query : query conditons
     * <update> : the expected modified key and value
     * upsert:
     * multi:
     * **/
    private void testUpdate() {
//        db.orders.update(
//                {"onumber" :"001"},
//        {$set: { "cname " : "zcy2"} },
//        true,
//                true
//        )
        template.upsert(new Query(Criteria.where("onnumber").is("001")),new Update().set("cnname","zcy"),"collectionName");


    }

    /**
    * 分页查询
    * */

    private void testPageQuery() {
        PageModel<String> model = new PageModel<String>();
        model.setPageNo(1);
        model.setPageSize(300);
        DBObject object = new BasicDBObject();
        Query  query = new BasicQuery(object);
        Long count = template.count(query,String.class);
        model.setRowCount(count);
        query.with(new Sort(Sort.Direction.ASC,"onnumber"));
        query.skip(model.getSkip()).limit(model.getPageSize());
        List<String> list = template.find(query,String.class);
        model.setData(list);
    }

}
