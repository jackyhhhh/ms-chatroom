package com.abc.service.impl;

import com.abc.bean.Msg;
import com.abc.repository.MsgRepository;
import com.abc.service.MsgService;
import com.abc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MsgServiceImpl implements MsgService {

    @Autowired
    private MsgRepository msgRepo;
    @Autowired
    private UserService userService;

    @Override
    public boolean saveMsg(Msg msg) {
        if(msg.getMid() == null){
            msgRepo.save(msg);
            return true;
        }
        return false;
    }

    @Override
    public List<Msg> getNewMsgForUser(String username) {
        String onlineTime = userService.getOnlineTimeStrByUsername(username);
        if(onlineTime != null){
            return msgRepo.getNewMsgForUser(onlineTime);
        }
        return new ArrayList<>();
    }
}
