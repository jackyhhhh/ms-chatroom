package com.abc.service;

import com.abc.entity.User;
import com.abc.entity.form.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserService {
    MyResponse register(RegForm regForm);
    MyResponse login(LoginForm loginForm, HttpServletResponse response);
    MyResponse logout(Integer uid, HttpServletRequest request);
    MyResponse deleteById(Integer uid);
    MyResponse stepInChatroom(Integer uid);
    MyResponse stepOutChatroom(Integer uid);
    MyResponse modifyInfo(ModifyInfoForm infoForm);
    MyResponse modifyPassword(ModifyPasswordForm passwordForm);
    MyResponse listOnline();
    MyResponse listOffline();
    User findByUsername(String name);
}
