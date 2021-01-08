package com.abc.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "fieldHandler"})
public class User implements Serializable {

    public static final int STATUS_OFF = 0;
    public static final int STATUS_ON = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uid;

    @Column(columnDefinition = "varchar(20)", unique = true, nullable = false)
    private String username;

    @Column(columnDefinition = "varchar(20)", nullable = false)
    private String password;

    @Column(columnDefinition = "varchar(20)")
    private String nickname;

    @Column(insertable = false, nullable = false, columnDefinition = "int(1) default 0")
    private Integer status;

    @Column(name = "online_time", columnDefinition = "datetime")
    private Date onlineTime;

    @CreatedDate
    @Column(name = "create_time", columnDefinition = "datetime", nullable = false, updatable = false)
    private Date createTime;

    @LastModifiedDate
    @Column(name = "update_time", columnDefinition = "datetime")
    private Date updateTime;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(Date onlineTime) {
        this.onlineTime = onlineTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
