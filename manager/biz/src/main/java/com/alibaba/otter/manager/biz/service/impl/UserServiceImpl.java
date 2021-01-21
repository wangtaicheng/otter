package com.alibaba.otter.manager.biz.service.impl;

import com.alibaba.otter.manager.biz.common.exceptions.ManagerException;
import com.alibaba.otter.manager.biz.common.exceptions.RepeatConfigureException;
import com.alibaba.otter.manager.biz.dao.UserMapper;
import com.alibaba.otter.manager.biz.entity.UserDO;
import com.alibaba.otter.manager.biz.service.UserService;
import com.alibaba.otter.shared.common.model.user.User;
import com.alibaba.otter.shared.common.utils.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * (User)表服务实现类
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:38
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public void createUser(User user) {
        Assert.assertNotNull(user);
        try {
            UserDO userDo = modelToDo(user);
            super.saveOrUpdate(userDo);
            if (!super.saveOrUpdate(userDo)) {
                String exceptionCause = "exist the same name user in the database.";
                logger.warn("WARN ## {}", exceptionCause);
                throw new RepeatConfigureException(exceptionCause);
            }
        } catch (RepeatConfigureException rce) {
            throw rce;
        } catch (Exception e) {
            logger.error("ERROR ## create user has an exception!");
            throw new ManagerException(e);
        }
    }

    @Override
    public void deleteUser(Long userId) {
        Assert.assertNotNull(userId);
        removeById(userId);
    }

    @Override
    public void updateUser(User user) {
        Assert.assertNotNull(user);
        Assert.assertNotNull(user.getId());
        UserDO userDo = modelToDo(user);
        updateById(userDo);

    }

    @Override
    public User findUserById(Long userId) {
        Assert.assertNotNull(userId);
        return doToModel(getById(userId));
    }

    @Override
    public User login(String name, String password) {
        UserDO userDo = new UserDO();
        userDo.setUserName(name);
        userDo.setPassword(password);
        UserDO one = getOne(new QueryWrapper<>(userDo));
        if (null == one) {
            return null;
        }
        return doToModel(one);
    }


    @Override
    public List<User> listAllUsers() {
        return list().stream().map(this::doToModel).collect(Collectors.toList());
    }

    @Override
    public List<User> listByCondition(Map condition) {
        Object searchKey = condition.get("searchKey");
        QueryWrapper<UserDO> query = Wrappers.query();
        if (StringUtils.checkValNotNull(searchKey)) {
            query.like("USERNAME", searchKey).or().like("ID", searchKey).or().like("DEPARTMENT", searchKey).or()
                 .like("REALNAME", searchKey);
        }
        return list(query).stream().map(this::doToModel).collect(Collectors.toList());
    }

    @Override
    public int getCount() {
        return count();
    }

    @Override
    public int getCount(Map condition) {
        Object searchKey = condition.get("searchKey");
        QueryWrapper<UserDO> query = Wrappers.query();
        if (StringUtils.checkValNotNull(searchKey)) {
            query.like("USERNAME", searchKey).or().like("ID", searchKey).or().like("DEPARTMENT", searchKey).or()
                 .like("REALNAME", searchKey);
        }
        return count(query);
    }


    private User doToModel(UserDO userDo) {
        User user = new User();
        user.setId(userDo.getId());
        user.setName(userDo.getUserName());
        user.setDepartment(userDo.getDepartment());
        user.setRealName(userDo.getRealName());
        user.setAuthorizeType(userDo.getAuthorizeType());
        user.setGmtCreate(userDo.getGmtCreate());
        user.setGmtModified(userDo.getGmtModified());
        return user;
    }

    private UserDO modelToDo(User user) {
        UserDO userDo = new UserDO();
        userDo.setId(user.getId());
        userDo.setUserName(user.getName());
        userDo.setPassword(user.getPassword());
        userDo.setDepartment(user.getDepartment());
        userDo.setRealName(user.getRealName());
        userDo.setAuthorizeType(user.getAuthorizeType());
        userDo.setGmtCreate(user.getGmtCreate());
        userDo.setGmtModified(user.getGmtModified());
        return userDo;
    }
}
