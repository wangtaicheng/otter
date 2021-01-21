package com.alibaba.otter.manager.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.alibaba.otter.manager.biz.dao.MetaHistoryMapper;
import com.alibaba.otter.manager.biz.entity.MetaHistory;
import com.alibaba.otter.manager.biz.service.MetaHistoryService;
import org.springframework.stereotype.Service;

/**
 * 表结构变化明细表(MetaHistory)表服务实现类
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:38
 */
@Service("metaHistoryService")
public class MetaHistoryServiceImpl extends ServiceImpl<MetaHistoryMapper, MetaHistory> implements MetaHistoryService {

}