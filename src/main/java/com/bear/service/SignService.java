package com.bear.service;

import com.bear.model.Sign;
import com.github.pagehelper.PageInfo;


public interface SignService extends IService<Sign>{
    // 获取班级签到信息列表
    PageInfo<Sign> selectAllSign(Sign sign, int page, int limit);
}
