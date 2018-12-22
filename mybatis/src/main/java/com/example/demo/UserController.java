package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public Object index() {
        return "hello world!";
    }

    @RequestMapping("/account/update")
    public Object updateAccount() throws Exception {
        userService.updateAccount(1, 1, 0);
        return userService.getUser(1L);
    }

    @RequestMapping("/user")
    public Object getUser() throws Exception {
        return userService.getUser(1L);
    }
}
