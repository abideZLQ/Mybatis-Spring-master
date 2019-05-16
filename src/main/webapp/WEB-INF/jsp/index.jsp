<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%--<%@ include file="/WEB-INF/views/include/taglib.jsp"%>--%>
<% request.setAttribute("webPath", request.getContextPath());%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
        <link href="${webPath}/static/layui/css/layui.css" rel="stylesheet" type="text/css"/>
        <title>政务签到系统</title>
        <script>
//            $(document).ready(function () {
//
//                if (window.parent.window != window) {
//                    window.top.location = "/login.do";
//                }
//            });
        </script>
    </head>
    <body class="layui-layout-body">
        <div class="layui-layout layui-layout-admin">
            <div class="layui-header">
                <div class="layui-logo">政务签到系统</div>

                <!-- 头部区域（可配合layui已有的水平导航） -->
                <div class="layui-nav layui-layout-left" style="color: #fff;"></div>

                <ul class="layui-nav layui-layout-right">

                    <li class="layui-nav-item">
                        <a href="javascript:;">
                            <img src="http://t.cn/RCzsdCq" class="layui-nav-img">
                            <shiro:principal property="username"></shiro:principal>
                        </a>
                        <dl class="layui-nav-child">
                            <dd><a href="${webPath}/user/jumpPersonalUserInfo.do" target="right">个人信息</a></dd>
                            <dd><a href="${webPath}/user/jumpUserPwd.do" target="right">修改密码</a></dd>
                        </dl>
                    </li>
                    <li class="layui-nav-item"><a href="${webPath}/logout">退出</a></li>
                </ul>
            </div>

            <div class="layui-side layui-bg-black">

                <div class="layui-side-scroll">
                    <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
                    <ul class="layui-nav layui-nav-tree"  lay-filter="test">
                        <li class="layui-nav-item"><a href="${webPath}/article/jumpMdList.do" target="right">文章列表</a></li>
                        <li class="layui-nav-item"><a href="${webPath}/sign/jumpSignIn.do" target="right">签到大厅</a></li>
                        <shiro:hasPermission name="menu:signList">
                            <li class="layui-nav-item"><a href="${webPath}/sign/jumpSignList.do" target="right">班级签到列表</a></li>
                        </shiro:hasPermission>
                        <li class="layui-nav-item layui-nav-itemed">
                            <a class="" href="javascript:;">用户信息</a>
                            <dl class="layui-nav-child">
                                <shiro:hasPermission name="menu:userList">
                                    <dd><a href="${webPath}/user/jumpUserList.do" target="right">系统用户列表</a></dd>
                                </shiro:hasPermission>
                                <dd><a href="${webPath}/role/jumpRoleList.do" target="right">系统角色列表</a></dd>
                                <dd><a href="${webPath}/permission/jumpPermissionList.do" target="right">系统权限列表</a></dd>
                                <dd><a href="${webPath}/dept/jumpDeptList.do" target="right">部门列表</a></dd>
                                <dd><a  href="${webPath}/user/jumpPersonalUserInfo.do" target="right">个人信息修改</a></dd>
                                <dd><a href="${webPath}/user/jumpUserPwd.do" target="right">密码修改</a></dd>
                            </dl>
                        </li>

                    </ul>
                </div>
            </div>
            <div class="layui-body">

                <div style="padding: 15px;">
                    <span class="layui-breadcrumb">
                        <a href="">首页</a>
                        <a href="">一级菜单</a>
                        <a href=""><cite>二级菜单</cite></a>
                    </span>
                    <div class="layui-tab-content" id="main">
                        <iframe name="right" id="rightMain" src="${webPath}/user/jumpPersonalUserInfo.do" frameborder="no" scrolling="auto" width="100%" height="100%" allowtransparency="true"></iframe>
                    </div>
                </div>
            </div>

            <div class="layui-footer">
                <!-- 底部固定区域 -->
                Copyright © 2018 All Rights Reserved Powered By Hellbao
            </div>
        </div>
        <script src="${webPath}/static/layui/layui.all.js" type="text/javascript"></script>
        <script src="${webPath}/static/jquery.min.js" type="text/javascript"></script>
        <script src="${webPath}/static/js/index.js" type="text/javascript"></script>
    </body>
</html>