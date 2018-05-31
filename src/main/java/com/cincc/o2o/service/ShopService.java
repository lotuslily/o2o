package com.cincc.o2o.service;

import com.cincc.o2o.dto.ImageHolder;
import com.cincc.o2o.dto.ShopExecution;
import com.cincc.o2o.entity.Shop;
import com.cincc.o2o.exceptions.ShopOperationException;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.InputStream;

/**
 * @author li
 * Date: 2018/05/07
 */
public interface ShopService {
    /**
     * 根据shopCondition分页返回相应店铺列表
     * @param shopCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public ShopExecution getShopList(Shop shopCondition,int pageIndex,int pageSize);
    /**
     * 注册店铺信息，包括图片处理
     *
     */
    ShopExecution addshop(Shop shop, ImageHolder thumbnail) throws ShopOperationException;

    /**
     * 通过店铺id获取店铺信息
     *
     */
    Shop getByShopId(long shopId);

    /**
     * 更新店铺信息，包括对图片的处理
     *
     */
    ShopExecution modifyShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException;


}
