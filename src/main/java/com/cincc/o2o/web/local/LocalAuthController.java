package com.cincc.o2o.web.local;

import com.cincc.o2o.dto.ImageHolder;
import com.cincc.o2o.dto.LocalAuthExecution;
import com.cincc.o2o.entity.LocalAuth;
import com.cincc.o2o.entity.PersonInfo;
import com.cincc.o2o.enums.LocalAuthStateEnum;
import com.cincc.o2o.exceptions.LocalAuthOperationException;
import com.cincc.o2o.service.LocalAuthService;
import com.cincc.o2o.util.CodeUtil;
import com.cincc.o2o.util.HttpServletRequestUtil;
import com.cincc.o2o.util.MD5;
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
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author li
 * Date: 2018/05/30
 */
@Controller
@RequestMapping(value = "/local")
public class LocalAuthController {
    @Autowired
    LocalAuthService localAuthService;
    /**
     * 修改本地用户的登录密码
     * @param request
     * @return
     */
    @RequestMapping(value="/changelocalpwd", method= RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> changeLocalPwd(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();
        //验证码校验
        if(!CodeUtil.checkVerifyCode(request)){
            modelMap.put( "success", false );
            modelMap.put( "errMsg", "验证码错误" );
            return modelMap;
        }
        String username = HttpServletRequestUtil.getString( request, "username" );
        String password = HttpServletRequestUtil.getString( request, "password" );
        String newPassword = HttpServletRequestUtil.getString( request, "newPassword" );


        PersonInfo user = (PersonInfo) request.getSession().getAttribute( "user" );
        //非空判断
        if(username != null && password != null && newPassword != null && user != null && user.getUserId()!=null && !password.equals( newPassword ) ){
            try{
                //通过系统传过来的userId来从数据库获取localUser，判断是否是本人操作，即用户名和密码是否存在且匹配
                LocalAuth localAuth = localAuthService.getLocalAuthByUserId( user.getUserId() );

                if(localAuth == null || !localAuth.getPassword().equals( MD5.getMd5(password) )){
                    modelMap.put( "success", false );
                    modelMap.put( "errMsg", "账号非本次登录账号" );
                    return modelMap;
                }
                //修改本地用户的密码
                LocalAuthExecution localAuthExecution = localAuthService.modyfiLocalAuth( user.getUserId(), username, password, newPassword );
                if(localAuthExecution.getState() == LocalAuthStateEnum.SUCCESS.getState()){
                    modelMap.put( "success", true );
                } else{
                    modelMap.put( "success", false );
                    modelMap.put( "errMsg", localAuthExecution.getStateInfo() );
                }
            }catch (LocalAuthOperationException e){
                modelMap.put( "success", false );
                modelMap.put( "errMsg", e.getMessage() );
                return modelMap;
            }
        } else{
            modelMap.put( "success", false );
            modelMap.put( "errMsg", "请输入密码" );
        }
        return modelMap;
    }

    /**
     * 本地用户注册
     * @param request
     * @return
     */
    @RequestMapping(value="/localuserregister", method=RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> localUserRegister(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();
        //验证码校验
        if(!CodeUtil.checkVerifyCode(request)) {
            modelMap.put( "success", false );
            modelMap.put( "errMsg", "验证码错误" );
            return modelMap;
        }
        //1.接收并转化相应的参数，包括本地账户，图片信息
        ObjectMapper objectMapper = new ObjectMapper(  );
        LocalAuth localAuth = null;
        String localAuthStr = HttpServletRequestUtil.getString( request, "localAuth" );
        if(localAuthStr != null){
            try {
                localAuth = objectMapper.readValue( localAuthStr, LocalAuth.class);
            } catch (IOException e) {
                modelMap.put( "success", false );
                modelMap.put( "errMsg", e.getMessage() );
                return modelMap;
            }
        }
        ImageHolder thumbnail = null;
        //在本次会话的上下文获取上传的文件
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver( request.getSession().getServletContext() );
        try {
            if (multipartResolver.isMultipart(request)) {
                MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
                //取出缩略图并构建ImageHolder对象
                CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartRequest.getFile("thumbnail");
                if (thumbnailFile != null) {
                    thumbnail=new ImageHolder(thumbnailFile.getOriginalFilename(),thumbnailFile.getInputStream());
                }
            }
        }catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }

        //进行本地用户注册 (根据表tb_local_auth UNIQUE KEY `UK_LOCAL_PROFILE` (`user_name`)设计，注册时的user_name不能重名否则报错）
        if(localAuth != null && localAuth.getUsername() != null && localAuth.getPassword() != null && thumbnail != null){
            try{
                LocalAuthExecution localAuthExecution = localAuthService.registerLocalAuth( localAuth, thumbnail );
                if(localAuthExecution.getState() == LocalAuthStateEnum.SUCCESS.getState()){
                    modelMap.put( "success", true );
                } else{
                    modelMap.put( "success", false );
                    modelMap.put( "errMsg", localAuthExecution.getStateInfo() );
                }
            } catch (Exception e){
                modelMap.put( "success", false );
                modelMap.put( "errMsg", e.toString() );
                return modelMap;
            }
        } else{
            modelMap.put( "success", false );
            modelMap.put( "errMsg", "请输入注册信息" );
        }
        return modelMap;
    }

    /**
     * 对登录用户进行核对
     * @param request
     * @return
     */
    @RequestMapping(value="/logincheck", method=RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> logincheck(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();
        //获取是否需要由验证码校验的标识符
        boolean needVerify = HttpServletRequestUtil.getBoolean(request, "needVerify");
        if (needVerify && !CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }
        String username = HttpServletRequestUtil.getString( request, "userName" );
        String password = HttpServletRequestUtil.getString( request, "password" );

        //非空校验
        if(username != null && password != null){
            //从数据库获取username和password对应的用户信息，如果存在，则证明正确，否则提示用户不存在或密码错误
            LocalAuth localAuth = localAuthService.getLocalUserByUserNameAndPwd( username, password );
            if(localAuth != null){
                modelMap.put( "success", true );
                request.getSession().setAttribute( "user", localAuth.getPersonInfo() );
            } else{
                modelMap.put( "success", false );
                modelMap.put( "errMsg", "用户名或密码错误" );
            }
        } else{
            modelMap.put( "success", false );
            modelMap.put( "errMsg", "用户名和密码均不能为空" );
        }
        return modelMap;
    }


    /**
     * 用户点击登出按钮时，注销掉session中的user
     * @param request
     * @return
     */
    @RequestMapping(value="/logout", method=RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> logout(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();
        //将session中的user注销掉
        request.getSession().setAttribute( "user", null );
        modelMap.put( "success", true );
        return modelMap;
    }
}
