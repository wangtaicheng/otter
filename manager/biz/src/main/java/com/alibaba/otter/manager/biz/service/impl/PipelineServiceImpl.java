package com.alibaba.otter.manager.biz.service.impl;

import com.alibaba.otter.manager.biz.dao.PipelineMapper;
import com.alibaba.otter.manager.biz.entity.PipelineDO;
import com.alibaba.otter.manager.biz.service.PipelineService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * (Pipeline)表服务实现类
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:38
 */
@Service("pipelineService")
public class PipelineServiceImpl extends ServiceImpl<PipelineMapper, PipelineDO> implements PipelineService {

}
