package com.cincc.o2o.dao;

import com.cincc.o2o.entity.ProductImg;

import java.util.List;

/**
 * @author li
 * Date: 2018/05/12
 */
public interface ProductImgDao {
    List<ProductImg> queryProductImgList(long productId);

    /**
     * 批量添加商品详情图片
     * @param productImgList
     * @return
     */
    int batchInsertProductImg(List<ProductImg> productImgList);

    /**
     * 删除指定商品下的所有详情图
     * @param productId
     * @return
     */
    int deleteProductImgByProductId(long productId);
}
