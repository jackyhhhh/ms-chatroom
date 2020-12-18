package com.abc.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "fieldHandler"})
public class User {

    public static final int STATUS_OFFLINE =  0;
    public static final int STATUS_ONLINE = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uid;
    @Column(length = 20, unique = true, nullable = false)
    private String username;
    @Column(length = 20)
    private String password;
    @Column(length = 20)
    private String nickname;
    @Column(insertable = false, nullable = false, columnDefinition = "int(1) default 0")
    private Integer status;
    @CreatedDate
    @Column(name = "create_time", nullable = false, updatable = false, columnDefinition = "datetime")
    private Date createTime;
    @LastModifiedDate
    @Column(name = "update_time", columnDefinition = "datetime")
    private Date updateTime;
    @Column(name = "online_time", columnDefinition = "datetime")
    private Date onlineTime;
    @Column(name = "offline_time", columnDefinition = "datetime")
    private Date offlineTime;

}
