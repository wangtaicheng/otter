package com.alibaba.otter.manager.biz.service;

import com.alibaba.otter.manager.biz.entity.AlarmRuleDO;
import com.alibaba.otter.shared.common.model.config.alarm.AlarmRule;
import com.alibaba.otter.shared.common.model.config.alarm.AlarmRuleStatus;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * (AlarmRule)表服务接口
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:34
 */
public interface AlarmRuleService extends IService<AlarmRuleDO> {
    void create(AlarmRule alarmRule);

    void modify(AlarmRule alarmRule);

    void remove(Long alarmRuleId);

    public void enableMonitor(Long alarmRuleId);

    public void disableMonitor(Long alarmRuleId);

    public void disableMonitor(Long alarmRuleId, LocalDateTime pauseTime);

    List<AlarmRule> getAllAlarmRules(AlarmRuleStatus status);

    AlarmRule getAlarmRuleById(Long AlarmRuleId);

    /**
     * 获取所有状态为 status 的 {@linkplain AlarmRule}，并且按照pipelineId分区
     *
     * @param status
     * @return
     */
    Map<Long, List<AlarmRule>> getAlarmRules(AlarmRuleStatus status);

    List<AlarmRule> getAlarmRules(Long pipelineId);

    List<AlarmRule> getAlarmRules(Long pipelineId, AlarmRuleStatus status);

    List<AlarmRule> listAllAlarmRules(Map condition);

    public int getCount();
}
