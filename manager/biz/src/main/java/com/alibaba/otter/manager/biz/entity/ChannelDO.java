package com.alibaba.otter.manager.biz.entity;


import com.alibaba.otter.shared.common.model.config.channel.ChannelParameter;
import com.alibaba.otter.shared.common.model.config.channel.ChannelStatus;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (Channel)表实体类
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:38
 */
@TableName("channel")
public class ChannelDO implements Serializable {

    private static final long serialVersionUID = -7057971104014360704L;
    @TableId("ID")
    private Long id;
    /**
     * channel命名
     */
    private String name;

    @TableField(exist = false)
    private ChannelStatus status;

    /**
     *
     */
    private String description;

    @TableField(typeHandler = FastjsonTypeHandler.class)
    private ChannelParameter parameters;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModified;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ChannelStatus getStatus() {
        return status;
    }

    public void setStatus(ChannelStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ChannelParameter getParameters() {
        return parameters;
    }

    public void setParameters(ChannelParameter parameters) {
        this.parameters = parameters;
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
