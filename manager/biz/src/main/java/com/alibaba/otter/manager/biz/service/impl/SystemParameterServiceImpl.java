package com.alibaba.otter.manager.biz.service.impl;

import com.alibaba.otter.manager.biz.dao.SystemParameterMapper;
import com.alibaba.otter.manager.biz.entity.SystemParameterDO;
import com.alibaba.otter.manager.biz.service.SystemParameterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * (SystemParameter)表服务实现类
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:38
 */
@Service("systemParameterService")
public class SystemParameterServiceImpl extends ServiceImpl<SystemParameterMapper, SystemParameterDO> implements SystemParameterService {

}
