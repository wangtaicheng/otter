package com.alibaba.otter.manager.biz.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.alibaba.otter.manager.biz.entity.DataMediaSource;
import com.alibaba.otter.manager.biz.service.DataMediaSourceService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (DataMediaSource)表控制层
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:38
 */
@RestController
@RequestMapping("dataMediaSource")
public class DataMediaSourceController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private DataMediaSourceService dataMediaSourceService;

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param dataMediaSource 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<DataMediaSource> page, DataMediaSource dataMediaSource) {
        return success(this.dataMediaSourceService.page(page, new QueryWrapper<>(dataMediaSource)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.dataMediaSourceService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param dataMediaSource 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody DataMediaSource dataMediaSource) {
        return success(this.dataMediaSourceService.save(dataMediaSource));
    }

    /**
     * 修改数据
     *
     * @param dataMediaSource 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody DataMediaSource dataMediaSource) {
        return success(this.dataMediaSourceService.updateById(dataMediaSource));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.dataMediaSourceService.removeByIds(idList));
    }
}