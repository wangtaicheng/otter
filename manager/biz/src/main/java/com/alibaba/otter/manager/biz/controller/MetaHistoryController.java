package com.alibaba.otter.manager.biz.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.alibaba.otter.manager.biz.entity.MetaHistory;
import com.alibaba.otter.manager.biz.service.MetaHistoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 表结构变化明细表(MetaHistory)表控制层
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:38
 */
@RestController
@RequestMapping("metaHistory")
public class MetaHistoryController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private MetaHistoryService metaHistoryService;

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param metaHistory 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<MetaHistory> page, MetaHistory metaHistory) {
        return success(this.metaHistoryService.page(page, new QueryWrapper<>(metaHistory)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.metaHistoryService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param metaHistory 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody MetaHistory metaHistory) {
        return success(this.metaHistoryService.save(metaHistory));
    }

    /**
     * 修改数据
     *
     * @param metaHistory 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody MetaHistory metaHistory) {
        return success(this.metaHistoryService.updateById(metaHistory));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.metaHistoryService.removeByIds(idList));
    }
}