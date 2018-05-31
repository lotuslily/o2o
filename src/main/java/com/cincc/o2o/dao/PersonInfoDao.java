package com.cincc.o2o.dao;

import com.cincc.o2o.entity.PersonInfo;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * @author li
 * Date: 2018/05/29
 */
public interface PersonInfoDao {

	/**
	 * 
	 * @param personInfoCondition
	 * @param rowIndex
	 * @param pageSize
	 * @return
	 */
	List<PersonInfo> queryPersonInfoList(
            @Param("personInfoCondition") PersonInfo personInfoCondition,
            @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

//	/**
//	 *
//	 * @param personInfoCondition
//	 * @return
//	 */
//	int queryPersonInfoCount(
//            @Param("personInfoCondition") PersonInfo personInfoCondition);


	PersonInfo queryPersonInfoById(long userId);


	int insertPersonInfo(PersonInfo personInfo);


	int updatePersonInfo(PersonInfo personInfo);

	int deletePersonInfo(long userId);
}
