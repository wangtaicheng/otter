package com.alibaba.otter.manager.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.alibaba.otter.manager.biz.dao.DataMediaPairMapper;
import com.alibaba.otter.manager.biz.entity.DataMediaPair;
import com.alibaba.otter.manager.biz.service.DataMediaPairService;
import org.springframework.stereotype.Service;

/**
 * (DataMediaPair)表服务实现类
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:38
 */
@Service("dataMediaPairService")
public class DataMediaPairServiceImpl extends ServiceImpl<DataMediaPairMapper, DataMediaPair> implements DataMediaPairService {

}