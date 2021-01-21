package com.alibaba.otter.manager.biz.service;

import com.alibaba.otter.manager.biz.entity.ChannelDO;
import com.alibaba.otter.shared.common.model.config.channel.Channel;
import com.alibaba.otter.shared.common.model.config.channel.ChannelStatus;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * (Channel)表服务接口
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:38
 */
public interface ChannelService extends IService<ChannelDO> {
    Channel findByPipelineId(Long pipelineId);

    Channel findByIdWithoutColumn(Long pipelineId);

    List<Channel> listByPipelineIds(Long... pipelineIds);

    public Channel findById(Long channelId);

    List<Channel> listByNodeId(Long nodeId);

    List<Channel> listOnlyChannels(Long... identities);

    List<Long> listAllChannelId();

    List<Channel> listByNodeId(Long nodeId, ChannelStatus... status);

    List<Channel> listByConditionWithoutColumn(Map condition);

    void stopChannel(Long channelId);

    void notifyChannel(Long channelId);

    void startChannel(Long channelId);

    int getCount();

    int getCount(Map condition);
}
