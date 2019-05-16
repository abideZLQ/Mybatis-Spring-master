<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setAttribute("webPath", request.getContextPath());%>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>班级签到列表</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="${webPath}/static/layui/css/layui.css" rel="stylesheet" type="text/css"/>
        <link href="${webPath}/static/css/common.css" rel="stylesheet" type="text/css"/>
        <script>
            // 表格重载
            // 子页面调用父页面注意点
            // 1.不可以放在$(function(){})中【如果放入,子iframe获取的时候,父页面还没有加载完成,所以获取不到父页面变量,为undefined】
            // 2.script不能放在body下面【script放在head中是在加载页面之前做的事,而重载的操作必须在加载页面之前做】
            // 结论：【script放在下面等同于$(function(){}),所以即使下面没有加$(function(){}),子页面也获取不到父页面的DOM、变量、方法】
            function refresh(){
                // table.reload('idTest', options);
                // 刷新页面
                window.location.reload();
            }
        </script>
    </head>
    <body>
        <div class="layui-form news_list">
            <blockquote class="layui-elem-quote">
                <div class="layui-inline">
                    系统用户列表
                </div>
            </blockquote>
            <div class="layui-form">
                <table id="demo" lay-filter="test"></table>
            	<div id="page"></div>
            </div>
        </div>
    </body>
    


    <script type="text/javascript" src="${webPath}/static/jquery.min.js"></script>
    <script type="text/javascript" src="${webPath}/static/layui/layui.js"></script>
    <script  type="text/javascript" src="${webPath}/static/js/form_js/jquery.form.js"></script>

    <%-- toolbar绑定头工具栏 --%>
    <script type="text/html" id="toolbarDemo">
        <div class="layui-btn-container">
            <button class="layui-btn" lay-event="add">新增用户</button>
            <button class="layui-btn" lay-event="selDel">删除选中用户</button>
        </div>
    </script>

    <%-- toolbar绑定列工具条 --%>
    <script type="text/html" id="barDemo">
        <button class="layui-btn layui-btn-sm" lay-event="update">修改</button>
        <button class="layui-btn layui-btn-sm layui-btn-danger" lay-event="del">删除</button>
    </script>

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

            // 需要删除的用户ID数组
            var delUserIdArray = [];

            /**
             * templet自定义函数
             * @d Date时间对象
             * @format 想要转成的格式字符串
             * 作用：将后台的date时间对象转换为format字符串
             */
            layui.laytpl.toDateString = function(d, format){
                var date = new Date(d || new Date())
                    ,ymd = [
                    this.digit(date.getFullYear(), 4)
                    ,this.digit(date.getMonth() + 1)
                    ,this.digit(date.getDate())
                ]
                    ,hms = [
                    this.digit(date.getHours())
                    ,this.digit(date.getMinutes())
                    ,this.digit(date.getSeconds())
                ];

                format = format || 'yyyy-MM-dd HH:mm:ss';

                return format.replace(/yyyy/g, ymd[0])
                    .replace(/MM/g, ymd[1])
                    .replace(/dd/g, ymd[2])
                    .replace(/HH/g, hms[0])
                    .replace(/mm/g, hms[1])
                    .replace(/ss/g, hms[2]);
            };


            /**
             * templet自定义函数
             * @num 日期(年月日时分秒)
             * @length 限定长度
             * @end x
             * 作用：数字前置补零
             */
            layui.laytpl.digit = function(num, length, end){
                var str = '';
                num = String(num);
                length = length || 2;
                for(var i = num.length; i < length; i++){
                    str += '0';
                }
                return num < Math.pow(10, length) ? str + (num|0) : num;
            };


            /**
             * templet自定义函数
             * @d 请求后台返回的数据对象
             * 作用：根据时间条件判断按钮的显示状态
             */
            layui.laytpl.isStatus = function(d){
                // 开始d.declareStartTime 截止d.declareEndTime
                var date = new Date();
                if( date < d.declareStartTime || date > d.declareEndTime ){
                    return '<button class="layui-btn layui-btn-fluid layui-btn-primary layui-btn-disabled" style="color:#666;">'
                        +'<i class="layui-icon">&#x1007;</i> 申报已关闭&#12288;&#12288;'
                        +'</button>';
                }else if( date > d.declareStartTime && date < d.declareEndTime ){
                    return '<button class="layui-btn layui-btn-fluid layui-btn-primary layui-btn-disabled" style="color:#5FB878;">'
                        +'<i class="layui-icon">&#x1005</i> 正在申报&#12288;&#12288;'
                        +'</button>';
                }
            }

            /**
             *
             * @param param 在列中显示内部属性对象的属性值
             * @returns {*}
             */
            layui.laytpl.getInnerObjectParam = function(param){
                // 返回内部属性对象的属性值
                return param;
            }


            // layui.table方法渲染
            table.render({
                // 绑定容器ID
                elem: '#demo'
                // 高度将始终铺满,表格容器距离浏览器顶部和底部的距离“和”为100px
                ,height: 'full-100'
                // 全局定义所有常规单元格的最小宽度,一般用于列宽自动分配的情况
                ,cellMinWidth:100
                // 设置数据接口
                ,url: '${webPath}/sign/getSignList.do'
                // 将返回的任意数据格式解析成 table 组件规定的数据格式
                ,parseData: function(res){ // res 即为原始返回的数据
                    return {
                        "code": res.code,    // 解析接口状态
                        "msg": res.msg,      // 解析提示文本
                        "count": res.count,  // 解析数据长度
                        "data": res.data     // 解析数据列表
                    };
                }
                // 支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem）
                ,page: {
                    // 自定义分页布局：总数、上一页、分页、下一页、每页条数选择、页面刷新、跳页
                    layout: ['count', 'prev', 'page', 'next', 'limit', 'refresh', 'skip']
                    // 上一页显示的名称
                    ,prev:'上一页'
                    // 下一页显示的名称
                    ,next:'下一页'
                    // 每页条数的选择项,必要条件：layout参数开启了limit
                    ,limits:[5,10,15]
                }
                // 大尺寸的表格
                ,size: 'lg'
                // toolbar绑定头工具栏
                //,toolbar: '#toolbarDemo'
                // 表头
                ,cols: [[
                    // 开启复选框
                    {type: 'checkbox', fixed: 'left'}
                    // field字段名 title标题名称 hide隐藏此列
                    ,{field: 'id', title: 'ID', hide: true}
                    ,{templet:'<div>{{ layui.laytpl.getInnerObjectParam(d.users.username) }}</div>', title: '用户名'}
                    ,{templet:'<div>{{ layui.laytpl.toDateString(d.signintime,"HH:mm:ss") }}</div>', title: '签到时间'}
                    ,{templet:'<div>{{ layui.laytpl.toDateString(d.signouttime,"HH:mm:ss") }}</div>', title: '签退时间'}
                    ,{templet:'<div>{{ layui.laytpl.toDateString(d.sdate,"yyyy-MM-dd") }}</div>', title: '签退日期'}
                ]]
            });

            // 事件监听 语法：table.on('event(filter)', callback); 注：event为内置事件名，filter为容器lay-filter设定的值

        });

    </script>


</html>
