package com.alibaba.otter.manager.biz.service;

import com.alibaba.otter.manager.biz.entity.UserDO;
import com.alibaba.otter.shared.common.model.user.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * (User)表服务接口
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:38
 */
public interface UserService extends IService<UserDO> {
    void createUser(User user);

    void deleteUser(Long userId);

    void updateUser(User user);

    User findUserById(Long userId);

    User login(String name, String password);

    List<User> listAllUsers();

    List<User> listByCondition(Map condition);

    int getCount();

    int getCount(Map condition);
}
