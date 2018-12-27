package com.example.demo;

import com.alibaba.dubbo.config.annotation.Service;

@Service(
        application = "${dubbo.application.id}",
        version = "${demo.service.version}",
        protocol = "${dubbo.protocol.id}",
        registry = "${dubbo.registry.id}"
)
public class DubboProviderServiceImpl implements DubboProviderService {
    public String sayHello(String name) {
        return "Hello, " + name + " (from Spring Boot)";
    }
}
