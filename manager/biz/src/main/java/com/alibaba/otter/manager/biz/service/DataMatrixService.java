package com.alibaba.otter.manager.biz.service;

import com.alibaba.otter.manager.biz.entity.DataMatrixDO;
import com.alibaba.otter.shared.common.model.config.data.DataMatrix;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * (DataMatrix)表服务接口
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:38
 */
public interface DataMatrixService extends IService<DataMatrixDO> {
    public void create(DataMatrix DataMatrix);

    public void remove(Long DataMatrixId);

    public void modify(DataMatrix DataMatrix);

    public List<DataMatrix> listByIds(Long... identities);

    public List<DataMatrix> listAll();

    public DataMatrix findById(Long DataMatrixId);

    public DataMatrix findByGroupKey(String name);

    public int getCount(Map condition);

    public List<DataMatrix> listByCondition(Map condition);
}
