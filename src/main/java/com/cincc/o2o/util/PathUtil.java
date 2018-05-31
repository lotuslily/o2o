package com.cincc.o2o.util;

/**
 * @author li
 * Date: 2018/05/05
 */
public class PathUtil {
    private static String separator=System.getProperty("file.separator");

    /**
     * 提供项目图片所要存储的根路径
     * 工具类，不需要实例化，所以用静态方法，调用如下
     * String realFileParentPath=PathUtil.getImgBasePath();
     * @return
     */
    public static String getImgBasePath(){
        String os=System.getProperty("os.name");
        String basePath="";
        if (os.toLowerCase().startsWith("win")){
            basePath="D:/projectdev/image/";
        }else {
            basePath="/home/xiangce/image";
        }
        basePath=basePath.replace("/",separator);
        return basePath;
    }

    /**
     * 根据shopID获取商铺图片路径
     * @param shopId
     * @return
     */
    public static String getShopImagePath(long shopId){
        String imagePath="/upload/item/shop" + shopId +"/";
        return imagePath.replace("/",separator);
    }

    /**
     * 根据userID获取用户图片路径
     * @return
     */
    public static String getPersonImagePath(long userId){
        String imagePath="/upload/item/personinfo" +"/"+userId +"/";
        return imagePath.replace("/",separator);
    }
}
