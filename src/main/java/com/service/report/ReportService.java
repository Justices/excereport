package com.service.report;

import com.document.to.RetailTo;
import java.util.List;

/**
 * Created by liujiaping on 2017/7/6.
 */
public interface ReportService {

    List<RetailTo> getSourceData(String collectionName);

    List getShopAmount(String collectionName);

    List getDealPriceRate(String collectionName);
}
