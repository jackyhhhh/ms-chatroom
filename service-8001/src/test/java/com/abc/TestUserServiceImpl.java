package com.abc;

import com.abc.bean.User;
import com.abc.service.UserService;
import com.abc.service.impl.UserServiceImpl;

public class TestUserServiceImpl{

    public static void main(String[] args) {
        UserService impl = new UserServiceImpl();
        User user = new User();
        user.setUsername("jack");
        user.setPassword("123");
        user.setNickname("jacky");
        System.out.println("save: "+impl.save(user));
    }


}
