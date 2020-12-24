package com.abc.controller;

import com.abc.bean.MyResponse;
import com.abc.bean.Password;
import com.abc.bean.User;
import com.abc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    // 校验用户名是否已存在
    @GetMapping("/checkUsername")
    public MyResponse loginHandler(@RequestParam("username")String username){
        User user = service.getByUsername(username);
        if(user == null){
            return new MyResponse("OK");
        }
        return new MyResponse("用户名已经存在");
    }

    // 注册接口
    @PostMapping("/reg")
    public MyResponse saveHandler(@RequestBody User user){
        Integer uid = user.getUid();
        String username = user.getUsername();
        String password = user.getPassword();
        if(uid != null){return new MyResponse("无效参数(uid="+uid+")!"); }
        if(username == null || password==null || "".equals(username) || "".equals(password)){return new MyResponse("用户名或密码不能为空!");}
        if(service.getByUsername(username) != null){return new MyResponse("用户名已经存在!");}
        service.save(user);
        return new MyResponse("OK");
    }

    // 登录接口
    @PostMapping("/login")
    public MyResponse loginHandler(@RequestBody User user){
        String username = user.getUsername();
        String password = user.getPassword();
        if(username == null || password == null){return new MyResponse("用户名或密码不能为空!");}
        User userInDB = service.getByUsername(username);
        if(userInDB == null){return new MyResponse("用户不存在!");}
        if(! password.equalsIgnoreCase(userInDB.getPassword())){ return new MyResponse("密码不正确!"); }
        return new MyResponse(userInDB);
    }

    // 修改信息接口
    @PostMapping("/modify")
    public MyResponse updateHandler(@RequestBody User user){
        Integer uid = user.getUid();
        String username = user.getUsername();
        String password = user.getPassword();
        String nickname = user.getNickname();
        Integer status = user.getStatus();
        if(uid == null){return new MyResponse("缺失参数uid");}
        User userInDB = service.getById(uid);
        if(! userInDB.getPassword().equals(password)){return new MyResponse("密码不正确!");}
        if(username == null){
            user.setUsername(userInDB.getUsername());
        }
        if(status == null){
            user.setStatus(userInDB.getStatus());
        }
        if(nickname == null){
            user.setNickname(userInDB.getNickname());
        }
        service.update(user);
        return new MyResponse("OK");
    }

    @PostMapping("/modifyPassword")
    public MyResponse modifyPasswordHandler(@RequestBody Password pwd){
        Integer uid = pwd.getUid();
        String oldPwd = pwd.getOldPwd();
        String newPwd = pwd.getNewPwd();
        if(uid == null){return new MyResponse("缺失参数uid");}
        if(oldPwd == null || newPwd == null || "".equals(oldPwd) || "".equals(newPwd)){return new MyResponse("新密码或旧密码不能为空！");}
        User userInDB = service.getById(uid);
        if(userInDB == null){return new MyResponse("用户(uid="+uid+")不存在！");}
        if(! userInDB.getPassword().equals(oldPwd)){return new MyResponse("旧密码不正确！");}
        userInDB.setPassword(newPwd);
        service.save(userInDB);
        return new MyResponse("OK");
    }

    @GetMapping("/turnOn")
    public MyResponse turnOnHandler(@RequestParam("username") String username){
            User user = service.getByUsername(username);
            if(user == null){return new MyResponse("用户不存在!");}
            service.turnOn(username);
            return new MyResponse("OK");
    }

    @GetMapping("/turnOff")
    public MyResponse turnOffHandler(@RequestParam("username") String username){
        User user = service.getByUsername(username);
        if(user == null){return new MyResponse("用户不存在!");}
        service.turnOff(username);
        return new MyResponse("OK");
    }

    @PostMapping("/removeUser")
    public MyResponse deleteByUsernameHandler(@RequestBody User user){
        String username = user.getUsername();
        String password = user.getPassword();
        if(username == null || password == null){return new MyResponse("用户名或密码不能为空!");}
        User userInDB = service.getByUsername(username);
        if(userInDB == null){return new MyResponse("用户不存在!");}
        if(! password.equalsIgnoreCase(userInDB.getPassword())){ return new MyResponse("密码不正确!"); }
        service.deleteByUsername(username);
        return new MyResponse("OK");
    }

    @GetMapping("/all")
    public MyResponse  listAllHandler(){return new MyResponse(service.listAll());}

    @GetMapping("/online")
    public MyResponse  listOnlineHandler(){return new MyResponse(service.listOnline());}

    @GetMapping("/offline")
    public MyResponse  listOfflineHandler(){return new MyResponse(service.listOffline());}

}
