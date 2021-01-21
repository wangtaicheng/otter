package com.alibaba.otter.manager.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.alibaba.otter.manager.biz.dao.DelayStatMapper;
import com.alibaba.otter.manager.biz.entity.DelayStat;
import com.alibaba.otter.manager.biz.service.DelayStatService;
import org.springframework.stereotype.Service;

/**
 * (DelayStat)表服务实现类
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:38
 */
@Service("delayStatService")
public class DelayStatServiceImpl extends ServiceImpl<DelayStatMapper, DelayStat> implements DelayStatService {

}