package com.bear.mapper;

import com.bear.model.Articles;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ArticlesMapper extends Mapper<Articles> {

    // 添加模糊查询的查询
    List<Articles> selectByPageArticles(Articles articles);
}