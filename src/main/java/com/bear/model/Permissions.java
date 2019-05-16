package com.bear.model;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Table(name = "permissions")
public class Permissions {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 资源名称
     */
    private String name;

    /**
     * 资源标识
     */
    private String permission;

    /**
     * 父级ID
     */
    private Integer parentid;

    /**
     * 菜单的URL
     */
    private String url;

    /**
     * 类型：如button按钮 menu菜单
     */
    private String type;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 排序
     */
    private Integer priority;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 0  no  1 yes 是否可用
     */
    private Integer available;


    /**
     * 作用：下属员工集合
     * 1.使用 @Transient 注解来告诉通用 Mapper 这不是表中的字段
     * 2.对于类中的复杂对象，以及 Map,List 等属性不需要配置这个注解
     */
    @Transient
    private List<Permissions> permissionsList;

    /**
     * 作用：领导引用
     * 1.使用 @Transient 注解来告诉通用 Mapper 这不是表中的字段
     * 2.对于类中的复杂对象，以及 Map,List 等属性不需要配置这个注解
     */
    @Transient
    private Permissions manager;


    public void setPermissionsList(List<Permissions> permissionsList) {
        this.permissionsList = permissionsList;
    }
    public List<Permissions> getPermissionsList() {
        return permissionsList;
    }

    public void setManager(Permissions manager) {
        this.manager = manager;
    }
    public Permissions getManager() {
        return manager;
    }


    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取资源名称
     *
     * @return name - 资源名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置资源名称
     *
     * @param name 资源名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取资源标识
     *
     * @return permission - 资源标识
     */
    public String getPermission() {
        return permission;
    }

    /**
     * 设置资源标识
     *
     * @param permission 资源标识
     */
    public void setPermission(String permission) {
        this.permission = permission;
    }

    /**
     * 获取父级ID
     *
     * @return parentid - 父级ID
     */
    public Integer getParentid() {
        return parentid;
    }

    /**
     * 设置父级ID
     *
     * @param parentid 父级ID
     */
    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    /**
     * 获取菜单的URL
     *
     * @return url - 菜单的URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置菜单的URL
     *
     * @param url 菜单的URL
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取类型：如button按钮 menu菜单
     *
     * @return type - 类型：如button按钮 menu菜单
     */
    public String getType() {
        return type;
    }

    /**
     * 设置类型：如button按钮 menu菜单
     *
     * @param type 类型：如button按钮 menu菜单
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取菜单图标
     *
     * @return icon - 菜单图标
     */
    public String getIcon() {
        return icon;
    }

    /**
     * 设置菜单图标
     *
     * @param icon 菜单图标
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * 获取排序
     *
     * @return priority - 排序
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * 设置排序
     *
     * @param priority 排序
     */
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    /**
     * 获取创建时间
     *
     * @return createtime - 创建时间
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * 设置创建时间
     *
     * @param createtime 创建时间
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * 获取0  no  1 yes 是否可用
     *
     * @return available - 0  no  1 yes 是否可用
     */
    public Integer getAvailable() {
        return available;
    }

    /**
     * 设置0  no  1 yes 是否可用
     *
     * @param available 0  no  1 yes 是否可用
     */
    public void setAvailable(Integer available) {
        this.available = available;
    }
}