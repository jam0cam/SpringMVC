package com.example.model;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * User: jitse
 * Date: 12/4/13
 * Time: 10:22 AM
 */
public class Player extends BaseObject {

    private String email;
    private String password;

    private String name;
    private String avatarUrl;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
