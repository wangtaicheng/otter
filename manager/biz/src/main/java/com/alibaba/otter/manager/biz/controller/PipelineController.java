package com.alibaba.otter.manager.biz.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.alibaba.otter.manager.biz.entity.Pipeline;
import com.alibaba.otter.manager.biz.service.PipelineService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (Pipeline)表控制层
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:38
 */
@RestController
@RequestMapping("pipeline")
public class PipelineController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private PipelineService pipelineService;

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param pipeline 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<Pipeline> page, Pipeline pipeline) {
        return success(this.pipelineService.page(page, new QueryWrapper<>(pipeline)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.pipelineService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param pipeline 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody Pipeline pipeline) {
        return success(this.pipelineService.save(pipeline));
    }

    /**
     * 修改数据
     *
     * @param pipeline 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody Pipeline pipeline) {
        return success(this.pipelineService.updateById(pipeline));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.pipelineService.removeByIds(idList));
    }
}