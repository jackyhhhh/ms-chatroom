package com.abc.controller;

import com.abc.bean.ModifyPasswordForm;
import com.abc.bean.MyResponse;
import com.abc.bean.User;
import com.abc.myAnnotation.LoginFree;
import com.abc.service.JwtService;
import com.abc.service.UserService;
import com.abc.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;

    @GetMapping("/checkToken")
    public void checkTokenHandler(){}

    @LoginFree
    // 登录接口
    @PostMapping("/login")
    public MyResponse loginHandler(@RequestBody User user, HttpServletResponse response){
        String username = user.getUsername();
        String password = user.getPassword();
        if(username == null || password == null){return MyResponse.fail("用户名或密码不能为空!");}
        User userInDB = userService.getByUsername(username);
        if(userInDB == null){return MyResponse.fail("用户不存在!");}
        if(! password.equalsIgnoreCase(userInDB.getPassword())){ return MyResponse.fail("密码不正确!"); }
        // 校验通过, 则生成token, 并在response中设置Cookie
        String token = jwtService.sign(userInDB.getUid());
        CookieUtil.setTokenInCookie(token, response);
        return MyResponse.success(userInDB);
    }

    @LoginFree
    @GetMapping("/logout")
    public MyResponse logoutHandler(@RequestParam("uid") Integer uid, HttpServletRequest request, HttpServletResponse response){
        // 登出时应返回一个带有失效token的Cookie
        String token = CookieUtil.getToken(request);
        CookieUtil.setTokenInCookie(jwtService.invalidToken(token), response);
        if(userService.logout(uid)){return MyResponse.success();}
        return MyResponse.fail("用户不存在!");
    }
    @LoginFree
    // 校验用户名是否已存在
    @GetMapping("/checkUsername")
    public MyResponse loginHandler(@RequestParam("username")String username){
        User user = userService.getByUsername(username);
        if(user == null){
            return MyResponse.success();
        }
        return MyResponse.fail("用户名已经存在");
    }

    @LoginFree
    // 注册接口
    @PostMapping("/reg")
    public MyResponse saveHandler(@RequestBody User user){
        Integer uid = user.getUid();
        String username = user.getUsername();
        String password = user.getPassword();
        if(uid != null){return MyResponse.fail("无效参数(uid="+uid+")!"); }
        if(username == null || password==null || "".equals(username.trim()) || "".equals(password.trim())){return MyResponse.fail("用户名或密码不能为空!");}
        if(userService.getByUsername(username) != null){return MyResponse.fail("用户名已经存在!");}
        userService.save(user);
        return MyResponse.success();
    }

    // 修改信息接口
    @PostMapping("/modify")
    public MyResponse updateHandler(@RequestBody User user){
        Integer uid = user.getUid();
        String username = user.getUsername();
        String password = user.getPassword();
        String nickname = user.getNickname();
        Integer status = user.getStatus();
        if(uid == null){return MyResponse.fail("缺失参数uid");}
        User userInDB = userService.getById(uid);
        if(! userInDB.getPassword().equals(password)){return MyResponse.fail("密码不正确!");}
        if(username == null || "".equals(username.trim())){
            user.setUsername(userInDB.getUsername());
        }
        if(status == null){
            user.setStatus(userInDB.getStatus());
        }
        if(nickname == null || "".equals(nickname.trim())){
            user.setNickname(userInDB.getNickname());
        }
        userService.update(user);
        return MyResponse.success();
    }

    @PostMapping("/modifyPassword")
    public MyResponse modifyPasswordHandler(@RequestBody ModifyPasswordForm pwd){
        Integer uid = pwd.getUid();
        String oldPwd = pwd.getOldPwd();
        String newPwd = pwd.getNewPwd();
        if(uid == null){return MyResponse.fail("缺失参数uid");}
        if(oldPwd == null || newPwd == null || "".equals(oldPwd) || "".equals(newPwd)){return MyResponse.fail("新密码或旧密码不能为空！");}
        User userInDB = userService.getById(uid);
        if(userInDB == null){return MyResponse.fail("用户(uid="+uid+")不存在！");}
        if(! userInDB.getPassword().equals(oldPwd)){return MyResponse.fail("旧密码不正确！");}
        userInDB.setPassword(newPwd);
        userService.save(userInDB);
        return MyResponse.success();
    }

    @GetMapping("/turnOn")
    public MyResponse turnOnHandler(@RequestParam("username") String username){
            User user = userService.getByUsername(username);
            if(user == null){return MyResponse.fail("用户不存在!");}
            userService.turnOn(username);
            return MyResponse.success();
    }

    @GetMapping("/turnOff")
    public MyResponse turnOffHandler(@RequestParam("username") String username){
        User user = userService.getByUsername(username);
        if(user == null){return MyResponse.fail("用户不存在!");}
        userService.turnOff(username);
        return MyResponse.success();
    }

    @PostMapping("/removeUser")
    public MyResponse deleteByUsernameHandler(@RequestBody User user){
        String username = user.getUsername();
        String password = user.getPassword();
        if(username == null || password == null){return MyResponse.fail("用户名或密码不能为空!");}
        User userInDB = userService.getByUsername(username);
        if(userInDB == null){return MyResponse.fail("用户不存在!");}
        if(! password.equalsIgnoreCase(userInDB.getPassword())){ return MyResponse.fail("密码不正确!"); }
        userService.deleteByUsername(username);
        return MyResponse.success();
    }

    @GetMapping("/all")
    public MyResponse  listAllHandler(){return MyResponse.success(userService.listAll());}

    @GetMapping("/online")
    public MyResponse  listOnlineHandler(){return MyResponse.success(userService.listOnline());}

    @GetMapping("/offline")
    public MyResponse  listOfflineHandler(){return MyResponse.success(userService.listOffline());}

}
