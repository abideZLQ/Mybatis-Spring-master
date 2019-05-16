package com.bear.mapper;

import com.bear.model.Permissions;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PermissionsMapper extends Mapper<Permissions> {
    // 根据用户id查询该用户所具有的权限
    List<String> selectPermissionByUserId(Integer id);
}