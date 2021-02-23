package com.bingye.provider.web;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.bingye.commons.domain.po.Test;
import com.bingye.commons.domain.vo.CommonResult;
import com.bingye.provider.service.ITestService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@RestController
public class TestController {

	@Resource
	public ITestService testService;
	
	@Value("${server.port}")
	public String serverPort;
	
	@GetMapping("/test/create")
	public CommonResult<Test> create(Test test) {
		try {
			int num = testService.create(test);
			if(num > 0) {
				return new CommonResult<Test>(200, "success", test);
			}else{
				return new CommonResult<Test>(404, "failure", null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new CommonResult<Test>(500, "error");
		}
	}
	
	@GetMapping("/test/get/{id}")
	public CommonResult<Test> get(@PathVariable("id") String id) {
		try {
			Test test = testService.getTestById(id);
			if(test != null) {
				return new CommonResult<Test>(200, "success serverPort="+serverPort, test);
			}else{
				return new CommonResult<Test>(404, "failure", null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new CommonResult<Test>(500, "error");
		}
	}
	
	@HystrixCommand(
            commandProperties ={
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value ="5000" ),
                    @HystrixProperty(name = "circuitBreaker.enabled",value= "true"), //是否打开断路器
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value= "10"), //最大请求次数
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value= "1000"), //时间窗口期
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value= "60") //失败率达到多少跳闸
            }
            ,fallbackMethod ="timeoutHystrix" //容错方法
    )
	@GetMapping("/timeout")
	public String timeout() {
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return serverPort;
	}
	
	public String timeoutHystrix() {
		return "容错处理";
	}
	
}
