package com.bear.service.impl;

import com.bear.mapper.ArticlesMapper;
import com.bear.model.Articles;
import com.bear.service.ArticlesService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageInfo;

import java.util.List;

@Service
public class ArticlesServiceImpl extends BaseService<Articles> implements ArticlesService {

    @Autowired
    private ArticlesMapper articlesMapper;

    // 添加模糊查询的pageHelper分页,和通用mapper中的普通pageHelper分页区分开
    public PageInfo<Articles> selectByPageArticles(Articles articles, Integer page, Integer limit){
        PageHelper.startPage(page,limit);
        // select(T entity)动态查询条件
        List<Articles> dataList = articlesMapper.selectByPageArticles(articles);
        PageInfo<Articles> pageInfo = new PageInfo<Articles>(dataList);
        return pageInfo;
    }
}
