package com.mongodb;

import lombok.Data;

import java.util.List;

/**
 * Created by liujiaping on 2017/7/6.
 */
@Data
public class PageModel<T> {

    private List<T> data;

    private Long rowCount;

    private int pageSize;

    private int pageNo;

    private int skip;
}
