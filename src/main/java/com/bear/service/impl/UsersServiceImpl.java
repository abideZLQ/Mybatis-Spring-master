package com.bear.service.impl;

import com.bear.mapper.RolesMapper;
import com.bear.mapper.UsersMapper;
import com.bear.model.Users;
import com.bear.service.UsersService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UsersServiceImpl extends BaseService<Users> implements UsersService {

    @Autowired
    private UsersMapper usersMapper;

    // 添加用户和角色的关系表
    public Integer saveUserRole(Integer userId, Integer roleId){
        Map<String,Integer> map = new HashMap<String, Integer>();
        map.put("userId",userId);
        map.put("roleId",roleId);
        Integer res = usersMapper.saveUserRole(map);
        return res;
    }

    // 修改用户和角色的关系表
    public Integer updateUserRole(Integer userId, Integer roleId){
        Map<String,Integer> map = new HashMap<String, Integer>();
        map.put("userId",userId);
        map.put("roleId",roleId);
        Integer res = usersMapper.updateUserRole(map);
        return res;
    }

    // 根据用户ID去用户角色中间表查询用户的角色
    public Integer selectRoleByUserId(Integer userId){
        Integer role = usersMapper.selectRoleByUserId(userId);
        return role;
    }
}
