package com.alibaba.otter.manager.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.alibaba.otter.manager.biz.dao.NodeMapper;
import com.alibaba.otter.manager.biz.entity.Node;
import com.alibaba.otter.manager.biz.service.NodeService;
import org.springframework.stereotype.Service;

/**
 * (Node)表服务实现类
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:38
 */
@Service("nodeService")
public class NodeServiceImpl extends ServiceImpl<NodeMapper, Node> implements NodeService {

}