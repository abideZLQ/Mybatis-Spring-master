package com.bear.service;

import com.bear.model.Permissions;

import java.util.List;
import java.util.Map;

public interface PermissionsService extends IService<Permissions> {
    // 根据用户id查询该用户所具有的权限
    List<String> selectPermissionByUserId(Integer id);

    // 获取权限树
    List<Map<String,Object>> getMenuTree(Integer parentid);
}
