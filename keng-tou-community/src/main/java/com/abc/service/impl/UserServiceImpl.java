package com.abc.service.impl;

import com.abc.entity.User;
import com.abc.entity.form.*;
import com.abc.repository.UserRepository;
import com.abc.service.CookieService;
import com.abc.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private CookieService cookieService;

    @Override
    public MyResponse register(RegForm form) {
        String username = form.getUsername();
        String password = form.getPassword();
        String nickname = form.getNickname();
        log.debug("reg:  u/p/n = "+username+"/"+password+"/"+nickname);
        if(username==null || password==null || "".equals(username.trim()) || "".equals(password.trim())){
            return MyResponse.fail("username or password can not be empty!");
        }
        if(userRepository.findByUsername(username) != null){
            return MyResponse.fail("user(username="+username+") already exist!");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        if(nickname==null || "".equals(nickname.trim())){
            user.setNickname("");
        }else{
            user.setNickname(nickname);
        }
        userRepository.save(user);
        return MyResponse.success();
    }

    @Override
    public MyResponse login(LoginForm form, HttpServletResponse response) {
        String username = form.getUsername();
        String password = form.getPassword();
        log.debug("login:  u/p = "+username+"/"+password);
        if(username==null || password==null || "".equals(username.trim()) || "".equals(password.trim())){
            return MyResponse.fail("username or password can not be empty!");
        }
        User user = userRepository.findByUsername(username);
        if(user == null || ! password.equals(user.getPassword())){
            return MyResponse.fail("username or password is incorrect!");
        }
        user.setStatus(User.STATUS_ONLINE);
        user.setOnlineTime(new Date());
        userRepository.save(user);
        // 在响应头设置token, 并将token保存到redis, token有效时间3小时
        String token_name = user.getUid() + "_token_" + UUID.randomUUID().toString();
        String token_value = user.getUid() + "&" + username;
        redisTemplate.boundValueOps(token_name).set(token_value, 1, TimeUnit.MINUTES);
        response.addCookie(new Cookie("token", token_name));
        return MyResponse.success(user);
    }

    @Override
    public MyResponse logout(Integer uid, HttpServletRequest request) {
        log.debug("logout:  uid = " + uid);
        if(! userRepository.existsById(uid)){
            return MyResponse.fail("user not found!");
        }
        User user = new User();
        Optional<User> optional = userRepository.findById(uid);
        if(optional.isPresent()){
            user = optional.get();
        }
        user.setStatus(User.STATUS_OFFLINE);
        user.setOnlineTime(null);
        user.setChatStatus(User.OUTSIDE_ROOM);
        userRepository.save(user);
        String token_name = cookieService.getToken(request);
        if(token_name != null){
            redisTemplate.boundValueOps(token_name).expireAt(new Date());
        }
        return MyResponse.success();
    }

    @Override
    public MyResponse deleteById(Integer uid) {
        log.debug("delete:  uid = " + uid);
        if(! userRepository.existsById(uid)){
            return MyResponse.fail("user not found!");
        }
        userRepository.deleteById(uid);
        return MyResponse.success();
    }

    @Override
    public MyResponse stepInChatroom(Integer uid) {
        log.debug("stepIn:  uid = " + uid);
        if(! userRepository.existsById(uid)){
            return MyResponse.fail("user not found! ");
        }
        User user = new User();
        Optional<User> optional = userRepository.findById(uid);
        if(optional.isPresent()){
            user = optional.get();
        }
        user.setChatStatus(User.INSIDE_ROOM);
        userRepository.save(user);
        return MyResponse.success();
    }

    @Override
    public MyResponse stepOutChatroom(Integer uid) {
        log.debug("stepOut:  uid = " + uid);
        if(! userRepository.existsById(uid)){
            return MyResponse.fail("user not found! ");
        }
        User user = new User();
        Optional<User> optional = userRepository.findById(uid);
        if(optional.isPresent()){
            user = optional.get();
        }
        user.setChatStatus(User.OUTSIDE_ROOM);
        userRepository.save(user);
        return MyResponse.success();
    }

    @Override
    public MyResponse modifyInfo(ModifyInfoForm form) {
        int uid = form.getUid();
        String username = form.getUsername();
        String nickname = form.getNickname();
        log.debug("modifyInfo:  uid/username/nickname = "+uid+"/"+username+"/"+nickname);
        if(! userRepository.existsById(uid)){
            return MyResponse.fail("user not found!");
        }
        if("".equals(username+nickname)){
            return MyResponse.fail("username&nickname can not be both empty!");
        }
        User user = new User();
        Optional<User> optional = userRepository.findById(uid);
        if(optional.isPresent()){
            user = optional.get();
        }
        if(! "".equals(username) && username != null){
            user.setUsername(username);
        }
        if(! "".equals(nickname) && nickname != null){
            user.setNickname(nickname);
        }
        userRepository.save(user);
        return MyResponse.success();
    }

    @Override
    public MyResponse modifyPassword(ModifyPasswordForm passwordForm) {
        Integer uid = passwordForm.getUid();
        String oldPwd = passwordForm.getOldPwd();
        String newPwd = passwordForm.getNewPwd();
        log.debug("modifyPwd:  uid/old/new = "+uid+"/"+oldPwd+"/"+newPwd);
        if(! userRepository.existsById(uid)){
            return MyResponse.fail("user not found!");
        }
        User user = new User();
        Optional<User> optional = userRepository.findById(uid);
        if(optional.isPresent()){
            user = optional.get();
        }
        if(! oldPwd.equals(user.getPassword())){
            return MyResponse.fail("incorrect password!");
        }
        user.setPassword(newPwd);
        userRepository.save(user);
        return MyResponse.success();
    }

    @Override
    public MyResponse listOnline() {
        return MyResponse.success(userRepository.findByStatus(User.STATUS_ONLINE));
    }

    @Override
    public MyResponse listOffline() {
        return MyResponse.success(userRepository.findByStatus(User.STATUS_OFFLINE));
    }

    @Override
    public User findByUsername(String name) {
        return userRepository.findByUsername(name);
    }
}
