package com.alibaba.otter.manager.biz.entity;


import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 表结构变化明细表(MetaHistory)表实体类
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:38
 */

public class MetaHistory implements Serializable {

    private static final long serialVersionUID = -8070634024409531811L;
    /**
     * 主键
     */
    @TableId("id")
    private Long id;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    private LocalDateTime gmtModified;

    /**
     * 通道名称
     */
    private String destination;

    /**
     * binlog文件名
     */
    private String binlogFile;

    /**
     * binlog偏移量
     */
    private Long binlogOffest;

    /**
     * binlog节点id
     */
    private String binlogMasterId;

    /**
     * binlog应用的时间戳
     */
    private Long binlogDatetime;

    /**
     * 执行sql时对应的schema
     */
    private String useSchema;

    /**
     * 对应的schema
     */
    private String sqlSchema;

    /**
     * 对应的table
     */
    private String sqlTable;

    /**
     * 执行的sql
     */
    private Object sqlText;

    /**
     * sql类型
     */
    private String sqlType;

    /**
     * 额外的扩展信息
     */
    private String extra;

}
