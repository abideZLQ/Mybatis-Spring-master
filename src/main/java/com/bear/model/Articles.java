package com.bear.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "articles")
public class Articles {
    /**
     * 文章表ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userid;

    /**
     * 标题
     */
    private String title;

    /**
     * 创建时间
     */
    private Date articledate;

    /**
     * markdown代码
     */
    private String content;

    /**
     * markdown的html代码
     */
    private String contenthtml;

    /**
     * 获取文章表ID
     *
     * @return id - 文章表ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置文章表ID
     *
     * @param id 文章表ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取用户ID
     *
     * @return userid - 用户ID
     */
    public Integer getUserid() {
        return userid;
    }

    /**
     * 设置用户ID
     *
     * @param userid 用户ID
     */
    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    /**
     * 获取标题
     *
     * @return title - 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取创建时间
     *
     * @return articledate - 创建时间
     */
    public Date getArticledate() {
        return articledate;
    }

    /**
     * 设置创建时间
     *
     * @param articledate 创建时间
     */
    public void setArticledate(Date articledate) {
        this.articledate = articledate;
    }

    /**
     * 获取markdown代码
     *
     * @return content - markdown代码
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置markdown代码
     *
     * @param content markdown代码
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取markdown的html代码
     *
     * @return contenthtml - markdown的html代码
     */
    public String getContenthtml() {
        return contenthtml;
    }

    /**
     * 设置markdown的html代码
     *
     * @param contenthtml markdown的html代码
     */
    public void setContenthtml(String contenthtml) {
        this.contenthtml = contenthtml;
    }
}