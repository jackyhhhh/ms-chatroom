package com.abc.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "fieldHandler"})
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer mid;
    @CreatedDate
    @Column(nullable = false, updatable = false, columnDefinition = "datetime")
    private Date sendTime;
    @Column(length = 20)
    private String username;
    @Column(length = 20)
    private String nickname;
    @Column(columnDefinition = "text", nullable = false)
    private String content;
}
