package com.alibaba.otter.manager.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.alibaba.otter.manager.biz.dao.CanalMapper;
import com.alibaba.otter.manager.biz.entity.Canal;
import com.alibaba.otter.manager.biz.service.CanalService;
import org.springframework.stereotype.Service;

/**
 * (Canal)表服务实现类
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:38
 */
@Service("canalService")
public class CanalServiceImpl extends ServiceImpl<CanalMapper, Canal> implements CanalService {

}