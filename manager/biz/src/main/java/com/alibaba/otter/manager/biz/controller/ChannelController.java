package com.alibaba.otter.manager.biz.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.alibaba.otter.manager.biz.entity.Channel;
import com.alibaba.otter.manager.biz.service.ChannelService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (Channel)表控制层
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:38
 */
@RestController
@RequestMapping("channel")
public class ChannelController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private ChannelService channelService;

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param channel 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<Channel> page, Channel channel) {
        return success(this.channelService.page(page, new QueryWrapper<>(channel)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.channelService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param channel 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody Channel channel) {
        return success(this.channelService.save(channel));
    }

    /**
     * 修改数据
     *
     * @param channel 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody Channel channel) {
        return success(this.channelService.updateById(channel));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.channelService.removeByIds(idList));
    }
}