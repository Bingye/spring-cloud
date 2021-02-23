package com.bingye.consumer.service.hystrix;

import org.springframework.stereotype.Service;

import com.bingye.commons.domain.po.Test;
import com.bingye.commons.domain.vo.CommonResult;
import com.bingye.consumer.service.IFeignService;

@Service
public class FeignHystixServiceImpl implements IFeignService{

	@Override
	public CommonResult<Test> get(String id) {
		return new CommonResult<Test>(500, "服务端down机了");
	}

	@Override
	public String timeout() {
		return "服务端down机了";
	}

}
