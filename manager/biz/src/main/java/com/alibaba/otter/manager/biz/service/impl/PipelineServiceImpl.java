package com.alibaba.otter.manager.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.alibaba.otter.manager.biz.dao.PipelineMapper;
import com.alibaba.otter.manager.biz.entity.Pipeline;
import com.alibaba.otter.manager.biz.service.PipelineService;
import org.springframework.stereotype.Service;

/**
 * (Pipeline)表服务实现类
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:38
 */
@Service("pipelineService")
public class PipelineServiceImpl extends ServiceImpl<PipelineMapper, Pipeline> implements PipelineService {

}