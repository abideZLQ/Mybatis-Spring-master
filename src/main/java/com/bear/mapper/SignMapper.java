package com.bear.mapper;

import com.bear.model.Sign;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SignMapper extends Mapper<Sign> {
    // 获取班级签到信息列表
    List<Sign> selectAllSign(Sign sign);
}