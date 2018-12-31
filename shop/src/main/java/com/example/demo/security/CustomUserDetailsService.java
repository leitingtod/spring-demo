package com.example.demo.security;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class CustomUserDetailsService implements UserDetailsManager {

  @Autowired
  private UserRepository userRepository;

  private Logger log = LoggerFactory.getLogger(this.getClass());

  @Value("${shop.security.admin.password:admin123}")
  private String adminPwd;

  @Value("${shop.guest.password:guest}")
  private String guestPwd;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findFirstByUsername(username);
    if (user == null) throw new UsernameNotFoundException(username);
    return user;
  }

  @PostConstruct
  public void init() {
    log.debug("Creating initial Users...");

    User admin = userRepository.findFirstByUsername("admin");
    if (!userExists("admin")) {
      User ob_admin = new User();
      ob_admin.setUsername("admin");
      ob_admin.setEnabled(true);
      ob_admin.setPassword(BCrypt.hashpw(adminPwd, BCrypt.gensalt(12)));
      Set<Role> roles = new HashSet<>();
      Role role = new Role();
      role.setRole(Role.RoleEnum.ADMIN);
      roles.add(role);
      ob_admin.setRoles(roles);
      createUser(ob_admin);
    } else {
      log.debug("Admin user exists already.");
    }

    log.debug("Users in the DB: ");
    for (User user : userRepository.findAll()) {
      log.debug("" + user);
    }
  }

  @Override
  public void createUser(UserDetails user) {
    if (userExists(user.getUsername())) {
      log.warn("User " + user.getUsername() + " already exists.");
      return;
    }
    userRepository.save((User) user);
    log.debug("Created user " + user.getUsername());
  }

  @Override
  public void updateUser(UserDetails user) {
    if (userExists(user.getUsername())) {
      userRepository.save((User) user);
      log.debug("Updated user " + user.getUsername());
      return;
    }
    log.warn(
        "User " + user.getUsername() + " does not exist, so no update operation was executed.");
  }

  @Override
  public void deleteUser(String username) {
    User user = userRepository.findFirstByUsername(username);
    if (user == null) {
      log.warn("User " + username + " does not exist and therefore cannot be deleted.");
      return;
    }
    userRepository.delete(userRepository.findFirstByUsername(username));
    log.debug("Successfully deleted user " + username);
  }

  @Override
  public void changePassword(String oldPassword, String newPassword) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String currentUserName = authentication.getName();
    log.debug("Changing password of user: " + currentUserName);
    User user = userRepository.findFirstByUsername(currentUserName);
    if (!BCrypt.checkpw(oldPassword, user.getPassword())) {
      throw new UnauthorizedUserException("Old password is wrong.");
    }
    if (!(authentication instanceof AnonymousAuthenticationToken)) { // TODO is this line needed?
      user.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt(12)));
      userRepository.save(user);
      log.debug("Password of user " + currentUserName + " has been changed successfully.");
    }
  }

  @Override
  public boolean userExists(String username) {
    return (userRepository.findFirstByUsername(username) != null);
  }
}
