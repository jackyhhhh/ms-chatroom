package com.abc.controller;

import com.abc.bean.HistoryMessage;
import com.abc.bean.MyResponse;
import com.abc.bean.User;
import com.abc.service.HistoryMessageService;
import com.abc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/msg")
public class HistoryMessageController {

    @Autowired
    private HistoryMessageService msgService;
    @Autowired
    private UserService userService;

    @PostMapping("/saveMsg")
    public MyResponse saveHandler(@RequestBody HistoryMessage historyMessage){
        Integer id = historyMessage.getMid();
        String username = historyMessage.getUsername();
        String content = historyMessage.getContent();
        if(id != null){ return new MyResponse("无效参数(mid="+id+")"); }
        if(username == null){return new MyResponse("用户名不能为空!");}
        if(content == null){return new MyResponse("不能发送空白消息!"); }
        msgService.save(historyMessage);
        return new MyResponse("OK");
    }

    @GetMapping("/describeMsgData")
    private MyResponse getMsgForUserWithPagingHandler(@RequestParam("username") String username,
                                                                @RequestParam("pageNumber") int pageNumber,
                                                                @RequestParam("pageSize")int pageSize){

        User user = userService.getByUsername(username);
        if(user == null){return new MyResponse("用户("+username+")不存在");}
        Date onlineTime = user.getOnlineTime();
        if(onlineTime == null){return new MyResponse("OK");}
        return new MyResponse(msgService.getMsgForUserWithPaging(onlineTime, pageNumber, pageSize));
    }

}
