package com.bear.controller;

import com.bear.model.BaseReturn;
import com.bear.model.Roles;
import com.bear.model.Users;
import com.bear.service.RolesService;
import com.bear.service.UsersService;
import com.bear.util.MD5Util;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.*;


@Controller
@RequestMapping(value="/user")
public class UsersController {

    @Autowired
    private UsersService usersService;
    @Autowired
    private RolesService rolesService;


    /**
     * 跳转用户列表页面
     * @return
     */
    @RequestMapping(value="/jumpUserList")
    public String jumpUserList(){
        return "user/userList";
    }

    /**
     * 获取用户列表
     * @return
     */
    @RequestMapping(value="/getUserList")
    @ResponseBody
    public Object getUserList(Users users,
                              @RequestParam(required = false, defaultValue = "1") int page,
                              @RequestParam(required = false, defaultValue = "10") int limit ) {
        // 没有pageHelper的处理方式：计算偏移量
        // page = limit * (page - 1);
        // 存在pageHelper的处理方式：不计算偏移量
        // select(T entity)动态查询条件 - 查询未被删除的用户
        users.setAvailable(1);
        PageInfo<Users> pageInfo = usersService.selectByPage(users,page,limit);
        // 将Layui-table返回json数据封装为对象
        BaseReturn baseReturn = new BaseReturn();
        baseReturn.setCode("0");
        baseReturn.setMsg("");
        baseReturn.setCount(pageInfo.getTotal());
        baseReturn.setData(pageInfo.getList());
        return baseReturn;
    }

    /**
     * 跳转用户信息页面
     * @param users
     * @param model
     * @return
     */
    @RequestMapping(value="/jumpUserInfo")
    public String jumpUserInfo(Users users, Model model){
        if(users.getId() != 0){
            users = usersService.selectOne(users);
            model.addAttribute("addOrUpdate","update");
            // 根据用户ID去用户角色中间表查询用户的角色
            Integer role = usersService.selectRoleByUserId(users.getId());
            model.addAttribute("usersRole",role);
        }else{
            users.setId(null);
            model.addAttribute("addOrUpdate","add");
        }
        model.addAttribute("users",users);
        // 查询所有的角色集合
        Roles roles = new Roles();
        roles.setAvailable(1);
        List<Roles> rolesList = rolesService.select(roles);
        model.addAttribute("rolesList",rolesList);
        return "user/userInfo";
    }

    /**
     * 跳转个人用户信息页面
     * @param model
     * @return
     */
    @RequestMapping(value="/jumpPersonalUserInfo")
    public String jumpPersonalUserInfo(Model model){
        // 从shiro的session中取出我们保存的对象，该对象在登录认证成功后保存的
        Users users = (Users) SecurityUtils.getSubject().getPrincipal();
        // 这里要注意一定不能直接把shiroSession中的用户给页面,因为Session有缓存,所以需要拿用户的ID再去数据库中查询
        users = usersService.selectByKey(users.getId());
        model.addAttribute("personal","update");
        model.addAttribute("addOrUpdate","update");
        model.addAttribute("users",users);
        return "user/userInfo";
    }

    /**
     * 新增编辑用户信息
     * @param users 用户
     * @param role 角色
     * @return
     */
    @RequestMapping(value="/saveUserInfo")
    @ResponseBody
    public Object saveUserInfo(Users users,String role){
        Map<String,Object> jsonMap = new HashMap<String, Object>();
        Integer res ;
        if(users.getId() == null){
            // 保持原子性,将2个事务放在service中.
            // 保存用户 将密码MD5加密
            users.setPassword(MD5Util.md5Pwd(users.getPassword(),users.getUsername(),3));
            users.setAvailable(1);
            res = usersService.save(users);
            if( role!=null && !"".equals(role) && res>0 ){
                // 添加用户和角色的关系表
                res = usersService.saveUserRole(users.getId(),Integer.parseInt(role));
            }
        }else{
            // 修改用户表中不为null的字段
            res = usersService.updateNotNull(users);
            if( role!=null && !"".equals(role) && res>0 ){
                // 修改用户和角色的关系表
                res = usersService.updateUserRole(users.getId(),Integer.parseInt(role));
            }
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
     * 删除/批量删除用户信息
     * @param delUserIdArray
     * @return
     */
    @RequestMapping(value="/delUserInfo")
    @ResponseBody
    public Object delUserInfo(Integer[] delUserIdArray){
        Map<String,Object> jsonMap = new HashMap<String, Object>();
        // 数组转集合
        List<Integer> delUserIdList = Arrays.asList(delUserIdArray);
        // 用来存放多个结果的集合
        List<Integer> resList = new ArrayList<Integer>();
        Users users = new Users();
        for (Integer id : delUserIdList) {
            users.setId(id);
            users.setAvailable(0);
            resList.add(usersService.updateNotNull(users));
        }
        boolean flag = resList.contains(0);
        if(flag){
            jsonMap.put("success",false);
            jsonMap.put("info","删除失败：(失败原因 - 单个未删除或未全部删除)");
        }else{
            jsonMap.put("success",true);
            jsonMap.put("info","删除成功");
        }
        return jsonMap;
    }


    /**
     * 跳转个人用户密码修改页面
     * @param model
     * @return
     */
    @RequestMapping(value="/jumpUserPwd")
    public String jumpUserPwd(Model model){
        // 从shiro的session中取出我们保存的对象，该对象在登录认证成功后保存的
        Users users = (Users) SecurityUtils.getSubject().getPrincipal();
        model.addAttribute("users",users);
        return "user/userPwd";
    }


    /**
     * 编辑个人用户密码
     * @param users
     * @return
     */
    @RequestMapping(value="/saveUserPwd")
    @ResponseBody
    public Object saveUserPwd(Users users){
        Map<String,Object> jsonMap = new HashMap<String, Object>();
        Integer res = null;
        if(users.getId() != null){
            // 保存用户 将密码MD5加密
            users.setPassword(MD5Util.md5Pwd(users.getPassword(),users.getUsername(),3));
            res = usersService.updateNotNull(users);
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


}


