package com.alibaba.otter.manager.biz.service.impl;

import com.alibaba.otter.canal.instance.manager.model.Canal;
import com.alibaba.otter.canal.instance.manager.model.CanalParameter;
import com.alibaba.otter.manager.biz.common.exceptions.ManagerException;
import com.alibaba.otter.manager.biz.common.exceptions.RepeatConfigureException;
import com.alibaba.otter.manager.biz.dao.CanalMapper;
import com.alibaba.otter.manager.biz.entity.CanalDO;
import com.alibaba.otter.manager.biz.service.AutoKeeperClusterService;
import com.alibaba.otter.manager.biz.service.CanalService;
import com.alibaba.otter.shared.common.model.autokeeper.AutoKeeperCluster;
import com.alibaba.otter.shared.common.utils.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.alibaba.otter.manager.biz.utils.DateUtils.asDate;
import static com.alibaba.otter.manager.biz.utils.DateUtils.asLocalDateTime;

/**
 * (Canal)表服务实现类
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:38
 */
@Service("canalService")
public class CanalServiceImpl extends ServiceImpl<CanalMapper, CanalDO> implements CanalService {
    private static final Logger logger = LoggerFactory.getLogger(CanalServiceImpl.class);
    @Autowired
    private AutoKeeperClusterService autoKeeperClusterService;

    @Override
    public void create(Canal canal) {
        Assert.assertNotNull(canal);
        CanalDO canalDO = modelToDo(canal);
        if (save(canalDO)) {
            canal.setId(canalDO.getId());
        } else {
            String exceptionCause = "exist the same repeat canal in the database.";
            logger.warn("WARN ## " + exceptionCause);
            throw new RepeatConfigureException(exceptionCause);
        }
    }

    @Override
    public void remove(Long canalId) {
        removeById(canalId);
    }

    @Override
    public void modify(Canal canal) {
        updateById(modelToDo(canal));
    }

    @Override
    public List<Canal> listByIds(Long... identities) {
        if (identities.length < 1) {
            return listAll();
        } else {
            LambdaQueryWrapper<CanalDO> in = Wrappers.<CanalDO>lambdaQuery()
                    .in(CanalDO::getId, Arrays.asList(identities));
            return doToModel(list(in));
        }
    }

    @Override
    public List<Canal> listAll() {
        return doToModel(list());
    }

    @Override
    public Canal findById(Long canalId) {
        return doToModel(getById(canalId));
    }

    @Override
    public Canal findByName(String name) {
        LambdaQueryWrapper<CanalDO> eq = Wrappers.<CanalDO>lambdaQuery()
                .eq(CanalDO::getName, name);
        return doToModel(getOne(eq));
    }

    @Override
    public int getCount(Map condition) {

        return count(buildLambdaQuery(condition));
    }


    @Override
    public List<Canal> listByCondition(Map condition) {
        return doToModel(list(buildLambdaQuery(condition)));
    }

    private LambdaQueryWrapper<CanalDO> buildLambdaQuery(Map condition) {
        Object searchKey = condition.get("searchKey");
        LambdaQueryWrapper<CanalDO> eq = Wrappers.lambdaQuery();
        if (com.baomidou.mybatisplus.core.toolkit.StringUtils.checkValNotNull(searchKey)) {
            eq.like(CanalDO::getName, searchKey).or().like(CanalDO::getId, searchKey).or()
              .like(CanalDO::getParameters,
                      searchKey);
        }
        return eq;
    }

    /**
     * 用于Model对象转化为DO对象
     *
     * @param canal
     * @return CanalDO
     */
    private CanalDO modelToDo(Canal canal) {
        CanalDO canalDo = new CanalDO();
        try {
            canalDo.setId(canal.getId());
            canalDo.setName(canal.getName());
            canalDo.setStatus(canal.getStatus());
            canalDo.setDescription(canal.getDesc());
            canalDo.setParameters(canal.getCanalParameter());
            canalDo.setGmtCreate(asLocalDateTime(canal.getGmtCreate()));
            canalDo.setGmtModified(asLocalDateTime(canal.getGmtModified()));
        } catch (Exception e) {
            logger.error("ERROR ## change the canal Model to Do has an exception");
            throw new ManagerException(e);
        }
        return canalDo;
    }

    /**
     * 用于DO对象转化为Model对象
     *
     * @param canalDo
     * @return Canal
     */
    private Canal doToModel(CanalDO canalDo) {
        Canal canal = new Canal();
        try {
            canal.setId(canalDo.getId());
            canal.setName(canalDo.getName());
            canal.setStatus(canalDo.getStatus());
            canal.setDesc(canalDo.getDescription());
            CanalParameter parameter = canalDo.getParameters();
            AutoKeeperCluster zkCluster = autoKeeperClusterService
                    .findAutoKeeperClusterById(parameter.getZkClusterId());
            if (zkCluster != null) {
                parameter.setZkClusters(Arrays.asList(StringUtils.join(zkCluster.getServerList(), ',')));
            }
            canal.setCanalParameter(canalDo.getParameters());
            canal.setGmtCreate(asDate(canalDo.getGmtCreate()));
            canal.setGmtModified(asDate(canalDo.getGmtModified()));
        } catch (Exception e) {
            logger.error("ERROR ## change the canal Do to Model has an exception");
            throw new ManagerException(e);
        }

        return canal;
    }

    private List<Canal> doToModel(List<CanalDO> canalDos) {
        return canalDos.stream().map(this::doToModel).collect(Collectors.toList());

    }
}
