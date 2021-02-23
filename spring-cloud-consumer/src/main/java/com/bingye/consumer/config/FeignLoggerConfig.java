package com.bingye.consumer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Logger;

@Configuration
public class FeignLoggerConfig {
	
	@Bean
	Logger.Level feignLoggerLevel(){
		return Logger.Level.FULL;
	}

}
