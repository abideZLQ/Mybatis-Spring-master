package com.bear.service;

import com.bear.model.Articles;
import com.github.pagehelper.PageInfo;

public interface ArticlesService extends IService<Articles> {
    // 添加模糊查询的pageHelper分页,和通用mapper中的普通pageHelper分页区分开
    PageInfo<Articles> selectByPageArticles(Articles articles, Integer page, Integer limit);
}
