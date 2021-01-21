package com.alibaba.otter.manager.biz.service.impl;

import com.alibaba.otter.manager.biz.dao.ThroughputStatMapper;
import com.alibaba.otter.manager.biz.entity.ThroughputStatDO;
import com.alibaba.otter.manager.biz.service.ThroughputStatService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * (ThroughputStat)表服务实现类
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:38
 */
@Service("throughputStatService")
public class ThroughputStatServiceImpl extends ServiceImpl<ThroughputStatMapper, ThroughputStatDO> implements ThroughputStatService {

}
