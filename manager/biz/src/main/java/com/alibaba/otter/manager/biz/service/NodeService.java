package com.alibaba.otter.manager.biz.service;

import com.alibaba.otter.manager.biz.entity.NodeDO;
import com.alibaba.otter.shared.common.model.config.node.Node;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * (Node)表服务接口
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:38
 */
public interface NodeService extends IService<NodeDO> {
    void create(Node entityObj);

    void remove(Long identity);

    void modify(Node entityObj);

    Node findById(Long identity);

    List<Node> listByIds(Long... identities);

    List<Node> listAll();

    List<Node> listByCondition(Map condition);

    int getCount();

    int getCount(Map condition);
}
