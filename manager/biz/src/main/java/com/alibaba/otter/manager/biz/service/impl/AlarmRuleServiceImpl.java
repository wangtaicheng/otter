package com.alibaba.otter.manager.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.alibaba.otter.manager.biz.dao.AlarmRuleMapper;
import com.alibaba.otter.manager.biz.entity.AlarmRule;
import com.alibaba.otter.manager.biz.service.AlarmRuleService;
import org.springframework.stereotype.Service;

/**
 * (AlarmRule)表服务实现类
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:35
 */
@Service("alarmRuleService")
public class AlarmRuleServiceImpl extends ServiceImpl<AlarmRuleMapper, AlarmRule> implements AlarmRuleService {

}