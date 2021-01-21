package com.alibaba.otter.manager.biz.service.impl;

import com.alibaba.otter.manager.biz.common.exceptions.ManagerException;
import com.alibaba.otter.manager.biz.dao.AlarmRuleMapper;
import com.alibaba.otter.manager.biz.entity.AlarmRuleDO;
import com.alibaba.otter.manager.biz.entity.AlarmRuleParameter;
import com.alibaba.otter.manager.biz.service.AlarmRuleService;
import com.alibaba.otter.shared.common.model.config.alarm.AlarmRule;
import com.alibaba.otter.shared.common.model.config.alarm.AlarmRuleStatus;
import com.alibaba.otter.shared.common.utils.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * (AlarmRule)表服务实现类
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:35
 */
@Service("alarmRuleService")
public class AlarmRuleServiceImpl extends ServiceImpl<AlarmRuleMapper, AlarmRuleDO> implements AlarmRuleService {


    private static final Logger logger = LoggerFactory.getLogger(AlarmRuleServiceImpl.class);

    @Override
    public void create(AlarmRule alarmRule) {
        Assert.assertNotNull(alarmRule);
        save(modelToDo(alarmRule));
    }

    @Override
    public void modify(AlarmRule alarmRule) {
        Assert.assertNotNull(alarmRule.getId());
        AlarmRuleDO alarmRuleDo = modelToDo(alarmRule);
        updateById(alarmRuleDo);
    }

    @Override
    public void remove(Long alarmRuleId) {
        Assert.assertNotNull(alarmRuleId);
        removeById(alarmRuleId);
    }

    @Override
    public void enableMonitor(Long alarmRuleId) {
        switchAlarmRuleStatus(alarmRuleId, AlarmRuleStatus.ENABLE, null);
    }

    @Override
    public void disableMonitor(Long alarmRuleId) {
        switchAlarmRuleStatus(alarmRuleId, AlarmRuleStatus.DISABLE, LocalDateTime.now());
    }

    @Override
    public void disableMonitor(Long alarmRuleId, LocalDateTime pauseTime) {
        switchAlarmRuleStatus(alarmRuleId, AlarmRuleStatus.DISABLE, LocalDateTime.now());
    }

    @Override
    public List<AlarmRule> getAllAlarmRules(AlarmRuleStatus status) {
        Assert.assertNotNull(status);
        LambdaQueryWrapper<AlarmRuleDO> eq = Wrappers.<AlarmRuleDO>lambdaQuery().eq(AlarmRuleDO::getStatus, status);
        return doToModel(list(eq));
    }

    @Override
    public AlarmRule getAlarmRuleById(Long alarmRuleId) {
        return doToModel(getById(alarmRuleId));
    }

    @Override
    public Map<Long, List<AlarmRule>> getAlarmRules(AlarmRuleStatus status) {
        return getAllAlarmRules(status).stream().collect(Collectors.groupingBy(AlarmRule::getPipelineId));
    }

    @Override
    public List<AlarmRule> getAlarmRules(Long pipelineId) {
        Assert.assertNotNull(pipelineId);
        LambdaQueryWrapper<AlarmRuleDO> eq = Wrappers.<AlarmRuleDO>lambdaQuery()
                .eq(AlarmRuleDO::getPipelineId, pipelineId);
        return doToModel(list(eq));
    }

    @Override
    public List<AlarmRule> getAlarmRules(Long pipelineId, AlarmRuleStatus status) {
        Assert.assertNotNull(pipelineId);
        Assert.assertNotNull(status);
        LambdaQueryWrapper<AlarmRuleDO> eq = Wrappers.<AlarmRuleDO>lambdaQuery()
                .eq(AlarmRuleDO::getPipelineId, pipelineId).eq(AlarmRuleDO::getStatus, status);
        return doToModel(list(eq));
    }

    @Override
    public List<AlarmRule> listAllAlarmRules(Map condition) {
        LambdaQueryWrapper<AlarmRuleDO> eq = Wrappers.<AlarmRuleDO>lambdaQuery()
                .orderByAsc(AlarmRuleDO::getPipelineId);
        return doToModel(list(eq));
    }

    @Override
    public int getCount() {
        return count();
    }

    private void switchAlarmRuleStatus(Long alarmRuleId, AlarmRuleStatus alarmRuleStatus, LocalDateTime pauseTime) {
        AlarmRuleDO alarmRuleDo = getById(alarmRuleId);

        if (null == alarmRuleDo) {
            String exceptionCause = "query alarmRule:" + alarmRuleId + " return null.";
            logger.error("ERROR ## {}", exceptionCause);
            throw new ManagerException(exceptionCause);
        }

        alarmRuleDo.setStatus(alarmRuleStatus);
        if (alarmRuleDo.getAlarmRuleParameter() != null) {
            alarmRuleDo.getAlarmRuleParameter().setPauseTime(pauseTime);
        } else if (pauseTime != null) {
            alarmRuleDo.setAlarmRuleParameter(new AlarmRuleParameter());
            alarmRuleDo.getAlarmRuleParameter().setPauseTime(pauseTime);
        }
        updateById(alarmRuleDo);
    }

    private AlarmRule doToModel(AlarmRuleDO alarmRuleDo) {
        AlarmRule alarmRule = new AlarmRule();
        alarmRule.setId(alarmRuleDo.getId());
        alarmRule.setMatchValue(alarmRuleDo.getMatchValue());
        alarmRule.setMonitorName(alarmRuleDo.getMonitorName());
        alarmRule.setReceiverKey(alarmRuleDo.getReceiverKey());

        // 如果数据库里面的数据为空，则返回默认值
        AlarmRuleParameter alarmRuleParameter = alarmRuleDo.getAlarmRuleParameter();
        alarmRule.setIntervalTime(alarmRuleParameter == null ? 1800L : alarmRuleParameter.getIntervalTime());
        LocalDateTime pauseTime = alarmRuleParameter == null ? null : alarmRuleParameter.getPauseTime();
        alarmRule.setPauseTime(pauseTime);
        alarmRule.setAutoRecovery(alarmRuleParameter != null && alarmRuleParameter.getAutoRecovery());
        alarmRule.setRecoveryThresold(alarmRuleParameter == null ? 3 : alarmRuleParameter.getRecoveryThresold());
        alarmRule.setPipelineId(alarmRuleDo.getPipelineId());
        alarmRule.setStatus(alarmRuleDo.getStatus());
        alarmRule.setDescription(alarmRuleDo.getDescription());
        alarmRule.setGmtCreate(alarmRuleDo.getGmtCreate());
        alarmRule.setGmtModified(alarmRuleDo.getGmtModified());
        return alarmRule;
    }

    private List<AlarmRule> doToModel(List<AlarmRuleDO> alarmRuleDos) {
        return alarmRuleDos.stream().map(this::doToModel).collect(Collectors.toList());

    }

    private AlarmRuleDO modelToDo(AlarmRule alarmRule) {
        AlarmRuleDO alarmRuleDo = new AlarmRuleDO();
        alarmRuleDo.setId(alarmRule.getId());
        alarmRuleDo.setMatchValue(alarmRule.getMatchValue());
        alarmRuleDo.setMonitorName(alarmRule.getMonitorName());
        alarmRuleDo.setReceiverKey(alarmRule.getReceiverKey());
        alarmRuleDo.setPipelineId(alarmRule.getPipelineId());
        alarmRuleDo.setStatus(alarmRule.getStatus());
        alarmRuleDo.setDescription(alarmRule.getDescription());
        alarmRuleDo.setGmtCreate(alarmRule.getGmtCreate());
        alarmRuleDo.setGmtModified(alarmRule.getGmtModified());
        AlarmRuleParameter alarmRuleParameter = new AlarmRuleParameter();
        alarmRuleParameter.setIntervalTime(alarmRule.getIntervalTime());
        alarmRuleParameter.setPauseTime(alarmRule.getPauseTime());
        alarmRuleParameter.setAutoRecovery(alarmRule.getAutoRecovery());
        alarmRuleParameter.setRecoveryThresold(alarmRule.getRecoveryThresold());
        alarmRuleDo.setAlarmRuleParameter(alarmRuleParameter);

        return alarmRuleDo;
    }

}
