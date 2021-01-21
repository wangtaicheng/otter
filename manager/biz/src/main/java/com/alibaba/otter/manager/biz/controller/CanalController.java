package com.alibaba.otter.manager.biz.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.alibaba.otter.manager.biz.entity.Canal;
import com.alibaba.otter.manager.biz.service.CanalService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (Canal)表控制层
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:38
 */
@RestController
@RequestMapping("canal")
public class CanalController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private CanalService canalService;

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param canal 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<Canal> page, Canal canal) {
        return success(this.canalService.page(page, new QueryWrapper<>(canal)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.canalService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param canal 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody Canal canal) {
        return success(this.canalService.save(canal));
    }

    /**
     * 修改数据
     *
     * @param canal 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody Canal canal) {
        return success(this.canalService.updateById(canal));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.canalService.removeByIds(idList));
    }
}