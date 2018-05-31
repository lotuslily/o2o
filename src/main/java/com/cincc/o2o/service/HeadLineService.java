package com.cincc.o2o.service;

import com.cincc.o2o.entity.HeadLine;

import java.io.IOException;
import java.util.List;

/**
 * @author li
 * Date: 2018/05/18
 */
public interface HeadLineService {
    /**
     *根据传入的条件返回指定的头条列表
     * @param headLineCondition
     * @return
     * @throws IOException
     */
    String HLLISTKEY = "headlinelist";
    List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException;

}
