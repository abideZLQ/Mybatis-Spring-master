package com.bear.controller;

import com.bear.model.Articles;
import com.bear.model.Users;
import com.bear.service.ArticlesService;
import com.bear.util.FileUtil;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/article")
public class ArticlesController {

    @Autowired
    private ArticlesService articlesService;


    /**
     * 跳转markdown文章信息页面(新增和修改)
     * @return
     */
    @RequestMapping(value="/jumpMdInfo")
    public String jumpMdInfo(Model model, Articles articles){
        // 根据ID查询有无文章对象
        // 根据ID查询有无文章对象不要用selectOne,用selectOne(动态条件)查询，当id=null，SQL会变成：select * from table
        // articles = articlesService.selectOne(articles);
        articles = articlesService.selectByKey(articles.getId());
        model.addAttribute("articles",articles);
        return "article/mdInfo";
    }


    /**
     * 新增编辑markdown信息
     * @param model
     * @param articles
     * @param contentHtml
     * @param content
     * @return
     */
    @RequestMapping(value="/saveMdInfo")
    @ResponseBody
    public Object saveMdInfo(Model model,Articles articles,
                             @RequestParam("content-editormd-html-code") String contentHtml,
                             @RequestParam("content-editormd-markdown-doc") String content){
        Map<String,Object> jsonMap = new HashMap<String,Object>();
        Integer res = null;
        // 填入MD和HTML信息
        articles.setContent(content);
        articles.setContenthtml(contentHtml);
        if(articles.getId() == null){
            // 从shiro的session中取出我们保存的对象，该对象在登录认证成功后保存的
            Users users = (Users) SecurityUtils.getSubject().getPrincipal();
            articles.setUserid(users.getId());
            articles.setArticledate(new Date());
            res = articlesService.save(articles);
        }else{
            res = articlesService.updateNotNull(articles);
        }
        if(res > 0){
            jsonMap.put("success",true);
            jsonMap.put("info","操作成功");
        }else{
            jsonMap.put("success",false);
            jsonMap.put("info","操作失败");
        }
        return jsonMap;
    }

    /**
     * 保存markdown上传图片
     * @param file
     * @return
     */
    @RequestMapping(value="/saveMdImg")
    @ResponseBody
    public Object saveMdImg(@RequestParam(value = "editormd-image-file") MultipartFile file){
        JSONObject res = new JSONObject();
        try{
            // 上传文件
            Map<String,Object> uploadMap = FileUtil.upload(file);
            if( (Boolean) uploadMap.get("success") ){
                res.put("success", 1);
                res.put("message", "上传成功");
                res.put("url", uploadMap.get("url"));
            }else {
                res.put("success", 0);
                res.put("message", "上传失败");
            }
        }catch(Exception e){
            System.out.println("上传图片异常" + e);
            res.put("success", 0);
            res.put("message", "上传异常");
        }

        return res.toString();
    }


    /**
     * 跳转markdown文章展示页面(详情页)
     * @param model
     * @param articles
     * @return
     */
    @RequestMapping(value="/jumpMdView")
    public String jumpMdView(Model model, Articles articles, HttpSession session){
//        // 根据ID查询有无文章对象
//        // 根据ID查询有无文章对象不要用selectOne,用selectOne(动态条件)查询，当id=null，SQL会变成：select * from table
//        // articles = articlesService.selectOne(articles);
//        articles = articlesService.selectByKey(articles.getId());
//        model.addAttribute("articles",articles);
//        return "article/mdView2";

        // 从shiro的session中取出我们保存的对象，该对象在登录认证成功后保存的
        Users users = (Users) SecurityUtils.getSubject().getPrincipal();
        articles = articlesService.selectByKey(articles.getId());
        model.addAttribute("articles",articles);
        session.setAttribute("sessionUsersId",users.getId());
        return "article/mdView3copy2";
    }


    /**
     * 跳转md文章列表页面
     * @return
     */
    @RequestMapping(value="/jumpMdList")
    public String jumpMdList(){
        return "article/mdList";
    }

    /**
     * 获取md文章列表
     * @return
     */
    @RequestMapping(value="/getMdList")
    @ResponseBody
    public Object getMdList(Articles articles,
                            @RequestParam(required = false, defaultValue = "1") int page,
                            @RequestParam(required = false, defaultValue = "10") int limit ) {
        // 没有pageHelper的处理方式：计算偏移量
        // page = limit * (page - 1);
        // 存在pageHelper的处理方式：不计算偏移量
        // PageInfo<Articles> pageInfo = articlesService.selectByPage(articles,page,limit);
        // 添加模糊查询的pageHelper分页,和通用mapper中的普通pageHelper分页区分开
        PageInfo<Articles> pageInfo = articlesService.selectByPageArticles(articles,page,limit);
        return pageInfo;
    }


    /**
     * 删除markdown文章信息
     * @param articles
     * @return
     */
    @RequestMapping(value="/delMdInfo")
    @ResponseBody
    public Object delMdInfo(Articles articles){
        Map<String,Object> jsonMap = new HashMap<String, Object>();
        Integer res = articlesService.delete(articles.getId());
        if(res > 0){
            jsonMap.put("success",true);
            jsonMap.put("info","删除成功");
        }else{
            jsonMap.put("success",false);
            jsonMap.put("info","删除失败");
        }
        return jsonMap;
    }

}
