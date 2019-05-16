package com.bear.service;

import com.bear.model.Depts;

import java.util.List;
import java.util.Map;

public interface DeptsService extends IService<Depts> {

    // 1.内存维护树结构 2.将内存树存入zTree对象,参数parentid是当前部门父ID,用于回显
    List<Map<String,Object>> getDeptTree(Integer parentid);
}
