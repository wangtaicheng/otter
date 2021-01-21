package com.alibaba.otter.manager.biz.service.impl;

import com.alibaba.otter.canal.parse.inbound.mysql.tsdb.dao.MetaHistoryDO;
import com.alibaba.otter.manager.biz.dao.MetaHistoryMapper;
import com.alibaba.otter.manager.biz.service.MetaHistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 表结构变化明细表(MetaHistory)表服务实现类
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:38
 */
@Service("metaHistoryService")
public class MetaHistoryServiceImpl extends ServiceImpl<MetaHistoryMapper, MetaHistoryDO> implements MetaHistoryService {

}
