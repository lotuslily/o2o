package com.cincc.o2o.service.impl;

import com.cincc.o2o.dao.LocalAuthDao;
import com.cincc.o2o.dao.PersonInfoDao;
import com.cincc.o2o.dto.ImageHolder;
import com.cincc.o2o.dto.LocalAuthExecution;
import com.cincc.o2o.entity.LocalAuth;
import com.cincc.o2o.entity.PersonInfo;
import com.cincc.o2o.enums.LocalAuthStateEnum;
import com.cincc.o2o.exceptions.LocalAuthOperationException;
import com.cincc.o2o.service.LocalAuthService;
import com.cincc.o2o.util.ImageUtil;
import com.cincc.o2o.util.MD5;
import com.cincc.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author li
 * Date: 2018/05/29
 */
@Service
public class LocalAuthServiceImpl implements LocalAuthService {
    @Autowired
    private LocalAuthDao localAuthDao;
    @Autowired
    PersonInfoDao personInfoDao;

    @Override
    public LocalAuth getLocalUserByUserNameAndPwd(String username, String password) {
        return localAuthDao.queryLocalByUserNameAndPwd(username, MD5.getMd5(password));
    }

    @Override

    public LocalAuth getLocalAuthByUserId(long userId) {
        return localAuthDao.queryLocalByUserId(userId);
    }

    @Override
    public LocalAuthExecution modyfiLocalAuth(long userId, String username, String password, String newPassword) {
        //此处userId若为Long包装类型，可使用==null判断非空，基本类型不可以
        //非空判断，并且新旧密码是否相同，若相同，则不进行修改
        if (userId >=1 && username != null && password != null && newPassword != null && !password.equals(newPassword)) {
            try {
                int effectedNum = localAuthDao.updateLocalAuth(userId, username, MD5.getMd5(password), MD5.getMd5(newPassword), new Date());
                if (effectedNum <= 0) {
                    throw new LocalAuthOperationException("更新密码失败");
                }
                return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS);
            } catch (Exception e) {
                throw new LocalAuthOperationException("更新密码失败:" + e.toString());
            }
        } else {
            return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
        }
    }

    @Override
    @Transactional
    public LocalAuthExecution registerLocalAuth(LocalAuth localAuth, ImageHolder thumbnail) {
        if (localAuth == null || localAuth.getPassword() == null || localAuth.getUsername() == null) {
            return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
        }
        try {
            localAuth.setCreateTime(new Date());
            localAuth.setLastEditTime(new Date());
            localAuth.setPassword(MD5.getMd5(localAuth.getPassword()));
            if (localAuth.getPersonInfo() != null && localAuth.getPersonInfo().getUserId()==null) {
                if (thumbnail != null) {
                    try {
                        //将用户头像放入用户所在的文件夹下
                        addProfileImg(localAuth, thumbnail);
                    } catch (Exception e) {
                        throw new LocalAuthOperationException("添加头像失败！");
                    }
                }
                localAuth.getPersonInfo().setCreateTime(new Date());
                localAuth.getPersonInfo().setLastEditTime(new Date());
                localAuth.getPersonInfo().setEnableStatus(1);
                try {
                    //插入用户详细信息的数据到person_info
                    PersonInfo personInfo = localAuth.getPersonInfo();
                    int effectedNum = personInfoDao.insertPersonInfo(personInfo);
                    if (effectedNum <= 0) {
                        throw new LocalAuthOperationException("添加用户信息失败");
                    }
                } catch (Exception e) {
                    throw new LocalAuthOperationException("insertPersonInfo error: " + e.getMessage());
                }
            }
            // 然后在将用户注册信息插入到local_auth中
            int effectedNum = localAuthDao.insertLocalAuth(localAuth);
            if (effectedNum <= 0) {
                throw new LocalAuthOperationException("帐号创建失败");
            } else {
                return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS, localAuth);
            }
        } catch (Exception e) {

            throw new RuntimeException("insertLocalAuth error: " + e.getMessage());
        }
    }

    /**
     * 将用户头像添加到用户的文件夹下，并将本地用户信息中的头像地址更新
     * @param localAuth
     * @param thumbnail
     */
    private void addProfileImg(LocalAuth localAuth, ImageHolder thumbnail) {
        String dest = PathUtil.getPersonImagePath();
        String profileImgAddr = ImageUtil.generateThumbnail(thumbnail, dest);
        localAuth.getPersonInfo().setProfileImg(profileImgAddr);
    }
}
