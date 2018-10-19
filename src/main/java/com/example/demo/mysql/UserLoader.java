package com.example.demo.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

@Component
public class UserLoader {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    UserLoader(UserRepository repository, UserMapper userMapper) {
        this.userRepository = repository;
        this.userMapper = userMapper;
    }

    @PostConstruct
    public void loadData() {

        User n = new User();
        n.setName("root");
        n.setEmail("root@test.com");
        userRepository.save(n);

        n = new User();
        n.setName("nginx");
        n.setEmail("nginx@test.com");
        userRepository.save(n);

        n = new User();
        n.setName("apache");
        n.setEmail("apache@test.com");
        userMapper.insert(n);

        n = new User();
        n.setName("mybatis");
        n.setEmail("mybatis@test.com");
        userMapper.insert(n);


        Flux.fromIterable(userRepository.findAll()).flatMap(u -> Mono.just(u))
                .subscribe(System.out::println);

        Flux.fromIterable(userMapper.getAll()).flatMap(u -> Mono.just(u))
                .subscribe(System.out::println);
    }
}
