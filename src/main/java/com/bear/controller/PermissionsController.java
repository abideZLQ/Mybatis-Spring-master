package com.bear.controller;

import com.bear.model.BaseReturn;
import com.bear.model.Permissions;
import com.bear.service.PermissionsService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("/permission")
public class PermissionsController {

    @Autowired
    private PermissionsService permissionsService;

    /**
     * 跳转权限列表页面
     * @return
     */
    @RequestMapping(value="/jumpPermissionList")
    public String jumpPermissionList(){
        return "permission/permissionList";
    }

    /**
     * 获取权限列表
     * @return
     */
    @RequestMapping(value="/getPermissionList")
    @ResponseBody
    public Object getPermissionList(Permissions permissions,
                              @RequestParam(required = false, defaultValue = "1") int page,
                              @RequestParam(required = false, defaultValue = "10") int limit ) {
        // 没有pageHelper的处理方式：计算偏移量
        // page = limit * (page - 1);
        // 存在pageHelper的处理方式：不计算偏移量
        // select(T entity)动态查询条件 - 查询未被删除的用户
        permissions.setAvailable(1);
        PageInfo<Permissions> pageInfo = permissionsService.selectByPage(permissions,page,limit);
        // 将Layui-table返回json数据封装为对象
        BaseReturn baseReturn = new BaseReturn();
        baseReturn.setCode("0");
        baseReturn.setMsg("");
        baseReturn.setCount(pageInfo.getTotal());
        baseReturn.setData(pageInfo.getList());
        return baseReturn;
    }

    /**
     * 跳转权限页面
     * @param permissions
     * @param model
     * @return
     */
    @RequestMapping(value="/jumpPermissionInfo")
    public String jumpPermissionInfo(Permissions permissions, Model model){
        if(permissions.getId() != 0){
            permissions = permissionsService.selectOne(permissions);
        }else{
            permissions.setId(null);
        }
        model.addAttribute("permissions",permissions);
        return "permission/permissionInfo";
    }

    /**
     * 获取权限树
     * @param parentid 当前权限父ID
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getMenuTree")
    public Object getMenuTree(Integer parentid){
        // 1.内存维护树结构 2.将内存树存入zTree对象,参数parentid是当前权限父ID,用于回显
        List<Map<String,Object>> zTreeJsonList = permissionsService.getMenuTree(parentid);
        return zTreeJsonList;
    }

    /**
     * 新增编辑权限信息
     * @param permissions
     * @return
     */
    @RequestMapping(value="/savePermissionInfo")
    @ResponseBody
    public Object savePermissionInfo(Permissions permissions){
        Map<String,Object> jsonMap = new HashMap<String, Object>();
        Integer res  = null;
        if(permissions.getId() == null){
            permissions.setAvailable(1);
            permissions.setCreatetime(new Date());
            // 虽然不给最上层权限添加父ID也可以(因为null和0都在最上层),但是为了规范,在新增权限的时候如果没有parentid,就将parentid设置为0
            if( permissions.getParentid() == null ){
                permissions.setParentid(0);
            }
            res = permissionsService.save(permissions);
        }else{
            // 修改权限表中不为null的字段
            res = permissionsService.updateNotNull(permissions);
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
     * 删除/批量删除权限信息
     * @param delUserIdArray
     * @return
     */
    @RequestMapping(value="/delPermissionInfo")
    @ResponseBody
    public Object delPermissionInfo(Integer[] delUserIdArray){
        Map<String,Object> jsonMap = new HashMap<String, Object>();
        // 数组转集合
        List<Integer> delUserIdList = Arrays.asList(delUserIdArray);
        // 用来存放多个结果的集合
        List<Integer> resList = new ArrayList<Integer>();
        Permissions permissions = new Permissions();
        for (Integer id : delUserIdList) {
            permissions.setId(id);
            permissions.setAvailable(0);
            // 伪删除
            resList.add(permissionsService.updateNotNull(permissions));
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
