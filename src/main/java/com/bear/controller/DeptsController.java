package com.bear.controller;

import com.bear.model.BaseReturn;
import com.bear.model.Depts;
import com.bear.service.DeptsService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/dept")
public class DeptsController {

    @Autowired
    private DeptsService deptsService;

    /**
     * 跳转部门列表页面
     * @return
     */
    @RequestMapping(value="/jumpDeptList")
    public String jumpPermissionList(){
        return "dept/deptList";
    }

    /**
     * 获取部门列表
     * @return
     */
    @RequestMapping(value="/getDeptList")
    @ResponseBody
    public Object getPermissionList(Depts depts,
                                    @RequestParam(required = false, defaultValue = "1") int page,
                                    @RequestParam(required = false, defaultValue = "10") int limit ) {
        // 没有pageHelper的处理方式：计算偏移量
        // page = limit * (page - 1);
        // 存在pageHelper的处理方式：不计算偏移量
        // select(T entity)动态查询条件 - 查询未被删除的用户
        PageInfo<Depts> pageInfo = deptsService.selectByPage(depts,page,limit);
        // 将Layui-table返回json数据封装为对象
        BaseReturn baseReturn = new BaseReturn();
        baseReturn.setCode("0");
        baseReturn.setMsg("");
        baseReturn.setCount(pageInfo.getTotal());
        baseReturn.setData(pageInfo.getList());
        return baseReturn;
    }


    /**
     * 跳转部门页面
     * @param depts
     * @param model
     * @return
     */
    @RequestMapping(value="/jumpDeptInfo")
    public String jumpDeptInfo(Depts depts, Model model){
        if(depts.getId() != 0){
            depts = deptsService.selectOne(depts);
        }else{
            depts.setId(null);
        }
        model.addAttribute("depts",depts);
        return "dept/deptInfo";
    }

    /**
     * 获取部门树
     * @param parentid 当前部门父ID
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getDeptTree")
    public Object getDeptTree(Integer parentid){
        // 1.内存维护树结构 2.将内存树存入zTree对象,参数parentid是当前部门父ID,用于回显
        List<Map<String,Object>> zTreeJsonList = deptsService.getDeptTree(parentid);
        return zTreeJsonList;
    }

    /**
     * 新增编辑部门信息
     * @param depts
     * @return
     */
    @RequestMapping(value="/saveDeptInfo")
    @ResponseBody
    public Object saveDeptInfo(Depts depts){
        Map<String,Object> jsonMap = new HashMap<String, Object>();
        Integer res  = null;
        if(depts.getId() == null){
            depts.setCreatetime(new Date());
            // 虽然不给最上层权限添加父ID也可以(因为null和0都在最上层),但是为了规范,在新增权限的时候如果没有parentid,就将parentid设置为0
            if( depts.getParentid() == null ){
                depts.setParentid(0);
            }
            res = deptsService.save(depts);
        }else{
            // 修改权限表中不为null的字段
            res = deptsService.updateNotNull(depts);
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
