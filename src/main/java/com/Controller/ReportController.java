package com.Controller;

import com.document.to.RetailTo;
import com.service.report.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by liujiaping on 2017/7/6.
 */
@Controller
@RequestMapping(value = "/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @RequestMapping(value = "/sourceData/{collectionName}",method = RequestMethod.POST)
    @ResponseBody
    public List<RetailTo> getSourceData(@PathVariable String collectionName){
        return reportService.getSourceData(collectionName);

    }
}
