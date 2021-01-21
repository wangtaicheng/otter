package com.alibaba.otter.manager.biz.entity;


import com.alibaba.otter.shared.common.model.user.AuthorizeType;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (User)表实体类
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:38
 */

public class UserDO implements Serializable {

    private static final long serialVersionUID = 2242602757447277939L;

    @TableId("ID")
    private Long id;

    @TableField(value = "username", updateStrategy = FieldStrategy.NOT_EMPTY)
    private String userName;

    @TableField(value = "password", updateStrategy = FieldStrategy.NOT_EMPTY)
    private String password;

    @TableField("authorizetype")
    private AuthorizeType authorizeType;

    private String department;

    @TableField("realname")
    private String realName;
    
    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AuthorizeType getAuthorizeType() {
        return authorizeType;
    }

    public void setAuthorizeType(AuthorizeType authorizeType) {
        this.authorizeType = authorizeType;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public LocalDateTime getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }
}
