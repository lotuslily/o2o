package com.cincc.o2o.exceptions;

/**
 * @author li
 * Date: 2018/05/07
 */
public class ProductCategoryOperationException extends RuntimeException {

    private static final long serialVersionUID = 2673187858238417136L;

    public ProductCategoryOperationException(String msg){
        super(msg);
    }
}
