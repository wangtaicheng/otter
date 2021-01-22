package com.alibaba.otter.manager.biz.service;

import com.alibaba.otter.manager.biz.entity.DataColumnPairGroupDO;
import com.alibaba.otter.shared.common.model.config.data.ColumnGroup;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * (ColumnPairGroup)表服务接口
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:38
 */
public interface ColumnPairGroupService extends IService<DataColumnPairGroupDO> {

    public void removeByDataMediaPairId(Long dataMediaPairId);

    public List<ColumnGroup> listByDataMediaPairId(Long dataMediaPairId);

    public Map<Long, List<ColumnGroup>> listByDataMediaPairIds(Long... dataMediaPairId);


    public void create(ColumnGroup entityObj);

    public void remove(Long identity);

    public void modify(ColumnGroup entityObj);

    public ColumnGroup findById(Long identity);

    public List<ColumnGroup> listByIds(Long... identities);

    public List<ColumnGroup> listAll();

    public List<ColumnGroup> listByCondition(Map condition);

    public int getCount();

    public int getCount(Map condition);
}
