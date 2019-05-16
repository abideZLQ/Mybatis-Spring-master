package com.bear.controller;

import com.bear.model.BaseReturn;
import com.bear.model.Roles;
import com.bear.service.RolesService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

@Controller
@RequestMapping(value="/role")
public class RolesController {

    @Autowired
    private RolesService rolesService;

    /**
     * 跳转角色列表页面
     * @return
     */
    @RequestMapping(value="/jumpRoleList")
    public String jumpRoleList(){
        return "role/roleList";
    }

    /**
     * 获取角色列表
     * @return
     */
    @RequestMapping(value="/getRoleList")
    @ResponseBody
    public Object getRoleList(Roles roles,
                              @RequestParam(required = false, defaultValue = "1") int page,
                              @RequestParam(required = false, defaultValue = "10") int limit ) {
        // 没有pageHelper的处理方式：计算偏移量
        // page = limit * (page - 1);
        // 存在pageHelper的处理方式：不计算偏移量
        // select(T entity)动态查询条件 - 查询未被删除的用户
        roles.setAvailable(1);
        PageInfo<Roles> pageInfo = rolesService.selectByPage(roles,page,limit);
        // 将Layui-table返回json数据封装为对象
        BaseReturn baseReturn = new BaseReturn();
        baseReturn.setCode("0");
        baseReturn.setMsg("");
        baseReturn.setCount(pageInfo.getTotal());
        baseReturn.setData(pageInfo.getList());
        return baseReturn;
    }

    /**
     * 跳转用户角色页面
     * @param roles
     * @param model
     * @return
     */
    @RequestMapping(value="/jumpRoleInfo")
    public String jumpRoleInfo(Roles roles, Model model){
        if(roles.getId() != 0){
            roles = rolesService.selectOne(roles);
        }else{
            roles.setId(null);
        }
        model.addAttribute("roles",roles);
        return "role/roleInfo";
    }

    /**
     * 获取角色权限树
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getRoleMenuTree")
    public Object getRoleMenuTree(Integer roleid){
        // 1.内存维护树结构 2.将内存树存入zTree对象,参数roleid是当前角色ID,用于回显
        // List<Map<String,Object>> zTreeJsonList = permissionsService.getMenuTree(parentid);
        List<Map<String,Object>> zTreeJsonList = rolesService.getRoleMenuTree(roleid);
        return zTreeJsonList;
    }


    /**
     * 新增编辑角色信息
     * @param roles 角色
     * @return
     */
    @RequestMapping(value="/saveRoleInfo")
    @ResponseBody
    public Object saveRoleInfo(Roles roles, String idList){
        String[] idArray = idList.split(",");
        List<String> permissionsIdList = Arrays.asList(idArray);
        Map<String,Object> jsonMap = new HashMap<String, Object>();
        // 新增编辑角色信息
        // 1、新增角色
        // 1)保存角色基本信息
        // 2)保存角色权限的中间表关系
        // 2、编辑角色
        // 1)修改角色基本信息
        // 2)修改角色权限的中间表关系
        // 步骤：(1)删除角色权限的中间表关系 (2)保存角色权限的中间表关系
        Integer res = rolesService.saveRoleInfo(roles,permissionsIdList);
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
     * 删除/批量删除角色信息(伪删除)
     * @param delUserIdArray 角色ID集合
     * @return
     */
    @RequestMapping(value="/delRoleInfo")
    @ResponseBody
    public Object delRoleInfo(Integer[] delUserIdArray){
        Map<String,Object> jsonMap = new HashMap<String, Object>();

        // 数组转集合
        List<Integer> delUserIdList = Arrays.asList(delUserIdArray);

        // 用来存放多个结果的集合
        List<Integer> resList = new ArrayList<Integer>();
        Roles roles = new Roles();
        for (Integer id : delUserIdList) {
            roles.setId(id);
            roles.setAvailable(0);
            // 伪删除
            resList.add(rolesService.updateNotNull(roles));
        }
        // 将所有修改的结果放入集合中,然后用集合特性来判断是否有修改失败的操作
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
}
