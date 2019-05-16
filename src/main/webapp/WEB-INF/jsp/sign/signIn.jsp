<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/3/12/012
  Time: 15:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% request.setAttribute("webPath", request.getContextPath());%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <script src="${webPath}/static/js/jquery-1.11.1.min.js"></script>
    <script src="${webPath}/static/layui/layui.js"></script>
    <link rel="stylesheet" href="${webPath}/static/layui/css/layui.css" media="all">
    <style>
        .main{
            margin:0 auto;
            height:720px;
            background: url(${webPath}/static/proImg/041.jpg) no-repeat;
            background-position: center; /*★使背景图片居中*/
            background-size: 100% 100%;
        }
        #time{
            width:200px;
            height:200px;
            background: url(${webPath}/static/proImg/042.jpg) no-repeat;
            background-position: center; /*★使背景图片居中*/
            background-size: 100% 100%;
        }
    </style>
</head>
<body>
    <div class="main">
        <div class="layui-row" style="height: 350px;">
              <div id="time">
                  请查看系统当前时间喔~
              </div>

        </div>
        <div class="layui-row">

            <div class="layui-col-lg2 layui-col-lg-offset2">
                <button id="signIn" class="layui-btn layui-btn-normal layui-btn-lg">${map.inMessage}</button>
            </div>


            <div class="layui-col-lg2 layui-col-lg-offset4">
                <button id="signOut" class="layui-btn layui-btn-normal layui-btn-lg">${map.outMessage}</button>
            </div>
        </div>
    </div>

</body>
<script type="text/javascript" src="${webPath}/static/jquery.min.js"></script>
<script type="text/javascript" src="${webPath}/static/layui/layui.js"></script>
<script type="text/javascript" src="${webPath}/static/js/form_js/jquery.form.js"></script>
<script>
    // 此代码块相当于初始化加载
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





    })

    // 获取系统当前时间(动态)
    var t = null;
    t = setTimeout(time,1000);//開始运行
    function time(){
        clearTimeout(t);//清除定时器
        dt = new Date();
        var h=dt.getHours();//获取时
        var m=dt.getMinutes();//获取分
        var s=dt.getSeconds();//获取秒
        $("#time").text("当前时间为："+h+"时"+m+"分"+s+"秒");
        t = setTimeout(time,1000); //设定定时器，循环运行
    }

    // 检查当前用户的当天的签到信息中的签到签退状态
    function checkSignInOut(type){
        // 签到信息 (签到打卡 | 迟到打卡) 这里比后台的签到信息少一个,操作没有成功页面不变
        var inMessage = "";
        // 签退信息 (签退打卡 | 早退打卡) 这里比后台的签退信息少一个,操作没有成功页面不变
        var outMessage = "";
        var date = new Date();
        var h = date.getHours();
        // 签到事件
        if(type == 1){
            if(h<9){
                inMessage = "签到打卡";
            }else{
                inMessage = "迟到打卡";
            }
            return inMessage;
        // 签退事件
        }else{
            if(h>=18){
                outMessage = "签退打卡";
            }else{
                outMessage = "早退打卡";
            }
            return outMessage;
        }
    }

    /**
     * 签到事件
     */
    $("#signIn").on("click",function(){
        $.ajax({
            url: "${webPath}/sign/saveSignInfo.do",
            type: "post",
            data: {type:1},
            // server端返回的数据将会被执行，并传进'success'回调函数
            dataType:'json',
            // 没有缓存
            cache: false,
            // 异步
            async: true,
            success: function (result) {
                if(result.success){
                    layer.msg(result.info, {
                        // 1 对号 2 错号
                        icon: 1,
                        // 2秒关闭（如果不配置，默认是3秒）
                        time: 5000
                    }, function(){
                        var inMessage = checkSignInOut(1);
                        $("#signIn").text(inMessage);

                    });
                }else{
                    layer.msg(result.info, {
                        // 1 对号 2 错号
                        icon: 2,
                        // 2秒关闭（如果不配置，默认是3秒）
                        time: 5000
                    })
                }
            }
        })
    })

    /**
     * 签退事件
     */
    $("#signOut").on("click",function(){
        $.ajax({
            url: "${webPath}/sign/saveSignInfo.do",
            type: "post",
            data: {type:2},
            // server端返回的数据将会被执行，并传进'success'回调函数
            dataType:'json',
            // 没有缓存
            cache: false,
            // 异步
            async: true,
            success: function (result) {
                if(result.success){
                    layer.msg(result.info, {
                        // 1 对号 2 错号
                        icon: 1,
                        // 2秒关闭（如果不配置，默认是3秒）
                        time: 5000
                    }, function(){
                        var outMessage = checkSignInOut(2);
                        $("#signOut").text(outMessage);
                    });
                }else{
                    layer.msg(result.info, {
                        // 1 对号 2 错号
                        icon: 2,
                        // 2秒关闭（如果不配置，默认是3秒）
                        time: 5000
                    })
                }
            }
        })
    })



</script>

</html>
