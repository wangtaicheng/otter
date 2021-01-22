package com.alibaba.otter.manager.biz.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.alibaba.otter.manager.biz.common.exceptions.ManagerException;
import com.alibaba.otter.manager.biz.common.exceptions.RepeatConfigureException;
import com.alibaba.otter.manager.biz.dao.ColumnPairGroupMapper;
import com.alibaba.otter.manager.biz.entity.DataColumnPairGroupDO;
import com.alibaba.otter.manager.biz.service.ColumnPairGroupService;
import com.alibaba.otter.manager.biz.service.ColumnPairService;
import com.alibaba.otter.shared.common.model.config.data.ColumnGroup;
import com.alibaba.otter.shared.common.model.config.data.ColumnPair;
import com.alibaba.otter.shared.common.utils.Assert;
import com.alibaba.otter.shared.common.utils.JsonUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * (ColumnPairGroup)表服务实现类
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:38
 */
@Service("columnPairGroupService")
public class ColumnPairGroupServiceImpl extends ServiceImpl<ColumnPairGroupMapper, DataColumnPairGroupDO> implements ColumnPairGroupService {
    private static final Logger logger = LoggerFactory.getLogger(ColumnPairGroupServiceImpl.class);
    @Autowired
    private ColumnPairService dataColumnPairService;

    @Override
    public void create(ColumnGroup entityObj) {
        Assert.assertNotNull(entityObj);

        try {
            DataColumnPairGroupDO dataColumnPairGroupDo = modelToDo(entityObj);
            save(dataColumnPairGroupDo);
        } catch (RepeatConfigureException rcf) {
            throw rcf;
        } catch (Exception e) {
            logger.error("ERROR ## create dataColumnPairGroup has an exception!");
            throw new ManagerException(e);
        }
    }

    @Override
    public List<ColumnGroup> listByDataMediaPairId(Long dataMediaPairId) {
        Assert.assertNotNull(dataMediaPairId);
        LambdaQueryWrapper<DataColumnPairGroupDO> eq = Wrappers.<DataColumnPairGroupDO>lambdaQuery()
                .eq(DataColumnPairGroupDO::getDataMediaPairId, dataMediaPairId);
        List<DataColumnPairGroupDO> dataColumnPairGroupDos = list(eq);
        if (CollectionUtils.isEmpty(dataColumnPairGroupDos)) {
            return new ArrayList<>();
        }

        return doToModel(dataColumnPairGroupDos);
    }

    @Override
    public Map<Long, List<ColumnGroup>> listByDataMediaPairIds(Long... dataMediaPairIds) {
        Assert.assertNotNull(dataMediaPairIds);
        return Arrays.stream(dataMediaPairIds).map(this::listByDataMediaPairId).filter(s -> !s.isEmpty())
                     .flatMap(Collection::stream).collect(Collectors.groupingBy(ColumnGroup::getDataMediaPairId));

    }

    @Override
    public void remove(Long identity) {
        removeById(identity);
    }

    @Override
    public void removeByDataMediaPairId(Long dataMediaPairId) {
        Assert.assertNotNull(dataMediaPairId);
        LambdaQueryWrapper<DataColumnPairGroupDO> eq = Wrappers.<DataColumnPairGroupDO>lambdaQuery()
                .eq(DataColumnPairGroupDO::getDataMediaPairId, dataMediaPairId);
        List<DataColumnPairGroupDO> dataColumnPairGroupDos = list(eq);
        dataColumnPairGroupDos.forEach(d -> {
            remove(d.getId());
        });
    }

    @Override
    public void modify(ColumnGroup entityObj) {
        updateById(modelToDo(entityObj));
    }

    @Override
    public ColumnGroup findById(Long identity) {
        return doToModel(getById(identity));
    }

    @Override
    public List<ColumnGroup> listByIds(Long... identities) {
        return Arrays.stream(identities).map(this::findById).collect(Collectors.toList());
    }

    @Override
    public List<ColumnGroup> listAll() {
        return doToModel(list());
    }

    @Override
    public List<ColumnGroup> listByCondition(Map condition) {
        return null;
    }

    @Override
    public int getCount() {
        return count();
    }

    @Override
    public int getCount(Map condition) {
        return 0;
    }

    /*-------------------------------------------------------------*/

    /**
     * 用于DO对象转化为Model对象
     */
    private ColumnGroup doToModel(DataColumnPairGroupDO dataColumnPairGroupDo) {
        ColumnGroup columnGroup = new ColumnGroup();
        columnGroup.setId(dataColumnPairGroupDo.getId());
        List<ColumnPair> columnPairs = new ArrayList<>();
        if (StringUtils.isNotBlank(dataColumnPairGroupDo.getColumnPairContent())) {
            columnPairs = JsonUtils.unmarshalFromString(dataColumnPairGroupDo.getColumnPairContent(),
                    new TypeReference<ArrayList<ColumnPair>>() {
                    });
        }

        columnGroup.setColumnPairs(columnPairs);
        columnGroup.setDataMediaPairId(dataColumnPairGroupDo.getDataMediaPairId());
        columnGroup.setGmtCreate(dataColumnPairGroupDo.getGmtCreate());
        columnGroup.setGmtModified(dataColumnPairGroupDo.getGmtModified());

        return columnGroup;
    }

    private List<ColumnGroup> doToModel(List<DataColumnPairGroupDO> dataColumnPairGroupDos) {
        return dataColumnPairGroupDos.stream().map(this::doToModel).collect(Collectors.toList());
    }

    /**
     * 用于Model对象转化为DO对象
     *
     * @param columnGroup ColumnGroup
     * @return DataMediaPairDO
     */
    private DataColumnPairGroupDO modelToDo(ColumnGroup columnGroup) {
        DataColumnPairGroupDO dataColumnPairGroupDo = new DataColumnPairGroupDO();
        dataColumnPairGroupDo.setId(columnGroup.getId());
        dataColumnPairGroupDo.setColumnPairContent(JsonUtils.marshalToString(columnGroup.getColumnPairs()));
        dataColumnPairGroupDo.setDataMediaPairId(columnGroup.getDataMediaPairId());
        dataColumnPairGroupDo.setGmtCreate(columnGroup.getGmtCreate());
        dataColumnPairGroupDo.setGmtModified(columnGroup.getGmtModified());

        return dataColumnPairGroupDo;
    }

}
