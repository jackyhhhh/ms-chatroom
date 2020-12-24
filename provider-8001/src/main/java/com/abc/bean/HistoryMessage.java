package com.abc.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "fieldHandler"})
public class HistoryMessage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer mid;
    @CreatedDate
    @Column(name = "send_time", columnDefinition = "datetime")
    private Date sendTime;
    @Column(length = 20)
    private String username;
    @Column(length = 20)
    private String nickname;
    @Column(nullable = false, columnDefinition = "text")
    private String content;
}
