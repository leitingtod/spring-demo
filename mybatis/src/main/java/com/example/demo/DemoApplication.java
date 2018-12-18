package com.example.demo;

import com.example.demo.mapper.CityMapper;
import com.example.demo.rbac.User;
import com.example.demo.rbac.UserMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
    private final CityMapper cityMapper;
    private final UserMapper userMapper;

    public DemoApplication(CityMapper cityMapper, UserMapper userMapper) {
        this.cityMapper = cityMapper;
        this.userMapper = userMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(this.cityMapper.findAll());
        System.out.println("---------------------------------");
        System.out.println(this.cityMapper.findByState("CA"));
        System.out.println("---------------------------------");
        System.out.println(this.cityMapper.findByCountry("CN"));
        System.out.println("---------------------------------\n");

        System.out.println(this.userMapper.findAll());
        System.out.println("---------------------------------");
        User user = new User();
        user.setName("admin3");
        user.setPassword("1235");
        user.setEmail("admin3@rbac.com");
        this.userMapper.insert1(user);
        System.out.println("id no return: " + user.getId());

        user.setName("admin5");
        user.setPassword("1235");
        user.setEmail("admin5@rbac.com");
        this.userMapper.insert2(user);
        System.out.println("id return not autoId: " + user.getId());

        user.setName("admin4");
        user.setPassword("1236");
        user.setEmail("admin4@rbac.com");
        this.userMapper.insert(user);
        System.out.println("id return autoId: " + user.getId());

        System.out.println("---------------------------------");
        System.out.println(this.userMapper.findAll());
        System.out.println("---------------------------------");
        user = this.userMapper.findById((long)2);
        System.out.println(user);
        user.setPassword("123");
        this.userMapper.update(user);
        System.out.println(this.userMapper.findById((long)2));
        System.out.println("---------------------------------");
        this.userMapper.delete((long)2);
        System.out.println(this.userMapper.findAll());
        System.out.println("---------------------------------");
        user.setName("admin7");
        user.setPassword("1237");
        user.setEmail("admin7@rbac.com");
        this.userMapper.insert(user);
        System.out.println("id return autoId: " + user.getId());
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
