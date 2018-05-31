package com.cincc.o2o.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @author li
 * Date: 2018/05/07
 */
public class HttpServletRequestUtil {
    public static int getInt(HttpServletRequest request,String key){
        try {
            return  Integer.decode(request.getParameter(key));
        } catch (Exception e) {
            return  -1;
        }
    }
    public static long getLong(HttpServletRequest request,String key){
        try {
            return  Long.decode(request.getParameter(key));
        } catch (Exception e) {
            return  -1;
        }
    }
    public static double getDouble(HttpServletRequest request,String key){
        try {
            return  Double.valueOf(request.getParameter(key));
        } catch (Exception e) {
            return  -1d;
        }
    }
    public static boolean getBoolean(HttpServletRequest request,String key){
        try {
            return  Boolean.valueOf(request.getParameter(key));
        } catch (Exception e) {
            return  false;
        }
    }
    public static String getString(HttpServletRequest request,String key){
        try {
            String result=request.getParameter(key);
            //去除字符串两侧空格
            if (result!=null) {
                result=result.trim();
            }
            if ("".endsWith(result)) {
                result=null;
            }
            return  result;
        } catch (Exception e) {
            return  null;
        }
    }
}
