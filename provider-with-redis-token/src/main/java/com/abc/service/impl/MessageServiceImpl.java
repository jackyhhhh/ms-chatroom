package com.abc.service.impl;

import com.abc.entity.Message;
import com.abc.entity.form.MyResponse;
import com.abc.repository.MessageRepository;
import com.abc.service.MessageService;
import com.abc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository repository;
    @Autowired
    private UserService userService;

    @Override
    public MyResponse saveMsg(Message message) {
        repository.save(message);
        return MyResponse.success();
    }

    @Override
    public MyResponse getMsgByUser(String username, int pageNumber, int pageSize) {
        Date onlineTime = userService.findByUsername(username).getOnlineTime();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("sendTime").descending());
        return MyResponse.success(repository.findBySendTimeGreaterThan(onlineTime, pageable));
    }

    @Override
    public MyResponse getLastMsgForUser(String username) {
        Date onlineTime = userService.findByUsername(username).getOnlineTime();
        Pageable pageable = PageRequest.of(0, 1, Sort.by("sendTime").descending());
        Page<Message> pages = repository.findBySendTimeGreaterThan(onlineTime, pageable);
        if(pages.getContent().size() > 0){
            return MyResponse.success(pages.getContent().get(0));
        }
        return MyResponse.success(null);
    }
}
