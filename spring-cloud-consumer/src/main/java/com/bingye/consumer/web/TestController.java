package com.bingye.consumer.web;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.bingye.commons.domain.po.Test;
import com.bingye.commons.domain.vo.CommonResult;

@RestController
@RefreshScope //spring config 手动刷新机制开启
public class TestController {
	//eureka 服务实例地址	
	public static final String PROVIDER_URL = "http://SPRING-CLOUD-PROVIDER";
	//zookeeper 服务实例地址
	//public static final String PROVIDER_URL = "http://spring-cloud-provider";
	//consul 服务实例地址
	//public static final String PROVIDER_URL = "http://spring-cloud-provider";
	
	@Resource
	private RestTemplate restTemplate;
	
	@SuppressWarnings("unchecked")
	@GetMapping("/test/get/{id}")
	public CommonResult<Test> getTestById(@PathVariable("id") String id) {
		return restTemplate.getForObject(PROVIDER_URL+"/spring-cloud-provider/test/get/"+id, CommonResult.class);
	}
	
	
	@Value("${ribbon.ReadTimeout}")
	public String connectimeOut;
	
	@GetMapping("/connectimeOut")
	public String connectimeOut() {
		return connectimeOut;
	}
}
