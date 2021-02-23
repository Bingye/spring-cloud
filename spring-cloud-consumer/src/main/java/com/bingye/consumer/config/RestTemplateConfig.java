package com.bingye.consumer.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

	@Bean
	@LoadBalanced  //赋予RestTemplate负载均衡的能力 当添加@LoadBalanced注解，就代表启动Ribbon,进行负载均衡
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	
}
