package com.alibaba.otter.manager.biz.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.alibaba.otter.manager.biz.entity.DelayStat;
import com.alibaba.otter.manager.biz.service.DelayStatService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (DelayStat)表控制层
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:38
 */
@RestController
@RequestMapping("delayStat")
public class DelayStatController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private DelayStatService delayStatService;

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param delayStat 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<DelayStat> page, DelayStat delayStat) {
        return success(this.delayStatService.page(page, new QueryWrapper<>(delayStat)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.delayStatService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param delayStat 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody DelayStat delayStat) {
        return success(this.delayStatService.save(delayStat));
    }

    /**
     * 修改数据
     *
     * @param delayStat 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody DelayStat delayStat) {
        return success(this.delayStatService.updateById(delayStat));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.delayStatService.removeByIds(idList));
    }
}