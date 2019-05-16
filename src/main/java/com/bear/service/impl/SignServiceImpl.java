package com.bear.service.impl;

import com.bear.mapper.SignMapper;
import com.bear.model.Sign;
import com.bear.model.Users;
import com.bear.service.SignService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SignServiceImpl extends BaseService<Sign> implements SignService{
    @Autowired
    private SignMapper signMapper;

    // 获取班级签到信息列表
    public PageInfo<Sign> selectAllSign(Sign sign, int page, int limit){
        PageHelper.startPage(page,limit);
        // select(T entity)查询条件 - 查询未被删除的用户
        List<Sign> signList = signMapper.selectAllSign(sign);
        PageInfo<Sign> pageInfo = new PageInfo<Sign>(signList);
        return pageInfo;
    }
}
