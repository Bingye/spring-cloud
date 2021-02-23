package com.bingye.consumer.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.bingye.commons.domain.po.Test;
import com.bingye.commons.domain.vo.CommonResult;
import com.bingye.consumer.service.hystrix.FeignHystixServiceImpl;

@Component
@FeignClient(value="SPRING-CLOUD-PROVIDER",path="spring-cloud-provider" , fallback=FeignHystixServiceImpl.class)
public interface IFeignService {

	@GetMapping("/test/get/{id}")
	public CommonResult<Test> get(@PathVariable("id") String id);
	
	@GetMapping("/timeout")
	public String timeout();
	
}
