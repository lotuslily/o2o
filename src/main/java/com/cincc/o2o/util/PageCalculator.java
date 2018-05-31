package com.cincc.o2o.util;

/**
 * @author li
 * Date: 2018/05/11
 */
public class PageCalculator {
    public static int calculateRowIndex(int pageIndex,int pageSize){
        return (pageIndex>0)?(pageIndex-1)*pageSize:0;
    }

}
