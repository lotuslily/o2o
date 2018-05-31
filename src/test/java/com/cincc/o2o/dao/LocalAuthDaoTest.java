package com.cincc.o2o.dao;

import com.cincc.o2o.BaseTest;
import com.cincc.o2o.entity.LocalAuth;
import com.cincc.o2o.entity.PersonInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * @author li
 * Date: 2018/05/29
 */
public class LocalAuthDaoTest extends BaseTest {
    @Autowired
    private LocalAuthDao localAuthDao;
    private static final String username = "testusername";
    private static final String password = "testpassword";
    @Test
    public void queryLocalByUserNameAndPwd() {
        //按照账户密码查询用户信息
        LocalAuth localAuth=localAuthDao.queryLocalByUserNameAndPwd(username,password);
        assertEquals("li",localAuth.getPersonInfo().getName());
    }

    @Test
    public void queryLocalByUserId() {
        //按照用户ID查询平台账号，进而获取用户信息
        LocalAuth localAuth=localAuthDao.queryLocalByUserId(3L);
        assertEquals("li",localAuth.getPersonInfo().getName());
    }

    @Test
    public void insertLocalAuth() {
       //新增一条平台账号信息
        LocalAuth localAuth=new LocalAuth();
        PersonInfo personInfo=new PersonInfo();
        personInfo.setUserId(3L);
        localAuth.setPersonInfo(personInfo);
        localAuth.setPassword(password);
        localAuth.setUsername(username);
        localAuth.setCreateTime(new Date());
        int effectedNum=localAuthDao.insertLocalAuth(localAuth);
        assertEquals(1,effectedNum);
    }

    @Test
    public void updateLocalAuth() {
        Date now =new Date();
        String newpassword="newpassword";
        int effectednum=localAuthDao.updateLocalAuth(3L,username,password,newpassword,now);
        assertEquals(1,effectednum);
    }
}