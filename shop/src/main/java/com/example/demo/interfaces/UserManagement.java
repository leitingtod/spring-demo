package com.example.demo.interfaces;

import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.NotAllowedException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.exceptions.PasswordWeakException;
import com.example.demo.model.User;

public interface UserManagement {

    /**
     * @param user
     */
    User add(User user) throws PasswordWeakException, BadRequestException;

    /**
     * @param user
     */
    void delete(User user);

    /**
     * @param newUser
     */
    User update(User newUser) throws NotAllowedException, BadRequestException, NotFoundException;

    /**
     * Iterable<User> query();
     *
     * @param username
     */
    User queryByName(String username) throws NotFoundException;

    /**
     * @param id
     */
    User query(String id) throws NotFoundException;

    /**
     * @param oldPwd
     * @param newPwd
     */
    void changePassword(String oldPwd, String newPwd) throws PasswordWeakException;

    /**
     * @param userName
     * @param newPwd
     */
    User changePasswordOf(String userName, String newPwd)
            throws PasswordWeakException, NotFoundException;

    User getCurrentUser() throws NotFoundException;
}
