package com.example.demo.mysql;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

@Component
public class UserLoader {
    private final UserRepository userRepository;

    UserLoader(UserRepository repository) {
        this.userRepository = repository;
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
        userRepository.save(n);

        System.out.println("--------------------");
        Flux.fromIterable(userRepository.findAll()).flatMap(u -> Mono.just(u)).subscribe(System.out::println);
    }
}