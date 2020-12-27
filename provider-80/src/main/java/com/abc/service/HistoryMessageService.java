package com.abc.service;

import com.abc.bean.HistoryMessage;
import org.springframework.data.domain.Page;

import java.util.Date;

public interface HistoryMessageService {
    void save(HistoryMessage historyMessage);
    Page<HistoryMessage> getMsgForUserWithPaging(Date onlineTime, int pageNumber, int pageSize);
    HistoryMessage getLastMsgForUser(Date onlineTime);

}
