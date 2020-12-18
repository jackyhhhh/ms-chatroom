package com.abc.service.impl;

import com.abc.bean.User;
import com.abc.repository.UserRepository;
import com.abc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public boolean save(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        Integer uid = user.getUid();
        // 校验输入 input
        if(username == null
                || password == null
                || "".equals(username)
                || uid != null
                || "".equals(password)){

            return false;
        }

        repository.save(user);
        return true;
    }

    @Override
    public boolean update(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        Integer uid = user.getUid();
        // 校验输入input
        if(username == null
                || password == null
                || "".equals(username)
                || "".equals(password)
                || uid == null){

            return false;
        }
        // 检验用户名和密码是否正确
        User userInDB = repository.findByUsername(username);
        if(userInDB.getUid() == null
                || !password.equals(userInDB.getPassword())){
            return false;
        }
        // 如果参数中没有传status值, 在线状态保持不变
        if(user.getStatus() == null){
            user.setStatus(userInDB.getStatus());
        }
        repository.save(user);
        return true;
    }

    @Override
    public boolean turnOn(String username) {
        User user = repository.findByUsername(username);
        if(user != null){
            user.setStatus(User.STATUS_ONLINE);
            user.setOnlineTime(new Date());
            user.setOfflineTime(null);
            repository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean turnOff(String username) {
        User user = repository.findByUsername(username);
        if(user != null){
            user.setStatus(User.STATUS_OFFLINE);
            user.setOfflineTime(new Date());
            user.setOnlineTime(null);
            repository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteByUsername(String username) {
        int uid = repository.findByUsername(username).getUid();
        if(repository.existsById(uid)){
            repository.deleteById(uid);
            return true;
        }
        return false;
    }

    @Override
    public String getOnlineTimeStrByUsername(String username) {
        User user = getByUsername(username);
        if(user != null){
            Date onlineTime = getByUsername(username).getOnlineTime();
            if(onlineTime != null){
                SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                return fmt.format(onlineTime);
            }
        }
        return null;
    }

    @Override
    public User getByUsername(String name) {
        return repository.findByUsername(name);
    }

    @Override
    public List<User> listAll() {
        return repository.findAll();
    }

    @Override
    public List<User> listOnline() {
        return repository.findByStatus(User.STATUS_ONLINE);
    }

    @Override
    public List<User> listOffline() {
        return repository.findByStatus(User.STATUS_OFFLINE);
    }

}
