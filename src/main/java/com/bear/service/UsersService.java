package com.bear.service;

import com.bear.model.Users;
import com.github.pagehelper.PageInfo;

import java.util.List;


public interface UsersService extends IService<Users> {

    // 添加用户和角色的关系表
    Integer saveUserRole(Integer userId, Integer roleId);

    // 修改用户和角色的关系表
    Integer updateUserRole(Integer userId, Integer roleId);

    // 根据用户ID去用户角色中间表查询用户的角色
    Integer selectRoleByUserId(Integer userId);
}
