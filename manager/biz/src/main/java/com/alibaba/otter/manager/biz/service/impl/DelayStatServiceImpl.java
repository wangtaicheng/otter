package com.alibaba.otter.manager.biz.service.impl;

import com.alibaba.otter.manager.biz.dao.DelayStatMapper;
import com.alibaba.otter.manager.biz.entity.DelayStatDO;
import com.alibaba.otter.manager.biz.service.DelayStatService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * (DelayStat)表服务实现类
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:38
 */
@Service("delayStatService")
public class DelayStatServiceImpl extends ServiceImpl<DelayStatMapper, DelayStatDO> implements DelayStatService {

}
