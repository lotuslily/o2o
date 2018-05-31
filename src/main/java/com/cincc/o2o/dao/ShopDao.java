package com.cincc.o2o.dao;

import com.cincc.o2o.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author li
 * Date: 2018/05/05
 */
public interface ShopDao {
    /**
     * 分页查询店铺，可输入的条件有：店铺名（模糊），店铺状态，店铺类别，区域id，owner
     * @param shopCondition
     * @param rowIndex 从第几行开始取数据
     * @param pageSize 返回的条数
     * 使用@Param的目的是在有多个参数时，用Param的值区分不同参数
     * @return
     */
    List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition,
                             @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

    /**
     *
     * @param ShopCondition
     * @return 返回shopList总数
     */
    int queryShopCount(@Param("shopCondition") Shop ShopCondition);


    /**
     * 通过shop id查询店铺
     * @param
     * @return
     */
    Shop queryByShopId(long shopId);
    /**
     * 新增店铺
     * @param shop
     * @return
     */
    int insertShop(Shop shop);

    /**
     * 更新店铺信息
     */
    int updateShop(Shop shop);
}
