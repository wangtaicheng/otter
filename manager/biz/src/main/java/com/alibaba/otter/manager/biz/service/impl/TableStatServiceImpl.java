package com.alibaba.otter.manager.biz.service.impl;

import com.alibaba.otter.manager.biz.dao.TableStatMapper;
import com.alibaba.otter.manager.biz.entity.TableStatDO;
import com.alibaba.otter.manager.biz.service.TableStatService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * (TableStat)表服务实现类
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:38
 */
@Service("tableStatService")
public class TableStatServiceImpl extends ServiceImpl<TableStatMapper, TableStatDO> implements TableStatService {

}
