package com.alibaba.otter.manager.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.alibaba.otter.manager.biz.dao.TableHistoryStatMapper;
import com.alibaba.otter.manager.biz.entity.TableHistoryStat;
import com.alibaba.otter.manager.biz.service.TableHistoryStatService;
import org.springframework.stereotype.Service;

/**
 * (TableHistoryStat)表服务实现类
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:38
 */
@Service("tableHistoryStatService")
public class TableHistoryStatServiceImpl extends ServiceImpl<TableHistoryStatMapper, TableHistoryStat> implements TableHistoryStatService {

}