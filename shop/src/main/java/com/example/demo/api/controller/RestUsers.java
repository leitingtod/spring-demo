package com.example.demo.api.controller;

import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.NotAllowedException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.exceptions.PasswordWeakException;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.service.UserManagement;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
public class RestUsers {

    @Autowired
    private UserManagement userManagement;
    @Autowired
    private Gson gson;

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public User create(@RequestBody @Valid User user)
            throws PasswordWeakException, NotAllowedException, BadRequestException, NotFoundException {
        log.info("Adding user: " + user.getUsername());
        if (isAdmin()) {
            user = userManagement.add(user);
            //      user.setPassword(null);
        } else {
            throw new NotAllowedException("Forbidden to create a new user");
        }
        return user;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public void delete(@PathVariable("id") String id) throws NotAllowedException, NotFoundException {
        log.info("Removing user with id " + id);
        if (isAdmin()) {
            if (!userManagement.getCurrentUser().getId().equals(id)) {
                User user = userManagement.query(id);
                userManagement.delete(user);
            } else {
                throw new NotAllowedException("You can't delete yourself. Please ask another admin.");
            }
        } else {
            throw new NotAllowedException("Forbidden to delete a user");
        }
    }

    @RequestMapping(
            value = "/multipledelete",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void multipleDelete(@RequestBody @Valid List<String> ids) throws NotFoundException {
        if (userManagement != null) {
            for (String id : ids) {
                log.info("removing User with id " + id);
                userManagement.delete(userManagement.query(id));
            }
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<User> findAll() {
        log.trace("Find all Users");
        return (List<User>) userManagement.query();
    }

    /**
     * Returns the User selected by username
     *
     * @param username : The username of the User
     * @return User: The User selected
     */
    @RequestMapping(value = "{username}", method = RequestMethod.GET)
    public User findById(@PathVariable("username") String username) throws NotFoundException {
        log.trace("find User with username " + username);
        User user = userManagement.query(username);
        log.trace("Found User: " + user);
        return user;
    }


    @RequestMapping(value = "current", method = RequestMethod.GET)
    public User findCurrentUser() throws NotFoundException {
        User user = userManagement.getCurrentUser();
        log.trace("Found User: " + user);
        return user;
    }

    @RequestMapping(
            value = "{username}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public User update(@RequestBody @Valid User new_user)
            throws NotAllowedException, BadRequestException, NotFoundException {
        return userManagement.update(new_user);
    }

    @RequestMapping(
            value = "changepwd",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void changePassword(@RequestBody /*@Valid*/ JsonObject newPwd)
            throws UnauthorizedUserException, PasswordWeakException {
        log.debug("Changing password");
        JsonObject jsonObject = gson.fromJson(newPwd, JsonObject.class);
        userManagement.changePassword(
                jsonObject.get("old_pwd").getAsString(), jsonObject.get("new_pwd").getAsString());
    }

    @RequestMapping(
            value = "changepwd/{username}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public void changePasswordOf(
            @PathVariable("username") String username, @RequestBody /*@Valid*/ JsonObject newPwd)
            throws UnauthorizedUserException, PasswordWeakException, NotFoundException,
            NotAllowedException {
        log.debug("Changing password of user " + username);
        if (isAdmin()) {
            JsonObject jsonObject = gson.fromJson(newPwd, JsonObject.class);
            userManagement.changePasswordOf(username, jsonObject.get("new_pwd").getAsString());
        } else {
            throw new NotAllowedException(
                    "Forbidden to change password of other users. Only admins can do this.");
        }
    }

    public boolean isAdmin() throws NotFoundException {
        User currentUser = userManagement.getCurrentUser();
        log.trace("Check user if admin: " + currentUser.getUsername());
        for (Role role : currentUser.getRoles()) {
            if (role.getRole().ordinal() == Role.RoleEnum.ADMIN.ordinal()) {
                return true;
            }
        }
        return false;
    }
}
