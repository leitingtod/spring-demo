package com.example.demo.interfaces;

import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.model.Post;

public interface PostManagement {

    /** @param post */
    Post add(Post post) throws BadRequestException;

    /** @param id */
    void delete(String id);

    /** @param newPost */
    Post update(Post newPost) throws BadRequestException, NotFoundException;

    /** Iterable<Post> query(); */
    Iterable<Post> query();

    /** @param id /** */
    Post query(String id) throws NotFoundException;
}
