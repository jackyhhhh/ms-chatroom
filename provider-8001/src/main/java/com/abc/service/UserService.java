package com.abc.service;

import com.abc.bean.ListUserForm;
import com.abc.bean.User;

import java.util.List;

public interface UserService {
    void save(User user);
    void update(User user);
    boolean turnOn(String username);
    boolean turnOff(String username);
    boolean deleteByUsername(String username);
    User getByUsername(String username);
    User getById(int id);
    List<ListUserForm> listAll();
    List<ListUserForm> listOnline();
    List<ListUserForm> listOffline();
}
