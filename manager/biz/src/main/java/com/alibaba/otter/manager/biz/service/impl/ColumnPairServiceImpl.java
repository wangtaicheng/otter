package com.alibaba.otter.manager.biz.service.impl;

import com.alibaba.otter.manager.biz.common.exceptions.ManagerException;
import com.alibaba.otter.manager.biz.common.exceptions.RepeatConfigureException;
import com.alibaba.otter.manager.biz.config.datacolumnpair.impl.DataColumnPairServiceImpl;
import com.alibaba.otter.manager.biz.dao.ColumnPairMapper;
import com.alibaba.otter.manager.biz.entity.DataColumnPairDO;
import com.alibaba.otter.manager.biz.service.ColumnPairService;
import com.alibaba.otter.shared.common.model.config.data.Column;
import com.alibaba.otter.shared.common.model.config.data.ColumnPair;
import com.alibaba.otter.shared.common.utils.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * (ColumnPair)表服务实现类
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:38
 */
@Service("columnPairService")
public class ColumnPairServiceImpl extends ServiceImpl<ColumnPairMapper, DataColumnPairDO> implements ColumnPairService {
    private static final Logger logger = LoggerFactory.getLogger(DataColumnPairServiceImpl.class);

    @Override
    public void create(ColumnPair entityObj) {
        Assert.assertNotNull(entityObj);
        try {
            DataColumnPairDO dataColumnPairDo = modelToDo(entityObj);
            save(dataColumnPairDo);
        } catch (RepeatConfigureException rcf) {
            throw rcf;
        } catch (Exception e) {
            logger.error("ERROR ## create dataColumnPair has an exception!");
            throw new ManagerException(e);
        }

    }

    @Override
    public void createBatch(List<ColumnPair> dataColumnPairs) {
        Assert.assertNotNull(dataColumnPairs);

        try {
            List<DataColumnPairDO> collect = dataColumnPairs.stream().map(this::modelToDo).collect(Collectors.toList());
            saveBatch(collect);
        } catch (RepeatConfigureException rcf) {
            throw rcf;
        } catch (Exception e) {
            logger.error("ERROR ## create dataColumnPair has an exception!");
            throw new ManagerException(e);
        }
    }

    @Override
    public void remove(Long identity) {
        Assert.assertNotNull(identity);
        try {
            removeById(identity);
        } catch (Exception e) {
            logger.error("ERROR ## remove dataColumnPair has an exception!");
            throw new ManagerException(e);
        }
    }

    @Override
    public void modify(ColumnPair entityObj) {
        Assert.assertNotNull(entityObj);

        try {
            DataColumnPairDO dataColumnPairDo = modelToDo(entityObj);
            updateById(dataColumnPairDo);
        } catch (RepeatConfigureException rce) {
            throw rce;
        } catch (Exception e) {
            logger.error("ERROR ## modify dataColumnPair has an exception!");
            throw new ManagerException(e);
        }
    }

    @Override
    public ColumnPair findById(Long identity) {
        Assert.assertNotNull(identity);
        DataColumnPairDO columePairDo = getById(identity);
        if (columePairDo == null) {
            return null;
        }
        return doToModel(columePairDo);

    }

    @Override
    public List<ColumnPair> listByIds(Long... identities) {
        return null;
    }

    @Override
    public List<ColumnPair> listAll() {
        return null;
    }

    @Override
    public List<ColumnPair> listByDataMediaPairId(Long dataMediaPairId) {
        Assert.assertNotNull(dataMediaPairId);
        List<ColumnPair> dataColumnPairs = new ArrayList<>();
        try {
            LambdaQueryWrapper<DataColumnPairDO> eq = Wrappers.<DataColumnPairDO>lambdaQuery()
                    .eq(DataColumnPairDO::getDataMediaPairId, dataMediaPairId);
            List<DataColumnPairDO> dataColumnPairDos = list(eq);
            if (dataColumnPairDos.isEmpty()) {
                logger.debug("DEBUG ## couldn't query any dataColumnPair, maybe hasn't create any dataColumnPair.");
                return dataColumnPairs;
            }
            dataColumnPairs = doToModel(dataColumnPairDos);
        } catch (Exception e) {
            logger.error("ERROR ## query dataColumnPair by dataMediaId:{} has an exception!", dataMediaPairId);
            throw new ManagerException(e);
        }

        return dataColumnPairs;
    }

    @Override
    public Map<Long, List<ColumnPair>> listByDataMediaPairIds(Long... dataMediaPairIds) {
        return Arrays.stream(dataMediaPairIds).map(this::listByDataMediaPairId).filter(s -> !s.isEmpty())
                     .flatMap(Collection::stream).collect(Collectors.groupingBy(ColumnPair::getDataMediaPairId));

    }

    @Override
    public void removeByDataMediaPairId(Long dataMediaPairId) {
        Assert.assertNotNull(dataMediaPairId);
        try {
            LambdaQueryWrapper<DataColumnPairDO> eq = Wrappers.<DataColumnPairDO>lambdaQuery()
                    .eq(DataColumnPairDO::getDataMediaPairId, dataMediaPairId);
            List<DataColumnPairDO> dataColumnPairDos = list(eq);
            dataColumnPairDos.forEach(d -> {
                remove(d.getId());
            });
        } catch (Exception e) {
            logger.error("ERROR ## remove dataColumnPair has an exception!");
            throw new ManagerException(e);
        }
    }

    @Override
    public List<ColumnPair> listByCondition(Map condition) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public int getCount(Map condition) {
        return 0;
    }

    /*-------------------------------------------------------------*/

    /**
     * 用于DO对象转化为Model对象
     */
    private ColumnPair doToModel(DataColumnPairDO dataColumnPairDo) {

        Column sourceColumn = dataColumnPairDo.getSourceColumnName() == null ? null : new Column(
                dataColumnPairDo.getSourceColumnName());
        Column targetColumn = dataColumnPairDo.getTargetColumnName() == null ? null : new Column(
                dataColumnPairDo.getTargetColumnName());
        ColumnPair columnPair = new ColumnPair(sourceColumn, targetColumn);
        columnPair.setId(dataColumnPairDo.getId());
        columnPair.setDataMediaPairId(dataColumnPairDo.getDataMediaPairId());
        columnPair.setGmtCreate(dataColumnPairDo.getGmtCreate());
        columnPair.setGmtModified(dataColumnPairDo.getGmtModified());

        return columnPair;
    }

    private List<ColumnPair> doToModel(List<DataColumnPairDO> dataColumnPairDos) {

        List<ColumnPair> columnPairs = new ArrayList<ColumnPair>();
        for (DataColumnPairDO dataColumnPairDo : dataColumnPairDos) {
            columnPairs.add(doToModel(dataColumnPairDo));
        }

        return columnPairs;
    }

    /**
     * 用于Model对象转化为DO对象
     *
     * @param dataColumnPair
     * @return DataMediaPairDO
     */
    private DataColumnPairDO modelToDo(ColumnPair dataColumnPair) {
        DataColumnPairDO dataColumnPairDo = new DataColumnPairDO();
        dataColumnPairDo.setId(dataColumnPair.getId());
        dataColumnPairDo
                .setSourceColumnName(dataColumnPair.getSourceColumn() == null ? null : dataColumnPair.getSourceColumn()
                                                                                                     .getName());
        dataColumnPairDo
                .setTargetColumnName(dataColumnPair.getTargetColumn() == null ? null : dataColumnPair.getTargetColumn()
                                                                                                     .getName());
        dataColumnPairDo.setDataMediaPairId(dataColumnPair.getDataMediaPairId());
        dataColumnPairDo.setGmtCreate(dataColumnPair.getGmtCreate());
        dataColumnPairDo.setGmtModified(dataColumnPair.getGmtModified());

        return dataColumnPairDo;
    }
}
