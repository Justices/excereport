package com.document.to;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by liujiaping on 2017/6/30.
 */
@Data
public class RetailTo implements Serializable {


    private static final long serialVersionUID = 8706625513655465323L;
    private String id ;

    private String shopType;

    private Long year;

    private String season;

    private String productNo;

    private String itemNo;

    private String shopNo;

    private Long count;

    private BigDecimal standardAmount;

    private BigDecimal dealAmount;

    private BigDecimal expectAmount;

    private BigDecimal standardPrice;
}
