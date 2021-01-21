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
 * (DataMediaPair)表实体类
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:38
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DataMediaPair extends BaseModel<DataMediaPair> {

@TableId("ID")
    private Long id;

private Long pullweight;

private Long pushweight;

private String resolver;

private String filter;

private Long sourceDataMediaId;

private Long targetDataMediaId;

private Long pipelineId;

private String columnPairMode;

private LocalDateTime gmtCreate;

private LocalDateTime gmtModified;

}