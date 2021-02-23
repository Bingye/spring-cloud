package com.bingye.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient  //该注解用于向eureka作为注册中心时注册服务
@EnableDiscoveryClient //该注解用于向consul或者zookeeper作为注册中心时注册服务
@EnableCircuitBreaker //开启服务Hystrix服务降级、熔断功能
public class ProviderApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ProviderApplication.class, args);
	}
}
