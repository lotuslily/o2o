package com.cincc.o2o.service;

import com.cincc.o2o.entity.ShopCategory;

import java.util.List;

/**
 * @author li
 * Date: 2018/05/08
 */
public interface ShopCategoryService {
    public static final String SCLISTKEY = "shopcategorylist";
    List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);
}
