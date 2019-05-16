package com.bear.service;

import com.bear.model.Roles;

import java.util.List;
import java.util.Map;

public interface RolesService extends IService<Roles>{

    // 查询所有角色集合
    List<String> selectAllRoles();

    // 根据用户id查询该用户所具有的角色
    List<String> selectRoleByUserId(Integer id);

    // 新增编辑角色信息
    Integer saveRoleInfo(Roles roles, List<String> permissionsIdList);

    // 1.内存维护树结构 2.将内存树存入zTree对象,参数roleid是当前角色ID,用于回显
    List<Map<String,Object>> getRoleMenuTree(Integer roleid);

}
