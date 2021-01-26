/*
 * Copyright (C) 2010-2101 Alibaba Group Holding Limited.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.otter.shared.arbitrate.impl.setl.lb;

import com.alibaba.otter.shared.arbitrate.impl.config.ArbitrateConfigUtils;
import com.alibaba.otter.shared.arbitrate.impl.setl.ArbitrateLifeCycle;
import com.alibaba.otter.shared.arbitrate.impl.setl.monitor.NodeMonitor;
import com.alibaba.otter.shared.common.model.config.node.Node;
import com.alibaba.otter.shared.common.model.config.pipeline.Pipeline;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 抽象的负载均衡接口
 *
 * @author jianghang 2011-9-20 下午01:31:29
 * @version 4.0.0
 */
public abstract class AbstractLoadBalance extends ArbitrateLifeCycle implements LoadBalance {

    protected NodeMonitor nodeMonitor;

    public AbstractLoadBalance(Long pipelineId) {
        super(pipelineId);
    }

    /**
     * getAliveNodes
     *
     * @return List<Node>
     */
    public abstract List<Node> getAliveNodes();

    @Override
    public void destory() {
        super.destory();
    }

    public void setNodeMonitor(NodeMonitor nodeMonitor) {
        this.nodeMonitor = nodeMonitor;
    }

    public List<Node> getExtractAliveNodes() {
        Pipeline pipeline = ArbitrateConfigUtils.getPipeline(getPipelineId());
        List<Node> extractNodes = pipeline.getExtractNodes();

        List<Long> aliveNodes = nodeMonitor.getAliveNodes();
        return extractNodes.stream().filter(sourceNode -> aliveNodes.contains(sourceNode.getId()))
                           .collect(Collectors.toList());
    }

    public List<Node> getTransformAliveNodes() {
        Pipeline pipeline = ArbitrateConfigUtils.getPipeline(getPipelineId());
        List<Node> transformNodes = pipeline.getLoadNodes();

        List<Long> aliveNodes = nodeMonitor.getAliveNodes();
        return transformNodes.stream().filter(sourceNode -> aliveNodes.contains(sourceNode.getId()))
                             .collect(Collectors.toList());
    }

}
