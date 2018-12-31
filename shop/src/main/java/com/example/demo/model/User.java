package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@JsonIgnoreProperties(
        value = {
                "accountNonExpired",
                "accountNonLocked",
                "credentialsNonExpired"
        }) // otherwise there are problems with the Json SchemaValidator
public class User extends BaseUser implements UserDetails {
    @Column(unique = true)
    private String username;

    private String password;

    private boolean enabled;

    private String email;

    @Override
    public String toString() {
        return "User{"
                + "username='"
                + username
                + '\''
                + ", password='"
                + password
                + '\''
                + ", enabled="
                + enabled
                + ", email='"
                + email
                + '\''
                + "} "
                + super.toString();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}

