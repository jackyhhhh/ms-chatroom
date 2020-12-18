package com.abc.repository;

import com.abc.bean.Msg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MsgRepository extends JpaRepository<Msg, Integer> {

    @Query(value = "select * from (select * from msg where send_time >= ?1 order by send_time desc limit 5) t order by send_time asc", nativeQuery = true)
    List<Msg> getNewMsgForUser(String onlineTime);

}
