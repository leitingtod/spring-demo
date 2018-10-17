package com.example.demo.mongo;

import lombok.AllArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/mongo")
public class PersonController {

    private final PersonRepository personRepository;

    /**
     * 新增一个 Person
     */
    @PostMapping("/person")
    public Mono<Void> add(@RequestBody Publisher<Person> person){
        return personRepository.insert(person).then();
    }

    /**
     * 根据 ID 查询 Person
     */
    @GetMapping("/person/{id}")
    public Mono<Person> getById(@PathVariable Long id){
        return personRepository.findById(id);
    }

    /**
     * 查询所有 Person
     */
    @GetMapping("/person/list")
    public Flux<Person> list(){
        return personRepository.findAll();
    }

    /**
     * 删除指定 Person
     */
    @DeleteMapping("/person/{id}")
    public Mono<Void> delete(@PathVariable Long id){
        return personRepository.deleteById(id).then();
    }
}

