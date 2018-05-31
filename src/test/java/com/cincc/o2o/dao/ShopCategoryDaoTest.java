package com.cincc.o2o.dao;

import com.cincc.o2o.BaseTest;
import com.cincc.o2o.entity.ShopCategory;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author li
 * Date: 2018/05/08
 */
public class ShopCategoryDaoTest extends BaseTest{
    @Autowired
    private ShopCategoryDao shopCategoryDao;
    @Test
//    public void testQueryShopCategory(){
//        List<ShopCategory> shopCategoryList=shopCategoryDao.queryshopCategory(new ShopCategory());
//        assertEquals(2,shopCategoryList.size());
//        ShopCategory testCategory=new ShopCategory();
//        ShopCategory parentCategory=new ShopCategory();
//        parentCategory.setShopCategoryId(1L);
//        testCategory.setParent(parentCategory);
//        shopCategoryList=shopCategoryDao.queryshopCategory(testCategory);
//        assertEquals(1,shopCategoryList.size());
//        System.out.println(shopCategoryList.get(0).getShopCategoryName());
//    }
    public void testQueryShopCategory(){
        List<ShopCategory> shopCategoryList=shopCategoryDao.queryshopCategory(new ShopCategory());
        System.out.println(shopCategoryList.size());
    }

}
