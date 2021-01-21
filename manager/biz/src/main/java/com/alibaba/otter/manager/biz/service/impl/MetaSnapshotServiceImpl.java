package com.alibaba.otter.manager.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.alibaba.otter.manager.biz.dao.MetaSnapshotMapper;
import com.alibaba.otter.manager.biz.entity.MetaSnapshot;
import com.alibaba.otter.manager.biz.service.MetaSnapshotService;
import org.springframework.stereotype.Service;

/**
 * 表结构记录表快照表(MetaSnapshot)表服务实现类
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:38
 */
@Service("metaSnapshotService")
public class MetaSnapshotServiceImpl extends ServiceImpl<MetaSnapshotMapper, MetaSnapshot> implements MetaSnapshotService {

}