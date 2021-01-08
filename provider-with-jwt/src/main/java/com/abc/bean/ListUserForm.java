package com.abc.bean;

import java.io.Serializable;

public class ListUserForm implements Serializable {
    private String username;
    private String nickname;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public ListUserForm(String username, String nickname){
        this.username = username;
        this.nickname = nickname;
    }
}
