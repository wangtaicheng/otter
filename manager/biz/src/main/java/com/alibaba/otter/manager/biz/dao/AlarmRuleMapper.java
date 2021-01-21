package com.alibaba.otter.manager.biz.dao;

import com.alibaba.otter.manager.biz.entity.AlarmRuleDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * (AlarmRule)表数据库访问层
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:33
 */
@Mapper
public interface AlarmRuleMapper extends BaseMapper<AlarmRuleDO> {

}
