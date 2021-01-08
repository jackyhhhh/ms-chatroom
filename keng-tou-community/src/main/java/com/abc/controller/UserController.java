package com.abc.controller;

import com.abc.entity.User;
import com.abc.entity.form.*;
import com.abc.myAnnotation.LoginFree;
import com.abc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @LoginFree
    @PostMapping("/register")
    public MyResponse registerHandler(@RequestBody RegForm form){return service.register(form);}

    @LoginFree
    @PutMapping("/login")
    public MyResponse loginHandler(@RequestBody LoginForm form, HttpServletResponse response){return service.login(form, response);}

    @LoginFree
    @GetMapping("/checkUsername")
    public MyResponse checkUsernameHandler(@RequestParam("username") String username){
        User user = service.findByUsername(username);
        if(user != null){return MyResponse.fail("user already exists");}
        return MyResponse.success();
    }

    @PutMapping("/logout")
    public MyResponse logoutHandler(@RequestParam("uid") Integer uid, HttpServletRequest request){return service.logout(uid, request);}

    @PutMapping("/stepIn")
    public MyResponse stepInHandler(@RequestParam("uid") Integer uid){return service.stepInChatroom(uid);}

    @PutMapping("/stepOut")
    public MyResponse stepOutHandler(@RequestParam("uid") Integer uid){return service.stepOutChatroom(uid);}

    @PutMapping("/modifyInfo")
    public MyResponse modifyInfoHandler(@RequestBody ModifyInfoForm form){return service.modifyInfo(form);}

    @PutMapping("/modifyPassword")
    public MyResponse modifyPasswordHandler(@RequestBody ModifyPasswordForm form){return service.modifyPassword(form);}
    
    @DeleteMapping("/deleteById")
    public MyResponse deleteByIdHandler(@RequestParam("uid") Integer uid){return service.deleteById(uid);}
    
    @GetMapping("/findByUsername")
    public MyResponse findByUsernameHandler(@RequestParam String username){return MyResponse.success(service.findByUsername(username));}

    @GetMapping("/online")
    public MyResponse listOnlineHandler(){return service.listOnline();}

    @GetMapping("/offline")
    public MyResponse listOfflineHandler(){return service.listOffline();}
}
