package com.bingye.consumer.web;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.bingye.commons.domain.po.Test;
import com.bingye.commons.domain.vo.CommonResult;
import com.bingye.consumer.service.IFeignService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@RestController
@DefaultProperties(defaultFallback="globalHystrix",commandProperties ={@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value ="5000" )})
public class FeignController {

	@Resource
	private IFeignService openFeignService;
	
	@GetMapping("/openfeign/test/get/{id}")
	public CommonResult<Test> get(@PathVariable("id") String id) {
		return openFeignService.get(id);
	}
	
	@HystrixCommand(fallbackMethod ="timeoutHystrix")
	@GetMapping("/openfeign/timeout")
	public String timeout() {
		return openFeignService.timeout();
	}
	
	public String timeoutHystrix() {
		return "消费端容错处理";
	}
	
	public String globalHystrix() {
		return "全局容错处理";
	}
}
