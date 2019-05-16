<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/4/30/030
  Time: 10:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% request.setAttribute("webPath", request.getContextPath());%>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link href="${webPath}/static/layui/css/layui.css" rel="stylesheet" type="text/css" media="all"/>
    <link href="${webPath}/static/css/common.css" rel="stylesheet" type="text/css"/>
    <link href="${webPath}/static/css/login.css" rel="stylesheet" type="text/css"/>
    <title>注册</title>
</head>
<body>
<div class="bg-top"></div>
<div class="center-area" >
    <div class="sys-title">政务签到系统</div>
    <div class="sys-card">

        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
            <legend>注册页面</legend>
        </fieldset>
        <blockquote class="layui-elem-quote">请填写个人信息</blockquote>
        <form class="layui-form layui-form-pane" id="form" method="post" lay-filter="example" action="${webPath}/user/saveUserInfo.do" style="padding:20px 60px;">

            <table class="layui-table">
                <colgroup>
                    <col width="20%">
                    <col width="30%">
                    <col width="20%">
                    <col width="30%">
                </colgroup>
                <tbody>
                <tr>
                    <td class="font-b color3">用户名</td>
                    <td><input type="text" name="username" lay-verify="required" placeholder="请输入用户名" autocomplete="off" class="layui-input"></td>
                    <td class="font-b color3">密码</td>
                    <td><input type="text" name="password" lay-verify="required" placeholder="请输入密码" autocomplete="off" class="layui-input"></td>
                </tr>
                <tr>
                    <td class="font-b color3">所属班级</td>
                    <td><input type="text" name="classname" lay-verify="required" placeholder="请输入所属班级" autocomplete="off" class="layui-input"></td>
                    <td class="font-b color3">联系方式</td>
                    <td><input type="text" name="phone" lay-verify="required" placeholder="请输入联系方式" autocomplete="off" class="layui-input"></td>
                </tr>
                <tr>
                    <td class="font-b color3">年龄</td>
                    <td><input type="text" name="age" lay-verify="required" placeholder="请输入年龄" autocomplete="off" class="layui-input"></td>
                    <td class="font-b color3">邮箱</td>
                    <td><input type="text" name="email" lay-verify="required" placeholder="请输入邮箱" autocomplete="off" class="layui-input"></td>
                </tr>
                </tbody>
            </table>


            <div class="layui-form-item" style="text-align: center;">
                <button class="layui-btn" lay-submit="" lay-filter="submit">注册</button>
                <a class="layui-btn layui-btn-warm" href="javascript:history.go(-1)">返&#12288;回</a>
            </div>
        </form>

    </div>
</div>
<div >
    <div class="sys-copyright">Copyright © 2018 All Rights Reserved Powered By Hellbao</div>
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
                            // 返回登录页
                            window.location  = "${webPath}/";
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
                    return '密码不一致';
                }
            }
        });


    });

</script>
</html>

