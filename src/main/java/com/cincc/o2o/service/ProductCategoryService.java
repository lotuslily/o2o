package com.cincc.o2o.service;

import com.cincc.o2o.dto.ProductCategoryExecution;
import com.cincc.o2o.entity.ProductCategory;
import com.cincc.o2o.exceptions.ProductCategoryOperationException;

import java.util.List;

/**
 * @author li
 * Date: 2018/05/12
 */
public interface ProductCategoryService {
    /**
     * 查询某个商铺下的所有商品类别信息
     */
    List<ProductCategory> getProductCategoryList(long shopId);

    /**
     *批量插入商品类别
     * @throws RuntimeException
     */
    ProductCategoryExecution batchAddProductCategory(
            List<ProductCategory> productCategoryList) throws ProductCategoryOperationException;

    /**
     *将此类别下的商品里的类别id置为空，再删除掉该商品的类别
     * @param productCategoryId
     * @param shopId
     * @return
     * @throws RuntimeException
     */
    ProductCategoryExecution deleteProductCategory(long productCategoryId,
                                                   long shopId) throws ProductCategoryOperationException;
}
