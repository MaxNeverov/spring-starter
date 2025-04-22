package com.mndev.spring.database.entity;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

//переназначение возможных ролей для Security
public enum Role implements GrantedAuthority {
    USER, ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
