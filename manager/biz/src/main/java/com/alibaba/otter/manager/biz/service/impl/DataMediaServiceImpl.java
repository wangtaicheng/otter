package com.alibaba.otter.manager.biz.service.impl;

import com.alibaba.otter.manager.biz.dao.DataMediaMapper;
import com.alibaba.otter.manager.biz.entity.DataMediaPairDO;
import com.alibaba.otter.manager.biz.service.DataMediaService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * (DataMedia)表服务实现类
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:38
 */
@Service("dataMediaService")
public class DataMediaServiceImpl extends ServiceImpl<DataMediaMapper, DataMediaPairDO> implements DataMediaService {

}
