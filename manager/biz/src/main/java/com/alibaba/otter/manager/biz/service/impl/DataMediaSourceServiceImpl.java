package com.alibaba.otter.manager.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.alibaba.otter.manager.biz.dao.DataMediaSourceMapper;
import com.alibaba.otter.manager.biz.entity.DataMediaSource;
import com.alibaba.otter.manager.biz.service.DataMediaSourceService;
import org.springframework.stereotype.Service;

/**
 * (DataMediaSource)表服务实现类
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:38
 */
@Service("dataMediaSourceService")
public class DataMediaSourceServiceImpl extends ServiceImpl<DataMediaSourceMapper, DataMediaSource> implements DataMediaSourceService {

}