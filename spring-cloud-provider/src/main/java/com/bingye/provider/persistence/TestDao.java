package com.bingye.provider.persistence;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.bingye.commons.domain.po.Test;

@Mapper
public interface TestDao {
	
	int create(Test test);
	
	Test getTestById(@Param("id") String id);
	
}
