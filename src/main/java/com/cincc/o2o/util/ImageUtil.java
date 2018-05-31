package com.cincc.o2o.util;

import com.cincc.o2o.dto.ImageHolder;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author li
 * Date: 2018/05/05
 */
public class ImageUtil {
    public  static String basePath=Thread.currentThread().getContextClassLoader().getResource("").getPath();
    public  static final SimpleDateFormat sDateFormat =new SimpleDateFormat("yyyyMMddHHmmss");
    public  static final Random r=new Random();

    /**
     * 处理缩略图
     * @param thumbnail
     * @param targetAddr 缩略图文件保存路径
     * @return
     */
    public static String generateThumbnail(ImageHolder thumbnail, String targetAddr){
        //获取不重复的随机名
        String realFileName=getRandonFileName();
        //获取文件的扩展名，如png，jpg等
        String extension= getFileExtension(thumbnail.getImageName());
        //如果目标路径不存在则自动创建
        makeDirPath(targetAddr);
        //获取文件存储的相对路径（带文件名）
        String relativeAddr=targetAddr + realFileName + extension;
        //获取文件要保存到的目标路径
        File dest= new File(PathUtil.getImgBasePath()+ relativeAddr);
        //调用Thumbnail生成带有水印的图片
        try{
            Thumbnails.of(thumbnail.getImage()).size(200,200)
            .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath+"/op.jpg")),0.25f).outputQuality(0.8f).toFile(dest);
        }catch (IOException e){
            e.printStackTrace();
        }
        //返回图片相对路径地址
        return  relativeAddr;
    }

    /**
     * 处理详情图，并返回新生成图片的相对值路径
     * @param thumbnail
     * @param targetAddr
     * @return
     */
    public static String generateNormalImg(ImageHolder thumbnail, String targetAddr) {
        //获取不重复的随机名
        String realFileName=getRandonFileName();
        //获取文件的扩展名，如png，jpg等
        String extension= getFileExtension(thumbnail.getImageName());
        //如果目标路径不存在则自动创建
        makeDirPath(targetAddr);
        //获取文件存储的相对路径（带文件名）
        String relativeAddr=targetAddr + realFileName + extension;
        //获取文件要保存到的目标路径
        File dest= new File(PathUtil.getImgBasePath()+ relativeAddr);
        //调用Thumbnail生成带有水印的图片
        try{
            Thumbnails.of(thumbnail.getImage()).size(337,640)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath+"/op.jpg")),0.25f).outputQuality(0.9f).toFile(dest);
        }catch (IOException e){
            e.printStackTrace();
        }
        //返回图片相对路径地址
        return  relativeAddr;
    }

    /**
     * 生成随机文件名，当前的年月日小时分钟秒数+5位随机数
     */
    public static String getRandonFileName(){
        //获取随机的五位数
        //nextInt(n)将返回一个大于等于0小于n的随机数
        int rannum=r.nextInt(89999)+10000;
        String nowTimeStr=sDateFormat.format(new Date());
        return nowTimeStr+rannum;
    }

    /**
     * 获取输入文件的扩展名
     */
    private static String getFileExtension(String fileName){

        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     *
     *  创建目标路径所涉及到的目录，即/home/work/xiangce/xxx.jpg
     *  那么。home work xaingce 这三个文件夹都得自动创建
     */
    private static void makeDirPath(String targetAddr){
        String realFileParentPath=PathUtil.getImgBasePath()+targetAddr;
        File dirPath=new File(realFileParentPath);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
    }

    /**
     * shorePath是文件路径还是目录路径
     * 如果storePath是文件路径则删除该文件
     * 如果storePath是目录路径则删除该目录下的所有文件
     * @param storePath
     */
    public static void deleteFileOrPath(String storePath){
        File fileOrPath =new File(PathUtil.getImgBasePath()+storePath);
        if (fileOrPath.exists()){
            if(fileOrPath.isDirectory()){
                File files[]=fileOrPath.listFiles();
                for (int i=0;i<files.length;i++){
                    files[i].delete();
                }
            }
            fileOrPath.delete();
        }
    }




    public static void main(String[] args) throws IOException{
        Thumbnails.of(new File("C:\\Users\\lotus\\Desktop\\新建文件夹\\1.jpg"))
                .size(1000,800).watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath+"/op.jpg")),0.5f)
                .outputQuality(0.8f).toFile("C:\\Users\\lotus\\Desktop\\新建文件夹\\3.jpg");

    }
}
