package com.cincc.o2o.dao;

import com.cincc.o2o.BaseTest;
import com.cincc.o2o.entity.Area;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author li
 * Date: 2018/05/05
 */
public class AreaDaoTest extends BaseTest{
    @Autowired
    private AreaDao areaDao;

    @Test
    @Ignore
    public void testQueryArea(){
        List<Area> areaList=areaDao.queryArea();
        assertEquals(3,areaList.size());
    }
}
