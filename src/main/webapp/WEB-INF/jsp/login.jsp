<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/3/12/012
  Time: 10:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% request.setAttribute("webPath", request.getContextPath());%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>政务签到系统</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <script src="${webPath}/static/js/jquery-1.11.1.min.js"></script>
    <script src="${webPath}/static/layui/layui.js"></script>
    <link rel="stylesheet" href="${webPath}/static/layui/css/layui.css"  media="all">
</head>
<style>
    body{
        /*随意值,初始化改变*/
        height: 900px;
        background: url(static/proImg/home1.png) no-repeat;
        background-position: center; /*★使背景图片居中*/
        background-size: 100% 100%;
        position: relative;
    }
    .mainer{
        width: 700px;
        height: 500px;
        position: absolute;
        /*随意值,初始化改变*/
        left: 700px;
        top: 200px;
    }

    .mainer-left{
        height: 500px;
        background: url(static/proImg/home2.png) no-repeat;
        background-position: center;
        /*为什么不设置100% 100%,这个图片有问题*/
        background-size: auto auto;
    }

    .mainer-right-content{
        background-color: #FFFFFF;height: 430px;
    }

    .mainer-right-content-title{
        font-size: 30px;color: #0387CF;position: absolute;top: 115px;left: 90px;
    }

    .mainer-right-content-form{
        position: absolute;top: 200px;left: -20px;
    }

    .mainer-right-footer{
        background-color: #E8E8E8;height: 70px;line-height:70px; color: #888888;text-align: center;
    }
</style>
<script>
    /**
     *当改变浏览器尺寸时调整相关元素的尺寸
     */
    $(window).resize(function () {
        resetElementsSize();
    });

    /**
     *页面尺寸随着浏览器改变时相应地改变相关元素
     */
    function resetElementsSize() {
        // 浏览器时下窗口可视区域高度
        var bodyHeight = $(window).height();
        // 使body背景上下居中
        $("body").css("height",bodyHeight);
        // 浏览器时下窗口可视区域宽度
        var bodyWeight = $(window).width();
        // 使login登录窗口上下居中
        $(".mainer").css("left",(bodyWeight-700)/2);
        $(".mainer").css("top",(bodyHeight-500)/2);
    }

    //注意：选项卡 依赖 element 模块，否则无法进行功能性操作
    layui.use(['element','laydate','form','layer','upload'], function(){
        var form = layui.form;
    });

    $(function(){
        resetElementsSize();
    })
</script>
<body>
<!-- login登录窗口 -->
<div class="layui-row mainer">
    <div class="layui-col-lg4 mainer-left"></div>
    <div class="layui-col-lg8">
        <div class="layui-row mainer-right-content">
            <div class="mainer-right-content-title">政务签到系统</div>
            <form class="layui-form mainer-right-content-form" action="${webPath}/login.do" type="post">
                <div class="layui-form-item">
                    <label class="layui-form-label"></label>
                    <div class="layui-input-block" style="width: 280px;">
                        <input type="text" name="username" placeholder="请输入账号" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"></label>
                    <div class="layui-input-block" style="width: 280px;">
                        <input type="password" name="password" placeholder="请输入密码" class="layui-input">
                    </div>
                </div>
                <%--<div class="layui-form-item">
                    <label class="layui-form-label"></label>
                    <div class="layui-input-block" style="width: 280px;color: red;font-weight: bold;">
                        ${error}
                    </div>
                </div>--%>
                <h4 class="text-center" style="color:red;margin-left: 110px;">${error}</h4>
                <div class="layui-form-item">
                    <label class="layui-form-label"></label>
                    <div class="layui-input-inline" style="width: 280px;">
                        <label>
                            <input type="checkbox" name="remember" lay-ignore>请记住我
                        </label>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"></label>
                    <div class="layui-input-inline" style="width: 280px;">
                        <input type="submit" class="layui-btn layui-btn-fluid layui-btn-normal" value="确定" />
                    </div>
                    <div class="layui-input-inline" style="margin-left: 109px;margin-top: 10px;">
                        没有账号 <span style="cursor:pointer;">请注册</span>
                    </div>
                </div>
            </form>
        </div>
        <div class="layui-row mainer-right-footer">
            Copyright © 2018 政务签到系统
        </div>
    </div>
</div>
</body>
</html>

