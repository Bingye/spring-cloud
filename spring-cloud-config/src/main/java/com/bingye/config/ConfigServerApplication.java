package com.bingye.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
//激活配置中心
@EnableConfigServer
public class ConfigServerApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ConfigServerApplication.class, args);
	}

}
