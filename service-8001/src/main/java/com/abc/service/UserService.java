package com.abc.service;

import com.abc.bean.User;

import java.util.List;

public interface UserService {
    boolean save(User user);
    boolean update(User user);
    boolean turnOn(String username);
    boolean turnOff(String username);
    boolean deleteByUsername(String username);
    String getOnlineTimeStrByUsername(String username);
    User getByUsername(String username);
    List<User> listAll();
    List<User> listOnline();
    List<User> listOffline();

}
