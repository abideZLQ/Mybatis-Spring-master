<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setAttribute("webPath", request.getContextPath());%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>TODO supply a title</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="${webPath}/static/layui/css/layui.css" rel="stylesheet" type="text/css"/>
        <link href="${webPath}/static/css/common.css" rel="stylesheet" type="text/css"/>

    </head>
    <body>
        <c:if test="${personal == 'update'}">
            <blockquote class="layui-elem-quote layui-text">
                提示消息：请修改个人用户信息
            </blockquote>

            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
                <legend>信息修改</legend>
            </fieldset>
        </c:if>

        <div class="layui-form news_list">
            <div style="margin: 20px;">
                <form class="layui-form layui-form-pane" id="form" method="post" lay-filter="example" action="${webPath}/user/saveUserInfo.do">
                    <input type="hidden" name="id" class="layui-input">
                    <div class="layui-form-item">
                        <label class="layui-form-label">用户名</label>
                        <div class="layui-input-block">
                            <input type="text" name="username" autocomplete="off" placeholder="请输入用户名" class="layui-input">
                        </div>
                    </div>
                    <c:if test="${addOrUpdate == 'add'}">
                        <div class="layui-form-item">
                            <label class="layui-form-label">密码</label>
                            <div class="layui-input-block">
                                <input type="password" name="password" lay-verify="required" placeholder="请输入密码" autocomplete="off" class="layui-input">
                            </div>
                        </div>
                    </c:if>
                    <div class="layui-form-item">
                        <label class="layui-form-label">年龄</label>
                        <div class="layui-input-block">
                            <input type="text" name="age" lay-verify="required" placeholder="请输入年龄" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">邮箱</label>
                        <div class="layui-input-block">
                            <input type="text" name="email" lay-verify="required|email" placeholder="请输入邮箱" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">联系方式</label>
                        <div class="layui-input-block">
                            <input type="text" name="phone" lay-verify="required" placeholder="请输入联系方式" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">所属班级</label>
                        <div class="layui-input-block">
                            <input type="text" name="classname" lay-verify="required" placeholder="请输入所属班级" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">用户角色</label>
                        <div class="layui-input-block">
                            <select name="role" lay-verify="required">
                                <option value=""></option>
                                <c:forEach items="${rolesList}" var="roles">
                                    <c:choose>
                                        <c:when test="${ roles.id == usersRole }">
                                            <option value="${roles.id}" selected>${roles.description}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${roles.id}">${roles.description}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <button class="layui-btn" lay-submit="" lay-filter="submit">提交</button>
                    </div>
                </form>
            </div>

        </div>
    </body>
    


    <script type="text/javascript" src="${webPath}/static/jquery.min.js"></script>
    <script type="text/javascript" src="${webPath}/static/layui/layui.js"></script>
    <script type="text/javascript" src="${webPath}/static/js/form_js/jquery.form.js"></script>



    <script>
        layui.use(['element','laydate','form','layer','upload','laypage','table','carousel'], function () {
            // 内置JQuery
            var $ = layui.jquery;
            // 常用元素操作
            var element = layui.element;
            // 日期与时间选择
            var laydate = layui.laydate;
            // 表单
            var form = layui.form;
            // 弹出层
            var layer = layui.layer;
            // 文件上传
            var upload = layui.upload;
            // 分页
            var laypage = layui.laypage;
            // 数据表格
            var table = layui.table;
            // 轮播
            var carousel = layui.carousel;


            // 表单初始赋值
            // 参数的键值是元素对应的 name 和 value
            form.val('example', {
                "id": "${users.id}"
                ,"username": "${users.username}"
                ,"password": "${users.password}"
                ,"age":"${users.age}"
                ,"email": "${users.email}"
                ,"phone": "${users.phone}"
                ,"classname": "${users.classname}"
                // select下拉框默认选中项
                ,"role": "${usersRole}"
            })

            // 监听form提交
            form.on('submit(submit)', function(data){
                // 异步提交
                $("#form").ajaxForm({
                    // server端返回的数据将会被执行，并传进'success'回调函数
                    dataType:'json'
                    ,success:function(result){
                       if(result.success){
                           layer.msg(result.info, {
                               // 1 对号 2 错号
                               icon: 1,
                               // 2秒关闭（如果不配置，默认是3秒）
                               time: 2000
                           }, function(){
                               // 注意：parent 是 JS 自带的全局对象，可用于操作父页面，得到当前iframe层的索引
                               var index = parent.layer.getFrameIndex(window.name);
                               // 关闭iframe页面
                               parent.layer.close(index);
                               // 刷新父级页面
                               parent.refresh();
                           });

                       }else{
                           layer.msg(result.info, {
                               // 1 对号 2 错号
                               icon: 2,
                               // 2秒关闭（如果不配置，默认是3秒）
                               time: 2000
                           })
                       }
                    }
                });
            });

            // 自定义验证规则
            form.verify({
                loginName: [/^[a-zA-Z]{1}([a-zA-Z0-9]|[._]){4,19}$/, '只能输入5-20个以字母开头、可带数字、“_”、“.”的字串']
                ,pass:  function(value, item){
                    if(value !='' && value !=null){
                        if(value.length < 6 ){
                            return '密码必须6到12位';
                        }else if(value.length >12 ){
                            return '密码必须6到12位';
                        }

                    }
                    var passwordl =$("input[name=passwordl]").val();
                    if(value !=passwordl){
                        return '密码不一直';
                    }
                }
            });


        });

    </script>


</html>
