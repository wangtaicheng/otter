package com.alibaba.otter.manager.biz.service;

import com.alibaba.otter.canal.instance.manager.model.Canal;
import com.alibaba.otter.manager.biz.entity.CanalDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * (Canal)表服务接口
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:38
 */
public interface CanalService extends IService<CanalDO> {
    void create(Canal canal);

    void remove(Long canalId);

    void modify(Canal canal);

    List<Canal> listByIds(Long... identities);

    List<Canal> listAll();

    Canal findById(Long canalId);

    Canal findByName(String name);

    int getCount(Map condition);

    List<Canal> listByCondition(Map condition);
}
