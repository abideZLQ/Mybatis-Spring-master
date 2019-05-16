package com.bear.service.impl;

import com.bear.mapper.PermissionsMapper;
import com.bear.model.Permissions;
import com.bear.service.PermissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PermissionsServiceImpl extends BaseService<Permissions> implements PermissionsService {

    // 书写自己的特殊SQL
    @Autowired
    private PermissionsMapper permissionsMapper;

    // 根据用户id查询该用户所具有的权限
    public List<String> selectPermissionByUserId(Integer id){
        return permissionsMapper.selectPermissionByUserId(id);
    }

    // 获取权限树
    // 1、内存维护树结构 2、将内存树存入zTree对象,参数parentid是当前权限父ID,用于回显
    public List<Map<String,Object>> getMenuTree(Integer parentid){
        Permissions permissions = new Permissions();
        // 获取type为1(菜单)的权限集合
        permissions.setType("1");
        // 获取未被删除的权限 1 可用 0 不可用
        permissions.setAvailable(1);
        List<Permissions> permissionsList = mapper.select(permissions);
        // 1、内存维护树结构
        permissionsList = memoryDefendTree(permissionsList);
        // 2、将内存树存入zTree对象,参数parentid是当前权限父ID,用于回显
        List<Map<String,Object>> zTreeJsonList = memoryTreeTransZtree(permissionsList,parentid);
        return zTreeJsonList;
    }

    // 内存维护树结构

    /** List中保存最上层对象,最上层对象的permissionsList保存着子对象..
     * [
     *   Perssions最上层对象1:id,name,parentId,permissionsList
     *   Perssions最上层对象2:id,name,parentId,permissionsList
     *   Perssions最上层对象3:id,name,parentId,permissionsList
     * ]
     */
    private List<Permissions> memoryDefendTree(List<Permissions> emps) {
        // 查询所有的员工数据.
        // List<Employee> emps = session.selectList("selectAllEmps");


        // 集合中保存最终的结果, 每个数据对象都是当前查询结果中的根节点数据.
        List<Permissions> roots = new ArrayList<Permissions>();

        // 定义Map集合.保存查询结果. key是员工的ID, value是对应的员工对象.
        Map<Integer, Permissions> workerMap = new HashMap<Integer, Permissions>();

        // 将查询结果转存到Map集合中
        for (Permissions emp : emps) {
            workerMap.put(emp.getId(), emp);
        }

        // 开始循环Map集合. 每次循环得到的对象,作为下属员工,找直属领导
        for (Map.Entry<Integer, Permissions> entry : workerMap.entrySet()) {
            Integer id = entry.getKey();
            Permissions emp = entry.getValue();
            // 查找领导
            Integer mgrId = emp.getParentid(); // 领导的主键数据
            Permissions manager = workerMap.get(mgrId); // 领导对象.
            if (manager == null) {
                // 当前员工没有直属领导. 代表是根节点.
                roots.add(emp);
                // 进入下次循环.
                continue;
            }
            // 当前员工不是根节点. 维护领导和下属员工的关系. 注解空指针异常问题.
            emp.setManager(manager);
            if (manager.getPermissionsList() == null) {
                manager.setPermissionsList(new ArrayList<Permissions>());
            }
            manager.getPermissionsList().add(emp);
        }

        System.out.println(roots);
        return roots;
    }

//    // 将内存树存入zTree对象,参数parentid是当前权限父ID,用于回显
//    private List<Map<String,Object>> memoryTreeTransZtree(List<Permissions> permissionsList, Integer parentid){
//        // zTree对象List集合
//        List<Map<String,Object>> jsonList = new ArrayList<Map<String,Object>>();
//        for(Permissions permissions : permissionsList){
//            // 父类JSON
//            Map<String, Object> jsonMap = new HashMap<String, Object>();
//            jsonMap.put("id", permissions.getId());
//            jsonMap.put("pId",permissions.getParentid());
//            jsonMap.put("name",permissions.getName());
//            jsonMap.put("open",true);
//            // 判断当前权限,并在权限树上进行回显操作
//            if(parentid != null && permissions.getId() == parentid){
//                jsonMap.put("checked",true);
//            }
//            // 判断是否有子类
//            if(permissions.getPermissionsList()!=null && permissions.getPermissionsList().size()>0){
//                // 子类JSON集合
//                List<Map<String,Object>> jsonList2 = new ArrayList<Map<String,Object>>();
//                for(Permissions permissionsSon : permissions.getPermissionsList()){
//                    Map<String, Object> jsonMap2 = new HashMap<String, Object>();
//                    jsonMap2.put("id", permissionsSon.getId());
//                    jsonMap2.put("pId",permissionsSon.getParentid());
//                    jsonMap2.put("name",permissionsSon.getName());
//                    // 判断当前权限,并在权限树上进行回显操作
//                    if(parentid != null && permissionsSon.getId() == parentid){
//                        jsonMap2.put("checked",true);
//                    }
//                    jsonList2.add(jsonMap2);
//                }
//                // 将子类JSON集合放入父类JSON中
//                jsonMap.put("children",jsonList2);
//            }
//            // 将父类JSON存入zTree对象List集合
//            jsonList.add(jsonMap);
//        }
//        System.out.println(jsonList);
//        return jsonList;
//    }

    // 将内存树存入zTree对象,参数parentid是当前权限父ID,用于回显
    private List<Map<String,Object>> memoryTreeTransZtree(List<Permissions> permissionsList, Integer parentid){
        // zTree对象List集合
        List<Map<String,Object>> jsonList = new ArrayList<Map<String,Object>>();
        for(Permissions permissions : permissionsList){
            // 父类JSON
            Map<String, Object> jsonMap = new HashMap<String, Object>();
            jsonMap.put("id", permissions.getId());
            jsonMap.put("pId",permissions.getParentid());
            jsonMap.put("name",permissions.getName());
            jsonMap.put("open",true);
            // 判断当前权限,并在权限树上进行回显操作
            if(parentid != null && permissions.getId() == parentid){
                jsonMap.put("checked",true);
            }
            // 子类集合递归
            jsonMap = sonListRecursion( jsonMap, permissions.getPermissionsList(),parentid  );

            // 将父类JSON存入zTree对象List集合
            jsonList.add(jsonMap);
        }
        System.out.println(jsonList);
        return jsonList;
    }


    // 子类集合递归
    private Map<String, Object> sonListRecursion(Map<String, Object> jsonMap, List<Permissions> permissionsList, Integer parentid){
        // 判断是否有子类
        if( permissionsList!=null && permissionsList.size()>0 ){
            // 子类JSON集合
            List<Map<String,Object>> jsonList2 = new ArrayList<Map<String,Object>>();
            for( Permissions permissionsSon : permissionsList ){
                Map<String, Object> jsonMap2 = new HashMap<String, Object>();
                jsonMap2.put("id", permissionsSon.getId());
                jsonMap2.put("pId",permissionsSon.getParentid());
                jsonMap2.put("name",permissionsSon.getName());
                // 判断当前权限,并在权限树上进行回显操作
                if(parentid != null && permissionsSon.getId() == parentid){
                    jsonMap2.put("checked",true);
                }
                // 子类集合递归
                sonListRecursion( jsonMap2, permissionsSon.getPermissionsList(),parentid  );
                jsonList2.add(jsonMap2);
            }
            // 将子类JSON集合放入父类JSON中
            jsonMap.put("children",jsonList2);
        }
        return jsonMap;
    }
}
