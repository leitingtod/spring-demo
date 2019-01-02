package com.example.demo.service;

import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.NotAllowedException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.exceptions.PasswordWeakException;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@ConfigurationProperties
public class UserManagement implements com.example.demo.interfaces.UserManagement {

    @Value("${shop.users.password.strength:true}")
    private boolean checkStrength;

    @Value("${shop.users.email.check:true}")
    private boolean checkEmail;

    @Autowired private UserRepository userRepository;

    @Autowired
    @Qualifier("customUserDetailsService")
    private UserDetailsManager customUserDetailsService;

    @Override
    public User add(User user) throws PasswordWeakException, BadRequestException {
        log.debug("Adding new user: " + user);

        if (customUserDetailsService.userExists(user.getUsername())) {
            throw new BadRequestException("Username exists already");
        }

        checkIntegrity(user);

        if (checkStrength) {
            Utils.checkPasswordIntegrity(user.getPassword());
        }

        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12)));
        customUserDetailsService.createUser(user);
        return user;
    }

    @Override
    public void delete(User user) {
        log.debug("Deleting user: " + user);
        customUserDetailsService.deleteUser(user.getUsername());
    }

    @Override
    public User update(User newUser) // TODO the update process seems to be wrong
            throws NotAllowedException, BadRequestException, NotFoundException {

        User userToUpdate = query(newUser.getId());
        if (!userToUpdate.getUsername().equals(newUser.getUsername())) {
            throw new NotAllowedException("Forbidden to change the username");
        }

        newUser.setPassword(userToUpdate.getPassword());

        checkIntegrity(newUser);

        userToUpdate.setEmail(newUser.getEmail());
        userToUpdate.setEnabled(newUser.isEnabled());

        newUser.setPassword(userToUpdate.getPassword());

        for (Role newRole : newUser.getRoles()) {
            boolean found = false;
            for (Role oldRole : userToUpdate.getRoles()) {
                if (oldRole.getRole().equals(newRole.getRole())) {
                    found = true;
                }
            }
            if (!found) {
                Role role = new Role();
                role.setRole(newRole.getRole());
                userToUpdate.getRoles().add(role);
            }
        }
        customUserDetailsService.updateUser(userToUpdate);
        return userToUpdate;
    }

    public Iterable<User> query() {
        log.trace("Listing users");
        return userRepository.findAll();
    }

    @Override
    public User query(String id) throws NotFoundException {
        log.trace("Looking for user with id: " + id);
        User user = userRepository.findFirstById(id);
        if (user == null) {
            throw new NotFoundException("Not found user with id: " + id);
        }
        return user;
    }

    @Override
    public User queryByName(String username) throws NotFoundException {
        log.trace("Get user: " + username);
        User user = userRepository.findFirstByUsername(username);

        if (user == null) {
            throw new NotFoundException("Not found user " + username);
        }
        return user;
    }

    @Override
    public void changePassword(String oldPwd, String newPwd)
            throws UnauthorizedUserException, PasswordWeakException {
        log.debug("Got old password: " + oldPwd);
        if (checkStrength) {
            Utils.checkPasswordIntegrity(newPwd);
        }
        customUserDetailsService.changePassword(oldPwd, newPwd);
    }

    @Override
    public User changePasswordOf(String userName, String newPwd)
            throws PasswordWeakException, NotFoundException {
        User user = userRepository.findFirstByUsername(userName);
        if (user == null) {
            throw new NotFoundException("Not found user " + userName);
        }
        if (checkStrength) {
            Utils.checkPasswordIntegrity(newPwd);
        }
        user.setPassword(BCrypt.hashpw(newPwd, BCrypt.gensalt(12)));
        customUserDetailsService.updateUser(user);
        return user;
    }

    private void checkIntegrity(User user) throws BadRequestException {
        if (user.getUsername() == null || user.getUsername().equals("")) {
            throw new BadRequestException("Username must be provided");
        }
        if (user.getPassword() == null || user.getPassword().equals("")) {
            throw new BadRequestException("Password must be provided");
        }
        if (checkEmail && user.getEmail() != null && !user.getEmail().equals("")) {
            Pattern pattern = Pattern.compile(".+@.+\\.[a-z]+");
            if (!pattern.matcher(user.getEmail()).matches()) {
                throw new BadRequestException("Email is not well formatted");
            }
        }
        Set<String> assignedProjects = new HashSet<>();
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            throw new BadRequestException("At least one role must be provided");
        }
        for (Role role : user.getRoles()) {
            if (role.getRole() == null) {
                throw new BadRequestException("Role must be provided");
            }
        }
    }

    @Override
    public User getCurrentUser() throws NotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        String currentUserName = authentication.getName();
        return this.queryByName(currentUserName);
    }
}
