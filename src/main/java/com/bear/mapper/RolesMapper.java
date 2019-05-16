package com.bear.mapper;

import com.bear.model.Roles;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface RolesMapper extends Mapper<Roles> {
    // 查询所有角色集合
    List<String> selectAllRoles();

    // 根据用户id查询该用户所具有的角色
    List<String> selectRoleByUserId(Integer id);

    // 保存角色权限的中间表关系
    Integer saveRolesAndPermissions(Map<String,Object> map);

    // 删除角色权限的中间表关系(返回值是删除的条数)
    Integer deleteRolesAndPermissions(Integer roleid);

    // 查询此角色的权限集合
    List<Integer> selectRolesOfPermissionsList(Integer roleid);


}