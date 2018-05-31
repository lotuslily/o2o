package com.cincc.o2o.exceptions;

/**
 * @author li
 * Date: 2018/05/07
 */
public class ShopOperationException extends RuntimeException {
    private static final long serialVersionUID = -4079740160651619216L;

    public ShopOperationException(String msg){
        super(msg);
    }
}
