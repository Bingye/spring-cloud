package com.bingye.ribbon;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.loadbalancer.BestAvailableRule;
import com.netflix.loadbalancer.IRule;

@Configuration
public class CustomBestAvailableRule {
	
	@Bean
	public IRule getRule() {
		return new BestAvailableRule();
	}

}
