package com.alibaba.otter.manager.biz.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.alibaba.otter.manager.biz.dao.AutoKeeperClusterMapper;
import com.alibaba.otter.manager.biz.entity.AutoKeeperClusterDO;
import com.alibaba.otter.manager.biz.service.AutoKeeperClusterService;
import com.alibaba.otter.shared.common.model.autokeeper.AutoKeeperCluster;
import com.alibaba.otter.shared.common.utils.JsonUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * (AutokeeperCluster)表服务实现类
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:38
 */
@Service("autokeeperClusterService")
public class AutoKeeperClusterServiceImpl extends ServiceImpl<AutoKeeperClusterMapper, AutoKeeperClusterDO> implements AutoKeeperClusterService {

    @Override
    public AutoKeeperCluster findAutoKeeperClusterById(Long id) {
        AutoKeeperClusterDO autoKeeperClusterDO = getById(id);
        return autoKeeperClusterDO == null ? null : doToModel(autoKeeperClusterDO);
    }

    @Override
    public List<AutoKeeperCluster> listAutoKeeperClusters() {
        return doToModel(list());
    }

    @Override
    public void modifyAutoKeeperCluster(AutoKeeperCluster autoKeeperCluster) {
        updateById(modelToDo(autoKeeperCluster));
    }

    @Override
    public void createAutoKeeperCluster(AutoKeeperCluster autoKeeperCluster) {
        save(modelToDo(autoKeeperCluster));
    }

    @Override
    public void removeAutoKeeperCluster(Long id) {
        removeById(id);
    }

    @Override
    public Integer getCount() {
        return count();
    }


    private AutoKeeperCluster doToModel(AutoKeeperClusterDO autoKeeperClusterDo) {
        AutoKeeperCluster autoKeeperCluster = new AutoKeeperCluster();
        autoKeeperCluster.setId(autoKeeperClusterDo.getId());
        autoKeeperCluster.setClusterName(autoKeeperClusterDo.getClusterName());
        autoKeeperCluster.setDescription(autoKeeperClusterDo.getDescription());
        autoKeeperCluster.setServerList(JsonUtils.unmarshalFromString(autoKeeperClusterDo.getServerList(),
                new TypeReference<List<String>>() {
                }));
        autoKeeperCluster.setGmtCreate(autoKeeperClusterDo.getGmtCreate());
        autoKeeperCluster.setGmtModified(autoKeeperClusterDo.getGmtModified());
        return autoKeeperCluster;
    }

    private List<AutoKeeperCluster> doToModel(List<AutoKeeperClusterDO> autoKeeperClusterDos) {
        List<AutoKeeperCluster> autoKeeperClusters = new ArrayList<AutoKeeperCluster>();
        for (AutoKeeperClusterDO autoKeeperClusterDo : autoKeeperClusterDos) {
            autoKeeperClusters.add(doToModel(autoKeeperClusterDo));
        }
        return autoKeeperClusters;
    }

    private AutoKeeperClusterDO modelToDo(AutoKeeperCluster autoKeeperCluster) {
        AutoKeeperClusterDO autokeeperClusterDo = new AutoKeeperClusterDO();
        autokeeperClusterDo.setId(autoKeeperCluster.getId());
        autokeeperClusterDo.setClusterName(autoKeeperCluster.getClusterName());
        autokeeperClusterDo.setDescription(autoKeeperCluster.getDescription());
        autokeeperClusterDo.setServerList(JsonUtils.marshalToString(autoKeeperCluster.getServerList()));
        autokeeperClusterDo.setGmtCreate(autoKeeperCluster.getGmtCreate());
        autokeeperClusterDo.setGmtModified(autoKeeperCluster.getGmtModified());
        return autokeeperClusterDo;
    }

}
