package com.example.demo.mongo;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonLoader {
    private final PersonRepository personRepository;

    PersonLoader(PersonRepository repository) {
        this.personRepository = repository;
    }

    @PostConstruct
    public void loadData() {
        personRepository.deleteAll().thenMany(personRepository.findAll()).subscribe();
        List<Person> ps = new ArrayList<>();
        ps.add(new Person(1L, "Alice"));
        ps.add(new Person(2L, "Tom"));
        ps.add(new Person(3L, "Jane"));
        Flux<Person> persons = Flux.fromIterable(ps);
        personRepository.insert(persons).thenMany(personRepository.findAll().flatMap(p-> Mono.just(p))).subscribe(System.out::println);
    }
}

