package com.example.demo;

import com.alibaba.dubbo.config.annotation.Service;

@Service(
        version = "${demo.service.version}",
        application = "${dubbo.application.id}",
        protocol = "${dubbo.protocol.id}",
        registry = "${dubbo.registry.id}"
)
public class DemoServiceImpl implements DemoService {
    public String sayHello(String name) {
        return "Hello, " + name + " (from Spring Boot)";
    }
}
