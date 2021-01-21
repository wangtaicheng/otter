package com.alibaba.otter.manager.biz.service.impl;

import com.alibaba.otter.manager.biz.dao.DataMatrixMapper;
import com.alibaba.otter.manager.biz.entity.DataMatrixDO;
import com.alibaba.otter.manager.biz.service.DataMatrixService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * (DataMatrix)表服务实现类
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:38
 */
@Service("dataMatrixService")
public class DataMatrixServiceImpl extends ServiceImpl<DataMatrixMapper, DataMatrixDO> implements DataMatrixService {

}
