package com.example.demo;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DubboConsumerController {

    @Reference(version = "${demo.service.version}",
            url = "dubbo://localhost:12345")
    private DubboProviderService providerService;

    @RequestMapping("/sayHello")
    public String sayHello(@RequestParam String name) {
        return providerService.sayHello(name);
    }
}
