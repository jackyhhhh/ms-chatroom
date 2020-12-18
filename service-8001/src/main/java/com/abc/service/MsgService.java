package com.abc.service;

import com.abc.bean.Msg;

import java.util.List;

public interface MsgService {
    boolean saveMsg(Msg msg);
    List<Msg> getNewMsgForUser(String username);
}
