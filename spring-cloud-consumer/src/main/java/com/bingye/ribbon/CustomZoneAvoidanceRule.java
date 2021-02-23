package com.bingye.ribbon;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.ZoneAvoidanceRule;

@Configuration
public class CustomZoneAvoidanceRule {
	
	@Bean
	public IRule getRule() {
		return new ZoneAvoidanceRule();
	}

}
