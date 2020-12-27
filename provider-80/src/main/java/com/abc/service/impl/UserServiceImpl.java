package com.abc.service.impl;

import com.abc.bean.User;
import com.abc.repository.UserRepository;
import com.abc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public void save(User user) {
        repository.save(user);
    }

    @Override
    public void update(User user) {
        repository.save(user);
    }

    @Override
    public boolean turnOn(String username) {
        User user = repository.findByUsername(username);
        if(user == null) {
            return false;
        }
        user.setStatus(User.STATUS_ON);
        user.setOnlineTime(new Date());
        repository.save(user);
        return true;
    }

    @Override
    public boolean turnOff(String username) {
        User user = repository.findByUsername(username);
        if(user == null ) {
            return false;
        }
        user.setStatus(User.STATUS_OFF);
        user.setOnlineTime(null);
        repository.save(user);
        return true;
    }

    @Override
    public boolean deleteByUsername(String username) {
        User user = repository.findByUsername(username);
        if(user != null) {
            repository.delete(user);
            return true;
        }
        return false;
    }

    @Override
    public User getByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public User getById(int id) {
        if(repository.existsById(id)){
            return repository.getOne(id);
        }
        return null;
    }

    @Override
    public List<String> listAll() {
        List<User> users = repository.findAll();
        List<String> usernames = new ArrayList<>();
        for(User user: users){
            usernames.add(user.getUsername());
        }
        return usernames;
    }

    @Override
    public List<String> listOnline() {
        List<User> users = repository.findByStatus(User.STATUS_ON);
        List<String> usernames = new ArrayList<>();
        for(User user: users){
            usernames.add(user.getUsername());
        }
        return usernames;
    }

    @Override
    public List<String> listOffline() {
        List<User> users = repository.findByStatus(User.STATUS_OFF);
        List<String> usernames = new ArrayList<>();
        for (User user : users) {
            usernames.add(user.getUsername());
        }
        return usernames;
    }
}
