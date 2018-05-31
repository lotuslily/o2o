package com.cincc.o2o.service;

import com.cincc.o2o.dto.ImageHolder;
import com.cincc.o2o.dto.LocalAuthExecution;
import com.cincc.o2o.entity.LocalAuth;

/**
 * @author li
 * Date: 2018/05/29
 */
public interface LocalAuthService {
    /**
     * 通过账号和密码获取平台账号信息
     * @param username
     * @param password
     * @return
     */
    LocalAuth getLocalUserByUserNameAndPwd(String username, String password);


    /**
     * 通过userId获取本地账号信息
     * @param userId
     * @return
     */
    LocalAuth getLocalAuthByUserId(long userId);


    /**
     * 修改本地账号的密码
     * @param userId
     * @param username
     * @param password
     * @param newPassword
     * @return
     */
    LocalAuthExecution modyfiLocalAuth(long userId, String username, String password, String newPassword);


    /**
     * 本地用户注册，和用户头像上传
     * @param localAuth
     * @param thumbnail
     * @return
     */
    LocalAuthExecution registerLocalAuth(LocalAuth localAuth, ImageHolder thumbnail);

}
