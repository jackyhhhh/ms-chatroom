package com.abc.controller;

import com.abc.entity.Message;
import com.abc.entity.form.MyResponse;
import com.abc.myAnnotation.LoginFree;
import com.abc.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/msg")
public class MessageController {

    @Autowired
    private MessageService service;

    @LoginFree
    @PostMapping("/save")
    public MyResponse saveHandler(@RequestBody Message message){return service.saveMsg(message);}

    @LoginFree
    @GetMapping("/describeMsgData")
    public MyResponse describeMsgDataHandler(@RequestParam("username") String username, int pageNumber, int pageSize){return service.getMsgByUser(username, pageNumber, pageSize);}

    @LoginFree
    @GetMapping("/describeLastMsg")
    public MyResponse describeMsgDataHandler(@RequestParam("username") String username){return service.getLastMsgForUser(username);}
}
