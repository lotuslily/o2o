package com.cincc.o2o.dao;

import com.cincc.o2o.entity.ShopCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author li
 * Date: 2018/05/08
 */
public interface ShopCategoryDao {
    List<ShopCategory> queryshopCategory(@Param("shopCategoryCondition") ShopCategory shopCategoryCondition);
}
