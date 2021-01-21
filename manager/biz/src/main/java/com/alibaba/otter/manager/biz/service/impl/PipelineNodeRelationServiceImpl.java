package com.alibaba.otter.manager.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.alibaba.otter.manager.biz.dao.PipelineNodeRelationMapper;
import com.alibaba.otter.manager.biz.entity.PipelineNodeRelation;
import com.alibaba.otter.manager.biz.service.PipelineNodeRelationService;
import org.springframework.stereotype.Service;

/**
 * (PipelineNodeRelation)表服务实现类
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:38
 */
@Service("pipelineNodeRelationService")
public class PipelineNodeRelationServiceImpl extends ServiceImpl<PipelineNodeRelationMapper, PipelineNodeRelation> implements PipelineNodeRelationService {

}