package com.cincc.o2o.dao;

import com.cincc.o2o.BaseTest;
import com.cincc.o2o.entity.Area;
import com.cincc.o2o.entity.PersonInfo;
import com.cincc.o2o.entity.Shop;
import com.cincc.o2o.entity.ShopCategory;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author li
 * Date: 2018/05/05
 */
public class ShopDaoTest extends BaseTest {
    @Autowired
    private ShopDao shopDao;


    @Test
    public void testQueryShopList(){
        Shop shopCondition =new Shop();
        ShopCategory childCategory=new ShopCategory();
        ShopCategory parentCategory=new ShopCategory();
        parentCategory.setShopCategoryId(1L);
        childCategory.setParent(parentCategory);
        shopCondition.setShopCategory(childCategory);

        List<Shop> shopList=shopDao.queryShopList(shopCondition,0,10);
        int count=shopDao.queryShopCount(shopCondition);
        System.out.println("店铺列表大小：" + shopList.size());
        System.out.println("店铺总数：" + count);

    }


    @Test
    @Ignore
    public void testQueryByShopId(){
        long shopId=8;
        Shop shop=shopDao.queryByShopId(shopId);
        System.out.println("area_id"+shop.getArea().getAreaId());
        System.out.println("area_name"+shop.getArea().getAreaName());
    }


    @Test
    @Ignore
    public void testInsertShop(){
        Shop shop=new Shop();

        PersonInfo owner =new PersonInfo();
        Area area=new Area();
        ShopCategory shopCategory=new ShopCategory();

        owner.setUserId(3L);
        area.setAreaId(13);
        shopCategory.setShopCategoryId(1L);

        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("测试的店铺");
        shop.setShopDesc("test");
        shop.setShopAddr("test");
        shop.setPhone("test");
        shop.setShopImg("test");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(1);
        shop.setAdvice("审核中");

        int effectedNum =shopDao.insertShop(shop);
        assertEquals(1,effectedNum);

    }

    @Test
    @Ignore
    public void testUpdateShop(){
        Shop shop=new Shop();
        shop.setShopId(1L);
        shop.setShopDesc("测试描述");
        shop.setShopAddr("测试地址");
        shop.setLastEditTime(new Date());
        int effectedNum =shopDao.updateShop(shop);
        assertEquals(1,effectedNum);

    }
}
