package com.alibaba.otter.manager.biz.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.alibaba.otter.manager.biz.entity.ColumnPair;
import com.alibaba.otter.manager.biz.service.ColumnPairService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (ColumnPair)表控制层
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:38
 */
@RestController
@RequestMapping("columnPair")
public class ColumnPairController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private ColumnPairService columnPairService;

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param columnPair 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<ColumnPair> page, ColumnPair columnPair) {
        return success(this.columnPairService.page(page, new QueryWrapper<>(columnPair)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.columnPairService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param columnPair 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody ColumnPair columnPair) {
        return success(this.columnPairService.save(columnPair));
    }

    /**
     * 修改数据
     *
     * @param columnPair 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody ColumnPair columnPair) {
        return success(this.columnPairService.updateById(columnPair));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.columnPairService.removeByIds(idList));
    }
}