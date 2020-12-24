package com.abc.service.impl;

import com.abc.bean.HistoryMessage;
import com.abc.repository.HistoryMessageRepository;
import com.abc.service.HistoryMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class HistoryMessageServiceImpl implements HistoryMessageService {

    @Autowired
    private HistoryMessageRepository repository;

    @Override
    public void save(HistoryMessage historyMessage) {
        repository.save(historyMessage);
    }


    @Override
    public Page<HistoryMessage> getMsgForUserWithPaging(Date onlineTime, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("sendTime").descending());
        return repository.getMsgBySendTimeGreaterThan(onlineTime, pageable);
    }
}
