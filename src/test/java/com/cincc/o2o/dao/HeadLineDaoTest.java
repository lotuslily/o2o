package com.cincc.o2o.dao;

import com.cincc.o2o.BaseTest;
import com.cincc.o2o.entity.HeadLine;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author li
 * Date: 2018/05/18
 */
public class HeadLineDaoTest extends BaseTest {
    @Autowired
    private HeadLineDao headLineDao;

    @Test
    public void testQueryArea(){
        List<HeadLine> headLineList=headLineDao.queryHeadLine(new HeadLine());
        assertEquals(3,headLineList.size());
    }
}
