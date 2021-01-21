package com.alibaba.otter.manager.biz.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (TableHistoryStat)表实体类
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:38
 */
@TableName("table_history_stat")
public class TableHistoryStatDO implements Serializable {

    private static final long serialVersionUID = -5761608094075955054L;
    @TableId("ID")
    private Long id;

    private Long fileSize;

    private Long fileCount;

    private Long insertCount;

    private Long updateCount;

    private Long deleteCount;

    private Long dataMediaPairId;

    private Long pipelineId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModified;

}
