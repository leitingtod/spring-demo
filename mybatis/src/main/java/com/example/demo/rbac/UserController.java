package com.example.demo.rbac;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class UserController {
    @SuppressWarnings("all")
    @Autowired
    UserService userService;

    @GetMapping("/user")
    public List<User> getUser() {
        return userService.getAll();
    }

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable("id") long id) {
        return userService.getUser(id);
    }


    @GetMapping("/user/{id}/account/update/{account}/{commit}/{delay}")
    public Object updateAccount(@PathVariable("id") long id, @PathVariable("account") BigDecimal account, @PathVariable("commit") String commit, @PathVariable("delay") int delay) {
        try {
            userService.setAccount(account, id, commit, delay);
        } catch (Exception e) {
            return e.toString()+" - " + userService.getUser(id);
        }
        return getUser(id);
    }
}
