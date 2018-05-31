package com.cincc.o2o.dao;

import com.cincc.o2o.entity.LocalAuth;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * @author li
 * Date: 2018/05/29
 */
public interface LocalAuthDao {
    /**
     * 根据传入的用户名和密码查询对应的信息，登录时使用
     * @param username
     * @param password
     * @return
     */
    LocalAuth queryLocalByUserNameAndPwd(@Param( "username" ) String username,
                                             @Param( "password" ) String password);

    /**
     * 通过userId查询对应的localAuth
     * @param userId
     * @return
     */
    LocalAuth queryLocalByUserId(@Param( "userId" ) long userId);

    /**
     * 添加平台账号
     * @param localAuth
     * @return
     */
    int insertLocalAuth(LocalAuth localAuth);

    /**
     * 通过userId，username，password更改密码
     * @return
     */
    int updateLocalAuth(@Param( "userId" ) Long userId,
                        @Param( "username" ) String username,
                        @Param( "password" ) String password,
                        @Param( "newPassword" ) String newPassword,
                        @Param( "lastEditTime" ) Date lastEditTime);
}
