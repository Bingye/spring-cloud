package com.bingye.provider.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bingye.commons.domain.po.Test;
import com.bingye.provider.persistence.TestDao;
import com.bingye.provider.service.ITestService;

@Service
public class TestServiceImpl implements ITestService {
	
	@Resource
	public TestDao testDao;

	@Override
	public int create(Test test) {
		return testDao.create(test);
	}

	@Override
	public Test getTestById(String id) {
		return testDao.getTestById(id);
	}

}
