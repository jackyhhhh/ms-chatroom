package com.abc.controller;

import com.abc.bean.Msg;
import com.abc.service.MsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/msg")
public class MsgController {

    @Autowired
    private MsgService service;

    @PostMapping
    public boolean saveHandler(@RequestBody Msg msg){return service.saveMsg(msg);}

    @GetMapping("/getNewMsgForUser/{username}")
    public List<Msg> getNewMsgForUserHandler(@PathVariable("username") String username){return service.getNewMsgForUser(username);}
}
