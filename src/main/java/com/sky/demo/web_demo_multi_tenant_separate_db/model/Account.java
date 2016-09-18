package com.sky.demo.web_demo_multi_tenant_separate_db.model;

import com.google.common.base.Objects;

import java.io.Serializable;

/**
 * Created by rg on 8/2/15.
 */
public class Account implements Serializable {

    private static final long serialVersionUID = -1983318223149173865L;
    private int id;
    private String userName;
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("userName", userName)
                .add("password", password)
                .toString();
    }
}
