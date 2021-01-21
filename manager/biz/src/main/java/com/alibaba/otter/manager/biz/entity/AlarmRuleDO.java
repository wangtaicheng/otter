package com.alibaba.otter.manager.biz.entity;


import com.alibaba.otter.shared.common.model.config.alarm.AlarmRuleStatus;
import com.alibaba.otter.shared.common.model.config.alarm.MonitorName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (AlarmRule)表实体类
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:21
 */
@TableName("alarm_rule")
public class AlarmRuleDO implements Serializable {

    private static final long serialVersionUID = 5106165191435199114L;
    @TableId("ID")
    private Long id;

    private MonitorName monitorName;

    private String receiverKey;

    private AlarmRuleStatus status;

    private Long pipelineId;

    private String description;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModified;

    private String matchValue;

    @TableField(value = "parameters", typeHandler = FastjsonTypeHandler.class)
    private AlarmRuleParameter alarmRuleParameter = new AlarmRuleParameter();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MonitorName getMonitorName() {
        return monitorName;
    }

    public void setMonitorName(MonitorName monitorName) {
        this.monitorName = monitorName;
    }

    public String getReceiverKey() {
        return receiverKey;
    }

    public void setReceiverKey(String receiverKey) {
        this.receiverKey = receiverKey;
    }

    public AlarmRuleStatus getStatus() {
        return status;
    }

    public void setStatus(AlarmRuleStatus status) {
        this.status = status;
    }

    public Long getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(Long pipelineId) {
        this.pipelineId = pipelineId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getMatchValue() {
        return matchValue;
    }

    public void setMatchValue(String matchValue) {
        this.matchValue = matchValue;
    }

    public AlarmRuleParameter getAlarmRuleParameter() {
        return alarmRuleParameter;
    }

    public void setAlarmRuleParameter(AlarmRuleParameter alarmRuleParameter) {
        this.alarmRuleParameter = alarmRuleParameter;
    }
}
