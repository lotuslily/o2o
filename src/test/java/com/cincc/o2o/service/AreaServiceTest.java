package com.cincc.o2o.service;

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
public class AreaServiceTest extends BaseTest {
    @Autowired
    private AreaService areaService;
    @Autowired
    private CacheService cacheService;
    @Test
    public void testGetAreaList(){
        List<Area>areaList=areaService.getAreaList();
        assertEquals("南京",areaList.get(0).getAreaName());
        cacheService.removeFromCache(AreaService.AREALISTKEY);
        List<Area>areaList1=areaService.getAreaList();
    }
}
