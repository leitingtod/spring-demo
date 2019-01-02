package com.example.demo.service;

import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.model.Post;
import com.example.demo.repository.PostRepository;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PostManagement implements com.example.demo.interfaces.PostManagement {
    @Autowired private PostRepository postsRepository;

    @Override
    public Post add(Post post) throws BadRequestException {
        log.debug("Adding new post: " + post);
        postsRepository.save(post);
        return post;
    }

    @Override
    public void delete(String id) {
        log.debug("Deleting post with id: " + id);
        postsRepository.deleteById(id);
    }

    @Override
    public Post update(Post newPost) throws BadRequestException, NotFoundException {
        Post postToUpdate = query(newPost.getId());
        postToUpdate.setTitle(newPost.getTitle());
        postToUpdate.setContent(newPost.getContent());
        postsRepository.save(postToUpdate);
        postToUpdate.setUpdated(new Date());
        return postToUpdate;
    }

    @Override
    public Iterable<Post> query() {
        return postsRepository.findAll();
    }

    @Override
    public Post query(String id) throws NotFoundException {
        try {
            return postsRepository.findFirstById(id);
        } catch (Exception e) {
            throw new NotFoundException(e.toString());
        }
    }
}
