package com.cincc.o2o.service;

import com.cincc.o2o.BaseTest;
import com.cincc.o2o.dto.ImageHolder;
import com.cincc.o2o.dto.ShopExecution;
import com.cincc.o2o.entity.Area;
import com.cincc.o2o.entity.PersonInfo;
import com.cincc.o2o.entity.Shop;
import com.cincc.o2o.entity.ShopCategory;
import com.cincc.o2o.enums.ShopStateEnum;
import com.cincc.o2o.exceptions.ShopOperationException;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * @author li
 * Date: 2018/05/07
 */
public class ShopServiceTest extends BaseTest{
    @Autowired
    private ShopService shopService;

    @Test
    public void testQueryShopList(){
        Shop shopCondition=new Shop();
        ShopCategory sc=new ShopCategory();
        sc.setShopCategoryId(3L);
        shopCondition.setShopCategory(sc);
        ShopExecution se=shopService.getShopList(shopCondition,2,2);
        System.out.println("店铺列表数" + se.getShopList().size());
        System.out.println("店铺总数" + se.getCount());
    }


    @Test
    @Ignore
    public void testModifyShop() throws ShopOperationException,FileNotFoundException{
        Shop shop=new Shop();
        shop.setShopId(8L);
        shop.setShopName("修改后的店铺名称");
        File shopImg=new File("C:\\Users\\lotus\\Desktop\\新建文件夹\\2.jpg");
        InputStream is=new FileInputStream(shopImg);
        ImageHolder imageHolder=new ImageHolder("2.jpg",is);
        ShopExecution shopExecution=shopService.modifyShop(shop,imageHolder);
        System.out.println("新的图片地址:"+ shopExecution.getShop().getShopImg());
    }

    @Test
    @Ignore
    public void testAddShop(){
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
        shop.setShopName("测试的店铺2");
        shop.setShopDesc("test1");
        shop.setShopAddr("test1");
        shop.setPhone("test1");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setAdvice("审核中");

        File shopImg=new File("C:\\Users\\lotus\\Desktop\\新建文件夹\\1.jpg");
        InputStream is= null;
        try {
            is = new FileInputStream(shopImg);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ImageHolder imageHolder=new ImageHolder(shopImg.getName(),is);
        ShopExecution se=shopService.addshop(shop,imageHolder);
        assertEquals(ShopStateEnum.CHECK.getState(),se.getState());


    }

}
