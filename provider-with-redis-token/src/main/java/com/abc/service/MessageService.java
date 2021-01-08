package com.abc.service;

import com.abc.entity.Message;
import com.abc.entity.form.MyResponse;

public interface MessageService {
    MyResponse saveMsg(Message message);
    MyResponse getMsgByUser(String username, int pageNumber, int pageSize);
    MyResponse getLastMsgForUser(String username);
}
