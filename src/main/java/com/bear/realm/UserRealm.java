package com.bear.realm;

import com.bear.model.Users;
import com.bear.service.PermissionsService;
import com.bear.service.RolesService;
import com.bear.service.UsersService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UsersService usersService;
    @Autowired
    private RolesService rolesService;
    @Autowired
    private PermissionsService permissionsService;

    //认证操作
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //从token中获取登录的用户名， 查询数据库返回用户信息
        String username = (String) token.getPrincipal();
        Users users = new Users();
        users.setUsername(username);
        users = usersService.selectOne(users);

        if(users == null){
            return null;
        }
        // 1、用户对象 2、用户密码（加密） 3、盐 4、realm名称
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(users, users.getPassword(),
                ByteSource.Util.bytes(users.getUsername()),
                getName());
        return info;
    }



    @Override
    public String getName() {
        return "UserRealm";
    }

    //授权操作
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        Users users = (Users) principals.getPrimaryPrincipal();

        List<String> permissions = new ArrayList<String>();
        List<String> roles = new ArrayList<String>();

        if("admin".equals(users.getUsername())){
            //拥有所有权限
            permissions.add("*:*");
            //查询所有角色
            roles = rolesService.selectAllRoles();
        }else{
            //根据用户id查询该用户所具有的角色
            roles = rolesService.selectRoleByUserId(users.getId());
            //根据用户id查询该用户所具有的权限
            permissions = permissionsService.selectPermissionByUserId(users.getId());
        }

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(permissions);
        info.addRoles(roles);
        return info;
    }

    //清除缓存
    public void clearCached() {
        //获取当前等的用户凭证，然后清除
        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
        super.clearCache(principals);
    }


}
