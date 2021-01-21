package com.alibaba.otter.manager.biz.service.impl;

import com.alibaba.otter.manager.biz.dao.ColumnPairMapper;
import com.alibaba.otter.manager.biz.entity.DataColumnPairDO;
import com.alibaba.otter.manager.biz.service.ColumnPairService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * (ColumnPair)表服务实现类
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:38
 */
@Service("columnPairService")
public class ColumnPairServiceImpl extends ServiceImpl<ColumnPairMapper, DataColumnPairDO> implements ColumnPairService {

}
