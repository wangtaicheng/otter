package com.alibaba.otter.manager.biz.entity;


import java.time.LocalDateTime;
import cn.chinaunicom.core.base.BaseModel;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * (TableStat)表实体类
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:38
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TableStat extends BaseModel<TableStat> {

@TableId("ID")
    private Long id;

private Long fileSize;

private Long fileCount;

private Long insertCount;

private Long updateCount;

private Long deleteCount;

private Long dataMediaPairId;

private Long pipelineId;

private LocalDateTime gmtCreate;

private LocalDateTime gmtModified;

}