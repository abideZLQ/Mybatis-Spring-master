package com.bear.service.impl;

import com.bear.mapper.PermissionsMapper;
import com.bear.mapper.RolesMapper;
import com.bear.model.Permissions;
import com.bear.model.Roles;
import com.bear.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RolesServiceImpl extends BaseService<Roles> implements RolesService {

    // 书写自己的特殊SQL
    @Autowired
    private RolesMapper rolesMapper;

    @Autowired
    private PermissionsMapper permissionsMapper;

    // 查询所有角色集合
    public List<String> selectAllRoles(){
        return rolesMapper.selectAllRoles();
    }


    // 根据用户id查询该用户所具有的角色
    public List<String> selectRoleByUserId(Integer id){
        return rolesMapper.selectRoleByUserId(id);
    }

    // 新增编辑角色信息
    public Integer saveRoleInfo(Roles roles, List<String> permissionsIdList){
        // 用来存放多个结果的集合
        List<Integer> resList = new ArrayList<Integer>();
        // 1、新增角色
        if( roles.getId() == null ){
            roles.setAvailable(1);
            // 1)保存角色基本信息
            resList.add( mapper.insert(roles) );
            // 判断此角色是否有权限集合
            if( permissionsIdList!=null && permissionsIdList.size()>0 ){
                // 2)保存角色权限的中间表关系
                resList.add( saveRolesAndPermissions(roles.getId(),permissionsIdList) );
            }
        // 2、编辑角色
        }else{
            // 1)修改角色基本信息
            resList.add( mapper.updateByPrimaryKeySelective(roles) );
            // 判断此角色是否有权限集合
            if( permissionsIdList!=null && permissionsIdList.size()>0 ){
                // 2)修改角色权限的中间表关系
                // 步骤：(1)删除角色权限的中间表关系 (2)保存角色权限的中间表关系
                resList.add( updateRolesAndPermissions(roles.getId(),permissionsIdList) );
            }
        }

        // 将所有修改的结果放入集合中,然后用集合特性来判断是否有修改失败的操作
        boolean flag = resList.contains(0);
        if(flag){
            // flag = true , 有不成功的操作
            return 0;
        }else{
            return 1;
        }
    }

    // 保存角色权限的中间表关系
    private Integer saveRolesAndPermissions(Integer id,List<String> permissionsIdList){
        // 用来存放多个结果的集合
        List<Integer> resList = new ArrayList<Integer>();
        // 查询Map
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("rolesId",id);
        for(String permissionsId : permissionsIdList){
            map.put("permissionsId",permissionsId);
            resList.add( rolesMapper.saveRolesAndPermissions(map) );
        }
        // 将所有修改的结果放入集合中,然后用集合特性来判断是否有修改失败的操作
        boolean flag = resList.contains(0);
        if(flag){
            // flag = true , 有不成功的操作
            return 0;
        }else{
            return 1;
        }
    }

    // 修改角色权限的中间表关系
    // 步骤：1、删除角色权限的中间表关系 2、保存角色权限的中间表关系
    private Integer updateRolesAndPermissions(Integer id,List<String> permissionsIdList){
        // 用来存放多个结果的集合
        List<Integer> resList = new ArrayList<Integer>();
        // 查询Map
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("rolesId",id);
        // 1、删除角色权限的中间表关系(返回值是删除的条数)
        Integer res = rolesMapper.deleteRolesAndPermissions(id);
        for(String permissionsId : permissionsIdList){
            map.put("permissionsId",permissionsId);
            // 错误写法：resList.add( rolesMapper.updateRolesAndPermissions(map) );
            // 2、保存角色权限的中间表关系
            resList.add( rolesMapper.saveRolesAndPermissions(map) );
        }
        // 将所有修改的结果放入集合中,然后用集合特性来判断是否有修改失败的操作
        boolean flag = resList.contains(0);
        if(flag){
            // flag = true , 有不成功的操作
            return 0;
        }else{
            return 1;
        }
    }

    // 获取角色权限树
    // 1.内存维护树结构 2.将内存树存入zTree对象,参数roleid是当前角色ID,用于查询此角色的权限集合并回显
    public List<Map<String,Object>> getRoleMenuTree(Integer roleid){
        Permissions permissions = new Permissions();
        // 获取未被删除的权限 1 可用 0 不可用
        permissions.setAvailable(1);
        List<Permissions> permissionsList = permissionsMapper.select(permissions);
        // 1、内存维护树结构
        permissionsList = memoryDefendTree(permissionsList);
        // 2、将内存树存入zTree对象,参数roleid是当前角色ID,用于查询此角色的权限集合并回显
        List<Map<String,Object>> zTreeJsonList = memoryTreeTransZtree(permissionsList,roleid);
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
//    private List<Map<String,Object>> memoryTreeTransZtree(List<Permissions> permissionsList, Integer roleid){
//        // 查询此角色的权限集合
//        List<Integer> idList = rolesMapper.selectRolesOfPermissionsList(roleid);
//
//
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
//            // if(parentid != null && permissions.getId() == parentid){
//            //     jsonMap.put("checked",true);
//            // }
//            // 判断当前角色的权限集合,并在角色权限树上进行回显操作
//            if( idList!=null && idList.size()>0 && idList.contains(permissions.getId()) ){
//                jsonMap.put("checked",true);
//            }
//
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
//                    // if(parentid != null && permissionsSon.getId() == parentid){
//                    //     jsonMap2.put("checked",true);
//                    // }
//                    // 判断当前角色的权限集合,并在角色权限树上进行回显操作
//                    if( idList!=null && idList.size()>0 && idList.contains(permissionsSon.getId()) ){
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
    private List<Map<String,Object>> memoryTreeTransZtree(List<Permissions> permissionsList, Integer roleid){
        // 查询此角色的权限集合
        List<Integer> idList = rolesMapper.selectRolesOfPermissionsList(roleid);


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
            // if(parentid != null && permissions.getId() == parentid){
            //     jsonMap.put("checked",true);
            // }
            // 判断当前角色的权限集合,并在角色权限树上进行回显操作
            if( idList!=null && idList.size()>0 && idList.contains(permissions.getId()) ){
                jsonMap.put("checked",true);
            }

            // 子类集合递归
            jsonMap = sonListRecursion( jsonMap, permissions.getPermissionsList(), idList  );

            // 将父类JSON存入zTree对象List集合
            jsonList.add(jsonMap);
        }
        System.out.println(jsonList);
        return jsonList;
    }

    // 子类集合递归
    private Map<String, Object> sonListRecursion(Map<String, Object> jsonMap, List<Permissions> permissionsList, List<Integer> idList){
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
                // if(parentid != null && permissionsSon.getId() == parentid){
                //     jsonMap2.put("checked",true);
                // }
                // 判断当前角色的权限集合,并在角色权限树上进行回显操作
                if( idList!=null && idList.size()>0 && idList.contains(permissionsSon.getId()) ){
                    jsonMap2.put("checked",true);
                }

                // 子类集合递归
                sonListRecursion( jsonMap2, permissionsSon.getPermissionsList(), idList  );

                jsonList2.add(jsonMap2);
            }
            // 将子类JSON集合放入父类JSON中
            jsonMap.put("children",jsonList2);
        }
        return jsonMap;
    }
}
