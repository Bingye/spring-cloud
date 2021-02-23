package com.bingye.ribbon;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RetryRule;

@Configuration
public class CustomRetryRule {
	
	@Bean
	public IRule getRule() {
		return new RetryRule();
	}

}
