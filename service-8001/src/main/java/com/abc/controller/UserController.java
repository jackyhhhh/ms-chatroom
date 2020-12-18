package com.abc.controller;

import com.abc.bean.User;
import com.abc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping
    public boolean saveHandler(@RequestBody User user){return service.save(user);}

    @PutMapping
    public boolean updateHandler(@RequestBody User user){return service.update(user);}

    @PutMapping("/turnOn/{username}")
    public boolean turnOnHandler(@PathVariable("username") String username){return service.turnOn(username);}

    @PutMapping("/turnOff/{username}")
    public boolean turnOffHandler(@PathVariable("username") String username){return service.turnOff(username);}

    @DeleteMapping("/{username}")
    public boolean deleteByUsernameHandler(@PathVariable("username") String username){return service.deleteByUsername(username);}

    @GetMapping("/{username}")
    public User getByUsernameHandler(@PathVariable("username") String username){return service.getByUsername(username);}

    @GetMapping("/listAll")
    public List<User> listAllHandler(){return service.listAll();}

    @GetMapping("/listOnline")
    public List<User> listOnlineHandler(){return service.listOnline();}

    @GetMapping("/listOffline")
    public List<User> listOfflineHandler(){return service.listOffline();}

}
