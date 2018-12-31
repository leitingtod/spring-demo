package com.example.demo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Role extends BaseEntity implements GrantedAuthority {

    @Enumerated(EnumType.STRING)
    private RoleEnum role;


    @Override
    public String getAuthority() {
        return "ROLE_" + role.name();
    }

    public enum RoleEnum {
        GUEST,
        USER,
        ADMIN,
    }
}
