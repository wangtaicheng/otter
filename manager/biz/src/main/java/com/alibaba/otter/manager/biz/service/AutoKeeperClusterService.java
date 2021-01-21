package com.alibaba.otter.manager.biz.service;

import com.alibaba.otter.manager.biz.entity.AutoKeeperClusterDO;
import com.alibaba.otter.shared.common.model.autokeeper.AutoKeeperCluster;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * (AutokeeperCluster)表服务接口
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:38
 */
public interface AutoKeeperClusterService extends IService<AutoKeeperClusterDO> {
    AutoKeeperCluster findAutoKeeperClusterById(Long id);

    List<AutoKeeperCluster> listAutoKeeperClusters();

    void modifyAutoKeeperCluster(AutoKeeperCluster autoKeeperCluster);

    void createAutoKeeperCluster(AutoKeeperCluster autoKeeperCluster);

    void removeAutoKeeperCluster(Long id);

    Integer getCount();
}
