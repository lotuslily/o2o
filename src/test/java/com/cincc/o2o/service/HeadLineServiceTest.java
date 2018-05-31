package com.cincc.o2o.service;

import com.cincc.o2o.BaseTest;
import com.cincc.o2o.entity.Area;
import com.cincc.o2o.entity.HeadLine;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author li
 * Date: 2018/05/05
 */
public class HeadLineServiceTest extends BaseTest {
    @Autowired
    private HeadLineService headLineService;
    @Test
    public void testGetHeadLineList() throws IOException {
        HeadLine headLine=new HeadLine();
        headLine.setEnableStatus(0);
        List<HeadLine> headLineList=headLineService.getHeadLineList(headLine);
    }
}
