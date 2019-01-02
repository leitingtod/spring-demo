package com.example.demo.api.controller;

import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.NotAllowedException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.exceptions.PasswordWeakException;
import com.example.demo.interfaces.PostManagement;
import com.example.demo.model.Post;
import com.google.gson.Gson;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/posts")
public class RestPosts {
    @Autowired private PostManagement postManagement;

    @Autowired private Gson gson;

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Post create(@RequestBody @Valid Post post)
            throws PasswordWeakException, NotAllowedException, BadRequestException,
                    NotFoundException {
        log.info("Adding post: " + post.getTitle());
        postManagement.add(post);
        return post;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") String id) {
        log.info("Removing post with id " + id);
        postManagement.delete(id);
    }

    @RequestMapping(
            value = "/multipledelete",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void multipleDelete(@RequestBody @Valid List<String> ids) throws NotFoundException {
        if (postManagement != null) {
            for (String id : ids) {
                log.info("removing Post with id " + id);
                postManagement.delete(postManagement.query(id).getId());
            }
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Post> findAll() {
        log.trace("Find all Posts");
        return (List<Post>) postManagement.query();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Post findById(@PathVariable("id") String id) throws NotFoundException {
        log.trace("Find all Posts");
        try {
            return postManagement.query(id);
        } catch (Exception e) {
            throw new NotFoundException(e.toString());
        }
    }

    @RequestMapping(
            value = "{id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Post update(@RequestBody @Valid Post newPost)
            throws BadRequestException, NotFoundException {
        return postManagement.update(newPost);
    }
}
