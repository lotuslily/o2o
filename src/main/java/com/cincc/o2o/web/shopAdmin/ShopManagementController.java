package com.cincc.o2o.web.shopAdmin;

import com.cincc.o2o.dto.ImageHolder;
import com.cincc.o2o.dto.ShopExecution;
import com.cincc.o2o.entity.Area;
import com.cincc.o2o.entity.PersonInfo;
import com.cincc.o2o.entity.Shop;
import com.cincc.o2o.entity.ShopCategory;
import com.cincc.o2o.enums.ShopStateEnum;
import com.cincc.o2o.exceptions.ShopOperationException;
import com.cincc.o2o.service.AreaService;
import com.cincc.o2o.service.ShopCategoryService;
import com.cincc.o2o.service.ShopService;
import com.cincc.o2o.util.CodeUtil;
import com.cincc.o2o.util.HttpServletRequestUtil;
import com.cincc.o2o.util.ImageUtil;
import com.cincc.o2o.util.PathUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author li
 * Date: 2018/05/07
 */
@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {
    @Autowired
    private ShopService shopService;

    @Autowired
    private ShopCategoryService shopCategoryService;

    @Autowired
    private AreaService areaService;

    @RequestMapping(value = "/getshopmanagementinfo",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getShopManagementInfo(HttpServletRequest request){
        Map<String,Object> modelMap=new HashMap<>();
        long shopId=HttpServletRequestUtil.getLong(request,"shopId");
        modelMap.put("test",shopId);
        if (shopId<=0){
            Object currentShopObj =request.getSession().getAttribute("currentShop");
            if(currentShopObj==null){
                modelMap.put("redirect",true);
                modelMap.put("url","/shopadmin/shoplist");
            }else {
                Shop currentShop=(Shop)currentShopObj;
                modelMap.put("redirect",false);
                modelMap.put("shopId",currentShop.getShopId());
            }
        }else {
            Shop currentShop=new Shop();
            currentShop.setShopId(shopId);
            request.getSession().setAttribute("currentShop",currentShop);
            modelMap.put("redirect",false);
        }
        return modelMap;
    }

    @RequestMapping(value = "/getshoplist",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getShopList(HttpServletRequest request){
        Map<String,Object> modelMap=new HashMap<>();
        PersonInfo user=new PersonInfo();
        user.setUserId(3L);
        user.setName("lotus");
        request.getSession().setAttribute("user",user);
        user=(PersonInfo)request.getSession().getAttribute("user");
        try {
            Shop shopCondition=new Shop();
            shopCondition.setOwner(user);
            ShopExecution se=shopService.getShopList(shopCondition,0,100);
            modelMap.put("shopList",se.getShopList());
            modelMap.put("user",user);
            modelMap.put("success",true);
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
        }
        return modelMap;
    }


    @RequestMapping(value = "/getshopbyid",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getShopById(HttpServletRequest request){
        Map<String,Object> modelMap=new HashMap<>();
        Long shopId=HttpServletRequestUtil.getLong(request,"shopId");
        if (shopId>-1){
            try {
                Shop shop=shopService.getByShopId(shopId);
                List<Area> areaList=areaService.getAreaList();
                modelMap.put("shop",shop);
                modelMap.put("areaList",areaList);
                modelMap.put("success",true);
            } catch (Exception e) {
                modelMap.put("success",false);
                modelMap.put("errMsg",e.toString());
            }
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","empty shopId");
        }
        return modelMap;
    }

    @RequestMapping(value = "getshopinitinfo",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getShopInitInfo(){
        Map<String,Object> modelMap=new HashMap<>();
        List<ShopCategory> shopCategoryList=new ArrayList<>();
        List<Area> areaList=new ArrayList<>();
        try {
            shopCategoryList=shopCategoryService.getShopCategoryList(new ShopCategory());
            areaList=areaService.getAreaList();
            modelMap.put("success",true);
            modelMap.put("shopCategoryList",shopCategoryList);
            modelMap.put("areaList",areaList);
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
        }
        return modelMap;
    }



    @RequestMapping(value = "registershop",method = RequestMethod.POST)
    @ResponseBody
    /**
     * request封装了所有的客户端提交的http请求信息
     * @param request
     * @return
     */
    private Map<String,Object> registerShop(HttpServletRequest request){
        Map<String,Object> modelMap=new HashMap<>();
        if (!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","输入了错误的验证码");
            return modelMap;
        }
        //1.接收并转换相应的参数，包括店铺信息以及图片信息
        /**
         * 接收前端传过来的信息，并将它们转换为实体类
         */
        String shopStr=HttpServletRequestUtil.getString(request,"shopStr");
        // 使用jackson-databind-->https://github.com/FasterXML/jackson-databind
        ObjectMapper mapper = new ObjectMapper();
        Shop shop=null;
        try {
            shop=mapper.readValue(shopStr,Shop.class);
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
            return  modelMap;
        }

        /**
         * 获取前端传递过来的文件流，接收到shopImg里面
         */
        CommonsMultipartFile shopImg=null;
        //文件上传解析器
        //获取本次会话的上下文，取得本次上传文件的相关内容，
        CommonsMultipartResolver commonsMultipartResolver=new CommonsMultipartResolver(
                request.getSession().getServletContext()
        );
        //判断request里面是不是有上传的文件流
        if (commonsMultipartResolver.isMultipart(request)){
            MultipartHttpServletRequest multipartHttpServletRequest=(MultipartHttpServletRequest)request;
            shopImg=(CommonsMultipartFile)multipartHttpServletRequest.getFile("shopImg");
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","上传文件不能为空");
            return modelMap;
        }
        //2.注册店铺
        if(shop!=null && shopImg!=null){
            //获取session
            PersonInfo owner=(PersonInfo)request.getSession().getAttribute("user");
            shop.setOwner(owner);

            /**
             * 将CommonsMultipartFile类型过渡为File类型
             */
//            File shopImgFile=new File(PathUtil.getImgBasePath() + ImageUtil.getRandonFileName());
//            try {
//                shopImgFile.createNewFile();
//            } catch (IOException e) {
//                modelMap.put("success",false);
//                modelMap.put("errMsg",e.getMessage());
//                return modelMap;
//            }

//            try {
//                inputStreamToFile(shopImg.getInputStream(),shopImgFile);
//            } catch (IOException e) {
//                modelMap.put("success",false);
//                modelMap.put("errMsg",e.getMessage());
//                return modelMap;
//            }
//
//            ShopExecution se=shopService.addshop(shop,shopImgFile);

            ShopExecution se= null;
            try {
                ImageHolder imageHolder=new ImageHolder(shopImg.getOriginalFilename(),shopImg.getInputStream());
                se = shopService.addshop(shop,imageHolder);
            } catch (IOException e) {
                modelMap.put("success",false);
                modelMap.put("errMsg",se.getStateInfo());
            }

            if(se.getState()== ShopStateEnum.CHECK.getState()){
                modelMap.put("success","true");
                //该用户可以操作的店铺列表
                List<Shop> shopList=(List<Shop>)request.getSession().getAttribute("shopList");
                if(shopList==null || shopList.size()==0) {
                    shopList = new ArrayList<>();
                }
                shopList.add(se.getShop());
                request.getSession().setAttribute("shopList",shopList);


            }else {
                modelMap.put("success",false);
                modelMap.put("errMsg",se.getStateInfo());
            }
            return modelMap;

        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","请输入店铺信息");
            return modelMap;
        }
        //3.返回结果
    }

    @RequestMapping(value = "modifyshop",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> modifyShop(HttpServletRequest request){
        Map<String,Object> modelMap=new HashMap<>();
        if (!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","输入了错误的验证码");
            return modelMap;
        }
        //1.接收并转换相应的参数，包括店铺信息以及图片信息
        /**
         * 接收前端传过来的信息，并将它们转换为实体类
         */
        String shopStr=HttpServletRequestUtil.getString(request,"shopStr");
        // 使用jackson-databind-->https://github.com/FasterXML/jackson-databind
        ObjectMapper mapper = new ObjectMapper();
        Shop shop=null;

        try {
            shop=mapper.readValue(shopStr,Shop.class);
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
            return modelMap;
        }

        /**
         * 获取前端传递过来的文件流，接收到shopImg里面
         */
        CommonsMultipartFile shopImg=null;
        //文件上传解析器
        //获取本次会话的上下文，取得本次上传文件的相关内容，
        CommonsMultipartResolver commonsMultipartResolver=new CommonsMultipartResolver(
                request.getSession().getServletContext()
        );
        //判断request里面是不是有上传的文件流
        if (commonsMultipartResolver.isMultipart(request)){
            MultipartHttpServletRequest multipartHttpServletRequest=(MultipartHttpServletRequest)request;
            shopImg=(CommonsMultipartFile)multipartHttpServletRequest.getFile("shopImg");
        }

        //2.修改店铺信息
        if(shop!=null && shop.getShopId()!=null) {
            ShopExecution se = null;
            try {
                if (shopImg == null) {
                    se = shopService.modifyShop(shop, null);
                } else {
                    ImageHolder imageHolder=new ImageHolder(shopImg.getOriginalFilename(),shopImg.getInputStream());
                    se = shopService.modifyShop(shop,imageHolder);
                }

                if (se.getState() == ShopStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", "true");
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", se.getStateInfo());
                }
            } catch (ShopOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            } catch (IOException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
            return modelMap;
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","请输入店铺Id");
            return modelMap;
        }
    }

    /**
     * CommonsMultipartFile 里getInputStream()方法返回类型为java File的InputStream类型
     * 借此可以用FileOutPutStream输出到File
     * 将CommonsMultipartFile类型过渡为File类型
     * @param ins
     * @param file
     */
//    private static void inputStreamToFile(InputStream ins, File file){
//
//        FileOutputStream os=null;
//        try {
//            os=new FileOutputStream(file);
//            int bytesRead=0;
//            byte[] buffer=new byte[1024];
//            //read方法返回的是实际读取的字节数
//            while ((bytesRead=ins.read(buffer))!=-1){
//                os.write(buffer,0,bytesRead);
//            }
//        } catch (Exception e) {
//            throw new RuntimeException("调用inputStreamToFile产生异常" + e.getMessage());
//        } finally {
//            try {
//                if (os!=null){
//                    os.close();
//                }
//                if (ins!=null){
//                    ins.close();
//                }
//            } catch (IOException e) {
//                throw new RuntimeException("inputStreamToFile关闭io产生异常" + e.getMessage());
//            }
//        }
//    }
}
