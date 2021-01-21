package com.alibaba.otter.manager.biz.entity;


import com.alibaba.otter.shared.common.model.config.data.ColumnPairMode;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (DataMediaPair)表实体类
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:38
 */
@TableName("DATA_MEDIA_PAIR")
public class DataMediaPairDO implements Serializable {

    private static final long serialVersionUID = 5097522629029046377L;
    @TableId("ID")
    private Long id;

    private Long pullweight;

    private Long pushweight;

    private String resolver;

    private String filter;

    private Long sourceDataMediaId;

    private Long targetDataMediaId;

    private Long pipelineId;

    private ColumnPairMode columnPairMode;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPullweight() {
        return pullweight;
    }

    public void setPullweight(Long pullweight) {
        this.pullweight = pullweight;
    }

    public Long getPushweight() {
        return pushweight;
    }

    public void setPushweight(Long pushweight) {
        this.pushweight = pushweight;
    }

    public String getResolver() {
        return resolver;
    }

    public void setResolver(String resolver) {
        this.resolver = resolver;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public Long getSourceDataMediaId() {
        return sourceDataMediaId;
    }

    public void setSourceDataMediaId(Long sourceDataMediaId) {
        this.sourceDataMediaId = sourceDataMediaId;
    }

    public Long getTargetDataMediaId() {
        return targetDataMediaId;
    }

    public void setTargetDataMediaId(Long targetDataMediaId) {
        this.targetDataMediaId = targetDataMediaId;
    }

    public Long getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(Long pipelineId) {
        this.pipelineId = pipelineId;
    }

    public ColumnPairMode getColumnPairMode() {
        return columnPairMode;
    }

    public void setColumnPairMode(ColumnPairMode columnPairMode) {
        this.columnPairMode = columnPairMode;
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
