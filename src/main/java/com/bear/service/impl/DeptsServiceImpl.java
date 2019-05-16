package com.bear.service.impl;

import com.bear.mapper.DeptsMapper;
import com.bear.model.Depts;
import com.bear.service.DeptsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DeptsServiceImpl extends BaseService<Depts> implements DeptsService {

    @Autowired
    private DeptsMapper deptsMapper;

    // 1.内存维护树结构 2.将内存树存入zTree对象,参数parentid是当前部门父ID,用于回显
    public List<Map<String,Object>> getDeptTree(Integer parentid){
        return null;
    }
}

