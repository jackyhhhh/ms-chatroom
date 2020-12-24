package com.abc.repository;

import com.abc.bean.HistoryMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;

public interface HistoryMessageRepository extends PagingAndSortingRepository<HistoryMessage, Integer> {

    Page<HistoryMessage> getMsgBySendTimeGreaterThan(Date onlineTime, Pageable pageable);
}
