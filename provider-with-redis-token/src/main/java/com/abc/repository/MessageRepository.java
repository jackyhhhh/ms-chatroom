package com.abc.repository;

import com.abc.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;

public interface MessageRepository extends PagingAndSortingRepository<Message, Integer> {
    Page<Message> findBySendTimeGreaterThan(Date onlineTime, Pageable pageable);
}
