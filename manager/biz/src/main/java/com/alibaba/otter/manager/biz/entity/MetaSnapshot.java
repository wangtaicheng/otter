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
 * 表结构记录表快照表(MetaSnapshot)表实体类
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:38
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MetaSnapshot extends BaseModel<MetaSnapshot> {

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
    * 表结构数据
    */
    private Object data;

   /**
    * 额外的扩展信息
    */
    private String extra;

}