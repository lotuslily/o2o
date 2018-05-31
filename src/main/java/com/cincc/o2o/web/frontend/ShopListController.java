package com.cincc.o2o.web.frontend;

import com.cincc.o2o.dto.ShopExecution;
import com.cincc.o2o.entity.Area;
import com.cincc.o2o.entity.Shop;
import com.cincc.o2o.entity.ShopCategory;
import com.cincc.o2o.service.AreaService;
import com.cincc.o2o.service.ShopCategoryService;
import com.cincc.o2o.service.ShopService;
import com.cincc.o2o.util.HttpServletRequestUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author li
 * Date: 2018/05/19
 */

@Controller
@RequestMapping("/frontend")
public class ShopListController {
    @Autowired
    private AreaService areaService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private ShopService shopService;

    /**
     * 返回商品列表页里的ShopCategory列表（二级或者一级），以及区域信息列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/listshopspageinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listShopsPageInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //试着从前端请求中获取parentId
        long parentId = HttpServletRequestUtil.getLong(request, "parentId");
        List<ShopCategory> shopCategoryList = null;
        if (parentId != -1) {
            //取出一级下的二级ShopCategory列表
            try {
                ShopCategory shopCategoryCondition = new ShopCategory();
                ShopCategory parent = new ShopCategory();
                parent.setShopCategoryId(parentId);
                shopCategoryCondition.setParent(parent);
                shopCategoryList = shopCategoryService.getShopCategoryList(shopCategoryCondition);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
        } else {
            try {
                //如果parentId不存在，则取出所有一级ShopCategory（即用户在首页选择的是全部商店列表）
                shopCategoryList = shopCategoryService.getShopCategoryList(null);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
        }
        modelMap.put("shopCategoryList", shopCategoryList);
        List<Area> areaList = null;
        try {
            areaList = areaService.getAreaList();
            modelMap.put("areaList", areaList);
            modelMap.put("success", true);
            return modelMap;
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

    }

    /**
     * 获取指定查询条件下的商铺列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/listshops", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listShops(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        if ((pageIndex > -1) && (pageSize > -1)) {
            long parentId = HttpServletRequestUtil.getLong(request, "parentId");
            long shopCategoryId = HttpServletRequestUtil.getLong(request, "shopCategoryId");
            int areaId = HttpServletRequestUtil.getInt(request, "areaId");
            String shopName = HttpServletRequestUtil.getString(request, "shopName");
            Shop shopCondition = compactShopCondition4Search(parentId, shopCategoryId, areaId, shopName);
            ShopExecution se = shopService.getShopList(shopCondition, pageIndex, pageSize);
            modelMap.put("shopList", se.getShopList());
            modelMap.put("count", se.getCount());
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageSize or pageIndex");
        }
        return modelMap;
    }

    /**
     * 组合查询条件，并将条件封装到ShopCondition对象里返回
     * @param parentId
     * @param shopCategoryId
     * @param areaId
     * @param shopName
     * @return
     */
        private Shop compactShopCondition4Search(long parentId, long shopCategoryId, int areaId, String shopName) {
            Shop shopCondition = new Shop();
            if (parentId != -1L) {
                //查询某个一级ShopCategory下面的所有二级ShopCategory里面的店铺列表
                ShopCategory parentCategory = new ShopCategory();
                ShopCategory childCategory=new ShopCategory();
                parentCategory.setShopCategoryId(parentId);
                childCategory.setParent(parentCategory);
                shopCondition.setShopCategory(childCategory);
            }
            if (shopCategoryId != -1L) {
                //查询某个二级ShopCategory下面的店铺列表
                ShopCategory shopCategory = new ShopCategory();
                shopCategory.setShopCategoryId(shopCategoryId);
                shopCondition.setShopCategory(shopCategory);
            }
            if (areaId != -1L) {
                //查询位于某个区域ID下的店铺列表
                Area area = new Area();
                area.setAreaId(areaId);
                shopCondition.setArea(area);
            }

            if (shopName != null) {
                //查询名字里包含shopName的店铺列表
                shopCondition.setShopName(shopName);
            }
            //前端展示的店铺都是审核成功的店铺
            shopCondition.setEnableStatus(1);
            return shopCondition;
        }

}
