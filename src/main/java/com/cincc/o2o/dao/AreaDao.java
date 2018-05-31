package com.cincc.o2o.dao;

import com.cincc.o2o.entity.Area;

import java.util.List;

/**
 * @author li
 * Date: 2018/05/05
 */
public interface AreaDao {
    /**
     * 列出区域列表
     * @return arealist
     */
    List<Area> queryArea();
}
