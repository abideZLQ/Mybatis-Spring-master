package com.bear.mapper;

import com.bear.model.Users;
import tk.mybatis.mapper.common.Mapper;

import java.util.Map;

public interface UsersMapper extends Mapper<Users> {

    // 添加用户和角色的关系表
    Integer saveUserRole(Map<String,Integer> map);

    // 修改用户和角色的关系表
    Integer updateUserRole(Map<String,Integer> map);

    // 根据用户ID去用户角色中间表查询用户的角色
    Integer selectRoleByUserId(Integer userId);
}