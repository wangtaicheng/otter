package com.alibaba.otter.manager.biz.entity;


import cn.chinaunicom.core.base.BaseModel;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * (AlarmRule)表实体类
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:21
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AlarmRule extends BaseModel<AlarmRule> {

    @TableId("ID")
    private Long id;

    private String monitorName;

    private String receiverKey;

    private String status;

    private Long pipelineId;

    private String description;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModified;

    private String matchValue;

    private String parameters;

}
