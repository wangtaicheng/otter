package com.alibaba.otter.manager.biz.entity;


import com.alibaba.otter.canal.instance.manager.model.CanalParameter;
import com.alibaba.otter.canal.instance.manager.model.CanalStatus;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (Canal)表实体类
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:38
 */
@TableName("canal")
public class CanalDO implements Serializable {

    private static final long serialVersionUID = -8767174311702508027L;
    @TableId("ID")
    private Long id;

    private String name;

    private String description;
    @TableField(exist = false)
    private CanalStatus status;
    @TableField(typeHandler = FastjsonTypeHandler.class)
    private CanalParameter parameters;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CanalStatus getStatus() {
        return status;
    }

    public void setStatus(CanalStatus status) {
        this.status = status;
    }

    public CanalParameter getParameters() {
        return parameters;
    }

    public void setParameters(CanalParameter parameters) {
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
