package com.service.report;

import com.BaseTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by liujiaping on 2017/7/6.
 */
public class ReportServiceTest extends BaseTestCase{

    @Autowired
    private ReportService reportService;

    @Test
    public void testFindSource() {
        String collectionName = "flyer_20170704";
        reportService.getSourceData(collectionName);
    }

    @Test
    public void testGetShopAmount() {
        String collectionName = "flyer_20170703";
        reportService.getShopAmount(collectionName);
    }

    @Test
    public void testGetDealPriceRate() {
        String collectionName = "flyer_20170703";
        List resultList = reportService.getDealPriceRate(collectionName);
        resultList.parallelStream().forEach(item-> System.out.println(item));
    }
}
