package com.alibaba.otter.manager.biz.service.impl;

import com.alibaba.otter.manager.biz.common.exceptions.ManagerException;
import com.alibaba.otter.manager.biz.dao.NodeMapper;
import com.alibaba.otter.manager.biz.entity.NodeDO;
import com.alibaba.otter.manager.biz.service.AutoKeeperClusterService;
import com.alibaba.otter.manager.biz.service.NodeService;
import com.alibaba.otter.shared.arbitrate.ArbitrateManageService;
import com.alibaba.otter.shared.common.model.autokeeper.AutoKeeperCluster;
import com.alibaba.otter.shared.common.model.config.node.Node;
import com.alibaba.otter.shared.common.model.config.node.NodeParameter;
import com.alibaba.otter.shared.common.model.config.node.NodeStatus;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * (Node)表服务实现类
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:38
 */
@Service("nodeService")
public class NodeServiceImpl extends ServiceImpl<NodeMapper, NodeDO> implements NodeService {
    private static final Logger logger = LoggerFactory.getLogger(NodeServiceImpl.class);
    @Autowired
    private ArbitrateManageService arbitrateManageService;
    @Autowired
    private AutoKeeperClusterService autoKeeperClusterService;

    @Override
    public void create(Node entityObj) {
        NodeDO nodeDo = modelToDo(entityObj);
        save(nodeDo);
    }

    @Override
    public void remove(Long identity) {
        removeById(identity);
    }

    @Override
    public void modify(Node entityObj) {
        NodeDO nodeDo = modelToDo(entityObj);
        updateById(nodeDo);
    }

    @Override
    public Node findById(Long identity) {
        return doToModel(getById(identity));
    }

    @Override
    public List<Node> listByIds(Long... identities) {
        return Arrays.stream(identities).map(this::findById).collect(Collectors.toList());
    }

    @Override
    public List<Node> listAll() {
        return doToModel(list());
    }

    @Override
    public List<Node> listByCondition(Map condition) {
        return doToModel(list(buildLambdaQuery(condition)));
    }

    @Override
    public int getCount() {
        return count();
    }

    @Override
    public int getCount(Map condition) {
        return count(buildLambdaQuery(condition));
    }

    private LambdaQueryWrapper<NodeDO> buildLambdaQuery(Map condition) {
        Object searchKey = condition.get("searchKey");
        LambdaQueryWrapper<NodeDO> eq = Wrappers.lambdaQuery();
        if (com.baomidou.mybatisplus.core.toolkit.StringUtils.checkValNotNull(searchKey)) {
            eq.like(NodeDO::getName, searchKey).or().like(NodeDO::getId, searchKey);
        }
        return eq;
    }

    /**
     * 用于Model对象转化为DO对象
     *
     * @param node
     * @return NodeDO
     */
    private NodeDO modelToDo(Node node) {
        NodeDO nodeDo = new NodeDO();
        try {
            nodeDo.setId(node.getId());
            nodeDo.setIp(node.getIp());
            nodeDo.setName(node.getName());
            nodeDo.setPort(node.getPort());
            nodeDo.setDescription(node.getDescription());
            nodeDo.setStatus(node.getStatus());
            nodeDo.setParameters(node.getParameters());
            nodeDo.setGmtCreate(node.getGmtCreate());
            nodeDo.setGmtModified(node.getGmtModified());
        } catch (Exception e) {
            logger.error("ERROR ## change the node Model to Do has an exception");
            throw new ManagerException(e);
        }
        return nodeDo;
    }

    /**
     * 用于DO对象转化为Model对象
     *
     * @param nodeDo
     * @return Node
     */
    private Node doToModel(NodeDO nodeDo) {
        Node node = new Node();
        try {
            node.setId(nodeDo.getId());
            node.setIp(nodeDo.getIp());
            node.setName(nodeDo.getName());
            node.setPort(nodeDo.getPort());
            node.setDescription(nodeDo.getDescription());
            node.setStatus(nodeDo.getStatus());
            // 处理下zk集群
            NodeParameter parameter = nodeDo.getParameters();
            if (parameter.getZkCluster() != null) {
                AutoKeeperCluster zkCluster = autoKeeperClusterService
                        .findAutoKeeperClusterById(parameter.getZkCluster().getId());
                parameter.setZkCluster(zkCluster);
            }

            node.setParameters(parameter);
            node.setGmtCreate(nodeDo.getGmtCreate());
            node.setGmtModified(nodeDo.getGmtModified());
        } catch (Exception e) {
            logger.error("ERROR ## change the node Do to Model has an exception");
            throw new ManagerException(e);
        }

        return node;
    }

    private List<Node> doToModel(List<NodeDO> nodeDos) {
        // 验证zk的node信息
        List<Long> nodeIds = arbitrateManageService.nodeEvent().liveNodes();
        return nodeDos.stream().map(this::doToModel).map(nodeDo -> {
            if (nodeIds.contains(nodeDo.getId())) {
                nodeDo.setStatus(NodeStatus.START);
            } else {
                nodeDo.setStatus(NodeStatus.STOP);
            }
            return nodeDo;
        }).collect(Collectors.toList());
    }

}
