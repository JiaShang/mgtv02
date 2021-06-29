package com.shang.mgtv02.service;

import com.shang.mgtv02.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    /**
     * 获取用户总量
     * @return
     */
    String getAllUsers();

    /**
     * 获取全部用户
     * @return
     */
    List<Map<String, Object>> findAll();

    /**
     * 根据name获取用户
     * @param name
     * @return
     */
    User getByName(String name);

    /**
     * 增加用户
     * @return
     */
    int addUser(User user);

    /**
     * 根据name删除用户
     * @param user
     * @return
     */
    int deleteUser(User user);

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    int updateUser(User user);

    /**
     * 判断是否存在该用户
     * @param name
     * @return
     */
    int isHasUser(String name);
}
