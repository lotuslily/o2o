package com.cincc.o2o.service;

import com.cincc.o2o.entity.Area;

import java.util.List;

/**
 * @author li
 * Date: 2018/05/05
 */
public interface AreaService {
    String AREALISTKEY = "arealist";
    List<Area> getAreaList();

}
