package com.alibaba.otter.manager.biz.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (SystemParameter)表实体类
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:38
 */
@TableName("system_parameter")
public class SystemParameterDO implements Serializable {

    private static final long serialVersionUID = 4656811319603742306L;
    @TableId("ID")
    private Long id;

    private String value;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModified;

}
