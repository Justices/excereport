package com.service.average.impl;

import com.document.to.RetailTo;
import com.mongodb.RetailDao;
import com.service.average.AverageService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by liujiaping on 2017/7/2.
 */
public class AverageServiceImpl implements AverageService {

    @Autowired
    private RetailDao retailDao;

    @Override
    public void getAverageRate() {

    }
}
