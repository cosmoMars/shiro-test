package com.wonders.xlab.shiro.entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;

/**
 * Created by mars on 16/7/7.
 */
@Entity
public class User extends AbstractPersistable<Long> {

    private String userName;

    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
