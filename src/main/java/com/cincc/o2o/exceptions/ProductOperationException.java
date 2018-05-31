package com.cincc.o2o.exceptions;

/**
 * @author li
 * Date: 2018/05/07
 */
public class ProductOperationException extends RuntimeException {

    private static final long serialVersionUID = 2426451674131628304L;

    public ProductOperationException(String msg){
        super(msg);
    }
}
