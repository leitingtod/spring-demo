package com.example.demo;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubboConfig;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
@DubboComponentScan(basePackages = "com.example.demo")
@EnableDubboConfig
public class DubboProvider {

	public static void main(String[] args) {
		new SpringApplicationBuilder(DubboProvider.class)
				.web(WebApplicationType.NONE)
				.run(args);
	}
}
