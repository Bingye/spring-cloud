package com.bingye.ribbon;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.loadbalancer.AvailabilityFilteringRule;
import com.netflix.loadbalancer.IRule;

@Configuration
public class CustomAvailabilityFilteringRule {
	
	@Bean
	public IRule getRule() {
		return new AvailabilityFilteringRule();
	}

}
