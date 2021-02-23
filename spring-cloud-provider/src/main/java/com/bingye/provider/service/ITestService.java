package com.bingye.provider.service;

import com.bingye.commons.domain.po.Test;

public interface ITestService {
	
	int create(Test test);
	
	Test getTestById(String id);

}
