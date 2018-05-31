package com.cincc.o2o.web.shopAdmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author li
 * Date: 2018/05/07
 */

/**
 * 主要用来解析路由并转发到相应的html中 WEB-INF下的页面只有经过转发才对外部可见
 */
@Controller
@RequestMapping(value = "/shopadmin",method = {RequestMethod.GET})
public class ShopAdminController {
    @RequestMapping(value = "/shopoperation")
    //转发至店铺注册/编辑页面
    public String shopOperation(){
        //spring-web.xml已经定义视图解析器 前缀为/WEB-INF/html 后缀为html
        //所以只要返回/WEB-INF/html目录下的路径
        //经过SpringMVC的转发，拼上头和尾
        return "shop/shopoperation";
    }

    @RequestMapping(value = "/shoplist")
    //转发至店铺列表页面
    public String shopList(){
        return "shop/shoplist";
    }

    @RequestMapping(value = "/shopmanagement")
    public String shopManagement(){
        return "shop/shopmanagement";
    }

    @RequestMapping(value = "/productcategorymanagement")
    public String productCategoryManagement(){
        return "shop/productcategorymanagement";
    }

    @RequestMapping(value = "/productoperation")
    public String productOperation(){
        return "shop/productoperation";
    }

    @RequestMapping(value = "/productmanagement")
    public String productManagement(){
        return "shop/productmanagement";
    }
}
