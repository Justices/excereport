package com.service.report;

import com.BaseTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
}
