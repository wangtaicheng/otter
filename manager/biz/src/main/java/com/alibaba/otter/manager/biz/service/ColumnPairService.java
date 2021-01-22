package com.alibaba.otter.manager.biz.service;

import com.alibaba.otter.manager.biz.entity.DataColumnPairDO;
import com.alibaba.otter.shared.common.model.config.data.ColumnPair;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * (ColumnPair)表服务接口
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:38
 */
public interface ColumnPairService extends IService<DataColumnPairDO> {
    public List<ColumnPair> listByDataMediaPairId(Long dataMediaPairId);

    public Map<Long, List<ColumnPair>> listByDataMediaPairIds(Long... dataMediaPairIds);

    public void createBatch(List<ColumnPair> dataColumnPairs);

    public void removeByDataMediaPairId(Long dataMediaPairId);

    public void create(ColumnPair entityObj);

    public void remove(Long identity);

    public void modify(ColumnPair entityObj);

    public ColumnPair findById(Long identity);

    public List<ColumnPair> listByIds(Long... identities);

    public List<ColumnPair> listAll();

    public List<ColumnPair> listByCondition(Map condition);

    public int getCount();

    public int getCount(Map condition);
}
