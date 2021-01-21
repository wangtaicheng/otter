package com.alibaba.otter.manager.biz.service.impl;

import com.alibaba.otter.manager.biz.dao.LogRecordMapper;
import com.alibaba.otter.manager.biz.entity.LogRecordDO;
import com.alibaba.otter.manager.biz.service.LogRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * (LogRecord)表服务实现类
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:38
 */
@Service("logRecordService")
public class LogRecordServiceImpl extends ServiceImpl<LogRecordMapper, LogRecordDO> implements LogRecordService {

}
