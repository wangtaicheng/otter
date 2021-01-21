package com.alibaba.otter.manager.biz.dao;

import com.alibaba.otter.manager.biz.entity.MetaSnapshot;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 表结构记录表快照表(MetaSnapshot)表数据库访问层
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:38
 */
@Mapper
public interface MetaSnapshotMapper extends BaseMapper<MetaSnapshot> {

}
