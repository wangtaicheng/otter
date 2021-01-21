package com.alibaba.otter.manager.biz.service.impl;

import com.alibaba.otter.manager.biz.common.exceptions.InvalidConfigureException;
import com.alibaba.otter.manager.biz.common.exceptions.ManagerException;
import com.alibaba.otter.manager.biz.config.parameter.SystemParameterService;
import com.alibaba.otter.manager.biz.config.pipeline.PipelineService;
import com.alibaba.otter.manager.biz.dao.ChannelMapper;
import com.alibaba.otter.manager.biz.entity.ChannelDO;
import com.alibaba.otter.manager.biz.remote.ConfigRemoteService;
import com.alibaba.otter.manager.biz.service.ChannelService;
import com.alibaba.otter.shared.arbitrate.ArbitrateManageService;
import com.alibaba.otter.shared.common.model.config.channel.Channel;
import com.alibaba.otter.shared.common.model.config.channel.ChannelStatus;
import com.alibaba.otter.shared.common.model.config.parameter.SystemParameter;
import com.alibaba.otter.shared.common.model.config.pipeline.Pipeline;
import com.alibaba.otter.shared.common.model.config.pipeline.PipelineParameter;
import com.alibaba.otter.shared.common.utils.Assert;
import com.alibaba.otter.shared.common.utils.JsonUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.*;
import java.util.stream.Collectors;

/**
 * (Channel)表服务实现类
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:38
 */
@Service("channelService")
public class ChannelServiceImpl extends ServiceImpl<ChannelMapper, ChannelDO> implements ChannelService {
    private static final Logger logger = LoggerFactory.getLogger(ChannelServiceImpl.class);
    private SystemParameterService systemParameterService;
    private ArbitrateManageService arbitrateManageService;
    private TransactionTemplate transactionTemplate;
    private ConfigRemoteService configRemoteService;
    private PipelineService pipelineService;

    /**
     * <pre>
     * 通过ChannelId找到对应的Channel对象
     * 并且根据ChannelId找到对应的所有Pipeline。
     * </pre>
     */
    @Override
    public Channel findById(Long channelId) {
        Assert.assertNotNull(channelId);
        ChannelDO channel = getById(channelId);
        if (channel == null) {
            String exceptionCause = "query channelId:{}" + channelId + " return null.";
            logger.error("ERROR ## {}", exceptionCause);
            throw new ManagerException(exceptionCause);
        }
        return doToModel(channel);
    }

    @Override
    public Channel findByPipelineId(Long pipelineId) {
        Pipeline pipeline = pipelineService.findById(pipelineId);
        return findById(pipeline.getChannelId());
    }

    @Override
    public Channel findByIdWithoutColumn(Long channelId) {
        ChannelDO channel = getById(channelId);
        if (channel == null) {
            String exceptionCause = "query channelId:" + channelId + " return null.";
            logger.error("ERROR ## {}", exceptionCause);
            throw new ManagerException(exceptionCause);
        }

        List<Channel> channels = doToModelWithColumn(Arrays.asList(channel));
        return channels.get(0);
    }

    @Override
    public List<Channel> listByPipelineIds(Long... pipelineIds) {
        return Arrays.stream(pipelineIds).map(this::findByPipelineId).collect(Collectors.toList());
    }

    @Override
    public List<Channel> listByNodeId(Long nodeId) {
        List<Pipeline> pipelines = pipelineService.listByNodeId(nodeId);
        return pipelines.stream().map(Pipeline::getChannelId).map(this::findById).collect(Collectors.toList());
    }

    @Override
    public List<Channel> listOnlyChannels(Long... identities) {
        return Arrays.stream(identities).map(this::findById).collect(Collectors.toList());
    }

    @Override
    public List<Long> listAllChannelId() {
        return list().stream().map(ChannelDO::getId).collect(Collectors.toList());
    }

    @Override
    public List<Channel> listByNodeId(Long nodeId, ChannelStatus... statuses) {
        HashSet<ChannelStatus> channelStatuses = Sets.newHashSet(statuses);
        List<Channel> channels = listByNodeId(nodeId);
        return channels.stream().filter(channel -> channelStatuses.contains(channel.getStatus()))
                       .collect(Collectors.toList());

    }

    @Override
    public List<Channel> listByConditionWithoutColumn(Map condition) {
        List<ChannelDO> channelDos = list(buildLambdaQuery(condition));
        if (channelDos.isEmpty()) {
            logger.debug("DEBUG ## couldn't query any channel by the condition:{}",
                    JsonUtils.marshalToString(condition));
            return new ArrayList<>();
        }
        return doToModelWithColumn(channelDos);
    }

    @Override
    public void stopChannel(Long channelId) {
        switchChannelStatus(channelId, ChannelStatus.STOP);
    }

    @Override
    public void notifyChannel(Long channelId) {
        switchChannelStatus(channelId, null);
    }

    @Override
    public void startChannel(Long channelId) {
        switchChannelStatus(channelId, ChannelStatus.START);
    }

    /**
     * 拿到channel总数进行分页
     */
    public int getCount() {
        return count();
    }

    public int getCount(Map condition) {
        return count(buildLambdaQuery(condition));
    }

    private LambdaQueryWrapper<ChannelDO> buildLambdaQuery(Map condition) {
        Object searchKey = condition.get("searchKey");
        LambdaQueryWrapper<ChannelDO> eq = Wrappers.lambdaQuery();
        if (com.baomidou.mybatisplus.core.toolkit.StringUtils.checkValNotNull(searchKey)) {
            eq.like(ChannelDO::getName, searchKey).or().like(ChannelDO::getId, searchKey);
        }
        return eq;
    }
    /*----------------------Start/Stop Channel 短期优化：增加异常和条件判断--------------------------*/

    /**
     * <pre>
     * 切换Channel状态
     *      1.首先判断Channel是否为空或状态位是否正确
     *      2.通知总裁器，更新节点
     *      3.数据库数据库更新状态
     *      4.调用远程方法，推送Channel到node节点
     * </pre>
     */
    private void switchChannelStatus(final Long channelId, final ChannelStatus channelStatus) {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    final ChannelDO channelDo = getById(channelId);

                    if (null == channelDo) {
                        String exceptionCause = "query channelId:" + channelId + " return null.";
                        logger.error("ERROR ## {}", exceptionCause);
                        throw new ManagerException(exceptionCause);
                    }

                    ChannelStatus oldStatus = arbitrateManageService.channelEvent().status(channelDo.getId());
                    Channel channel = doToModel(channelDo);
                    // 检查下ddl/home配置
                    List<Pipeline> pipelines = channel.getPipelines();
                    if (pipelines.size() > 1) {
                        boolean ddlSync = true;
                        boolean homeSync = true;
                        for (Pipeline pipeline : pipelines) {
                            homeSync &= pipeline.getParameters().isHome();
                            ddlSync &= pipeline.getParameters().getDdlSync();
                        }

                        if (ddlSync) {
                            throw new InvalidConfigureException(InvalidConfigureException.INVALID_TYPE.DDL);
                        }

                        if (homeSync) {
                            throw new InvalidConfigureException(InvalidConfigureException.INVALID_TYPE.HOME);
                        }
                    }

                    channel.setStatus(oldStatus);
                    ChannelStatus newStatus = channelStatus;
                    if (newStatus != null) {
                        if (newStatus.equals(oldStatus)) {
                            // String exceptionCause = "switch the channel(" +
                            // channelId + ") status to " +
                            // channelStatus
                            // + " but it had the status:" + oldStatus;
                            // logger.error("ERROR ## " + exceptionCause);
                            // throw new ManagerException(exceptionCause);
                            // ignored
                            return;
                        } else {
                            channel.setStatus(newStatus);// 强制修改为当前变更状态
                        }
                    } else {
                        newStatus = oldStatus;
                    }

                    // 针对关闭操作，要优先更改对应的status，避免node工作线程继续往下跑
                    if (newStatus.isStop()) {
                        arbitrateManageService.channelEvent().stop(channelId);
                    } else if (newStatus.isPause()) {
                        arbitrateManageService.channelEvent().pause(channelId);
                    }

                    // 通知变更内容
                    boolean result = configRemoteService.notifyChannel(channel);// 客户端响应成功，才更改对应的状态

                    if (result) {
                        // 针对启动的话，需要先通知到客户端，客户端启动线程后，再更改channel状态
                        if (newStatus.isStart()) {
                            arbitrateManageService.channelEvent().start(channelId);
                        }
                    }

                } catch (Exception e) {
                    logger.error("ERROR ## switch the channel({}}) status has an exception.", channelId);
                    throw new ManagerException(e);
                }
            }
        });

    }

    /**
     * <pre>
     * 用于DO对象转化为Model对象
     * 现阶段优化：
     *      需要五次SQL交互:pipeline\node\dataMediaPair\dataMedia\dataMediaSource（五个层面）
     *      目前优化方案为单层只执行一次SQL，避免重复循环造成IO及数据库查询开销
     * 长期优化：
     *      对SQL进行改造，尽量减小SQL调用次数
     * </pre>
     *
     * @param channelDo channelDo
     * @return Channel
     */

    private Channel doToModel(ChannelDO channelDo) {
        Channel channel = new Channel();
        try {
            channel.setId(channelDo.getId());
            channel.setName(channelDo.getName());
            channel.setDescription(channelDo.getDescription());
            channel.setStatus(arbitrateManageService.channelEvent().status(channelDo.getId()));
            channel.setParameters(channelDo.getParameters());
            channel.setGmtCreate(channelDo.getGmtCreate());
            channel.setGmtModified(channelDo.getGmtModified());
            List<Pipeline> pipelines = pipelineService.listByChannelIds(channelDo.getId());
            // 合并PipelineParameter和ChannelParameter
            SystemParameter systemParameter = systemParameterService.find();
            for (Pipeline pipeline : pipelines) {
                PipelineParameter parameter = new PipelineParameter();
                parameter.merge(systemParameter);
                parameter.merge(channel.getParameters());
                // 最后复制pipelineId参数
                parameter.merge(pipeline.getParameters());
                pipeline.setParameters(parameter);
                // pipeline.getParameters().merge(channel.getParameters());
            }
            channel.setPipelines(pipelines);
        } catch (Exception e) {
            logger.error("ERROR ## change the channel DO to Model has an exception");
            throw new ManagerException(e);
        }

        return channel;
    }

    /**
     * <pre>
     * 用于DO对象数组转化为Model对象数组
     * 现阶段优化：
     *      需要五次SQL交互:pipeline\node\dataMediaPair\dataMedia\dataMediaSource（五个层面）
     *      目前优化方案为单层只执行一次SQL，避免重复循环造成IO及数据库查询开销
     * 长期优化：
     *      对SQL进行改造，尽量减小SQL调用次数
     * </pre>
     *
     * @param channelDos channelDos
     * @return Channel
     */
    private List<Channel> doToModel(List<ChannelDO> channelDos) {
        List<Channel> channels = new ArrayList<Channel>();
        try {
            // 1.将ChannelID单独拿出来
            List<Long> channelIds = new ArrayList<Long>();
            for (ChannelDO channelDo : channelDos) {
                channelIds.add(channelDo.getId());
            }
            Long[] idArray = new Long[channelIds.size()];

            // 拿到所有的Pipeline进行ChannelID过滤，避免重复查询。
            List<Pipeline> pipelines = pipelineService.listByChannelIds(channelIds.toArray(idArray));
            SystemParameter systemParameter = systemParameterService.find();
            for (ChannelDO channelDo : channelDos) {
                Channel channel = new Channel();
                channel.setId(channelDo.getId());
                channel.setName(channelDo.getName());
                channel.setDescription(channelDo.getDescription());
                ChannelStatus channelStatus = arbitrateManageService.channelEvent().status(channelDo.getId());
                channel.setStatus(null == channelStatus ? ChannelStatus.STOP : channelStatus);
                channel.setParameters(channelDo.getParameters());
                channel.setGmtCreate(channelDo.getGmtCreate());
                channel.setGmtModified(channelDo.getGmtModified());
                // 遍历，将该Channel节点下的Pipeline提取出来。
                List<Pipeline> subPipelines = new ArrayList<Pipeline>();
                for (Pipeline pipeline : pipelines) {
                    if (pipeline.getChannelId().equals(channelDo.getId())) {
                        // 合并PipelineParameter和ChannelParameter
                        PipelineParameter parameter = new PipelineParameter();
                        parameter.merge(systemParameter);
                        parameter.merge(channel.getParameters());
                        // 最后复制pipelineId参数
                        parameter.merge(pipeline.getParameters());
                        pipeline.setParameters(parameter);
                        subPipelines.add(pipeline);
                    }
                }

                channel.setPipelines(subPipelines);
                channels.add(channel);
            }
        } catch (Exception e) {
            logger.error("ERROR ## change the channels DO to Model has an exception");
            throw new ManagerException(e);
        }

        return channels;
    }

    private List<Channel> doToModelWithColumn(List<ChannelDO> channelDos) {
        List<Channel> channels = new ArrayList<Channel>();
        try {
            // 1.将ChannelID单独拿出来
            List<Long> channelIds = new ArrayList<Long>();
            for (ChannelDO channelDo : channelDos) {
                channelIds.add(channelDo.getId());
            }
            Long[] idArray = new Long[channelIds.size()];

            // 拿到所有的Pipeline进行ChannelID过滤，避免重复查询。
            List<Pipeline> pipelines = pipelineService.listByChannelIdsWithoutColumn(channelIds.toArray(idArray));
            SystemParameter systemParameter = systemParameterService.find();
            for (ChannelDO channelDo : channelDos) {
                Channel channel = new Channel();
                channel.setId(channelDo.getId());
                channel.setName(channelDo.getName());
                channel.setDescription(channelDo.getDescription());
                ChannelStatus channelStatus = arbitrateManageService.channelEvent().status(channelDo.getId());
                channel.setStatus(null == channelStatus ? ChannelStatus.STOP : channelStatus);
                channel.setParameters(channelDo.getParameters());
                channel.setGmtCreate(channelDo.getGmtCreate());
                channel.setGmtModified(channelDo.getGmtModified());
                // 遍历，将该Channel节点下的Pipeline提取出来。
                List<Pipeline> subPipelines = new ArrayList<Pipeline>();
                for (Pipeline pipeline : pipelines) {
                    if (pipeline.getChannelId().equals(channelDo.getId())) {
                        // 合并PipelineParameter和ChannelParameter
                        PipelineParameter parameter = new PipelineParameter();
                        parameter.merge(systemParameter);
                        parameter.merge(channel.getParameters());
                        // 最后复制pipelineId参数
                        parameter.merge(pipeline.getParameters());
                        pipeline.setParameters(parameter);
                        subPipelines.add(pipeline);
                    }
                }

                channel.setPipelines(subPipelines);
                channels.add(channel);
            }
        } catch (Exception e) {
            logger.error("ERROR ## change the channels DO to Model has an exception");
            throw new ManagerException(e);
        }

        return channels;
    }

    private List<Channel> doToModelOnlyChannels(List<ChannelDO> channelDos) {
        List<Channel> channels = new ArrayList<Channel>();
        try {
            // 1.将ChannelID单独拿出来
            List<Long> channelIds = new ArrayList<Long>();
            for (ChannelDO channelDo : channelDos) {
                channelIds.add(channelDo.getId());
            }

            for (ChannelDO channelDo : channelDos) {
                Channel channel = new Channel();
                channel.setId(channelDo.getId());
                channel.setName(channelDo.getName());
                channel.setDescription(channelDo.getDescription());
                ChannelStatus channelStatus = arbitrateManageService.channelEvent().status(channelDo.getId());
                channel.setStatus(null == channelStatus ? ChannelStatus.STOP : channelStatus);
                channel.setParameters(channelDo.getParameters());
                channel.setGmtCreate(channelDo.getGmtCreate());
                channel.setGmtModified(channelDo.getGmtModified());
                // 遍历，将该Channel节点下的Pipeline提取出来。
                List<Pipeline> subPipelines = new ArrayList<Pipeline>();
                channel.setPipelines(subPipelines);
                channels.add(channel);
            }
        } catch (Exception e) {
            logger.error("ERROR ## change the channels doToModelOnlyChannels has an exception");
            throw new ManagerException(e);
        }

        return channels;
    }

}
