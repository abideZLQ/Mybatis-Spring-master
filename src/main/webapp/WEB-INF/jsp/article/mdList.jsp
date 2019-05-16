<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setAttribute("webPath", request.getContextPath());%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>文章列表</title>
    <link rel="stylesheet" href="${webPath}/static/layui/css/layui.css" />
    <style>
        *{
            margin: 0;
            padding: 0;
        }
        body{
            background-color: #FFFFFF;
        }
        .outerDiv{
            margin: 15px;
        }
        .content-p1{
            font-size: 20px;margin-top: 10px;cursor:pointer;
        }
        .content-p2{
            color: #888888; font-size: 14px;margin-top: 10px;margin-bottom: 10px;
        }
        .content-btn{
            margin-top: 15px;
        }
    </style>
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
    <!-- 头部选择+新建 -->
    <div class="layui-row outerDiv">
        <div class="layui-col-lg1">
            <input type="text" class="layui-input" name ="title" id="title" placeholder="请输入搜索内容" style="width: 190px;">
        </div>
        <div class="layui-col-lg10">
            <button class="layui-btn layui-btn-normal" id="search" onclick="getInfoToPage()" >搜索</button>
        </div>
        <div class="layui-col-lg1">
            <button class="layui-btn layui-btn-normal" id="add" style="background-color: #5CBD5C;"><i class="fa fa-plus" style="color: #fff;"></i> 新增</button>
        </div>
    </div>
    <!-- 内容 -->
    <div class="layui-card outerDiv">
        <div class="layui-card-header" style="font-size: 30px;">学习园地</div>
        <div class="layui-card-body layui-row">
            <!-- 内容列表 -->
            <div id="tbody"></div>
        </div>
    </div>

    <div class="layui-row outerDiv">
        <!-- 分页插件 -->
        <div id="demo0" class="layui-col-md-offset5 layui-col-lg6"></div>
    </div>
</body>
<script type="text/javascript" src="${webPath}/static/jquery.min.js"></script>
<script src="${webPath}/static/layui/layui.all.js" type="text/javascript"></script>
<script>
    var webPath = "${webPath}";
    // 分页插件的当前页 - 作用：1.初始化 2.分页插件使用
    var page = 1;
    // 分页插件的当前页的数量 - 作用：1.初始化 2.分页插件使用
    var limit = 10;
    // 总记录数
    var total = 0;
    // 搜索条件
    var searchParam;
    $(document).ready(function () {
        getInfoToPage();
    });

    // 分页方法
    function getInfoToPage() {
        // 获取搜索条件
        searchParam = $.trim($("#title").val());
        // 1.请求数据并渲染
        studentInfo();
        // 2.分页插件操作
        toPage();
    }

    // 1.请求数据并渲染
    function studentInfo() {
        $.ajax({
            type: "post",
            url: "${webPath}/article/getMdList.do",
            async: false,
            dataType: 'json',
            data: {
                "page": page,
                "limit": limit,
                "title":searchParam
            },
            success: successFunction
        });
    }

    //数据请求成功
    function successFunction(result) {
        $("#tbody").html("");
        // 重新赋值当前页
        page = result.pageNum;
        // 重新赋值每页的数量
        limit = result.pageSize;
        // 总记录数
        total = result.total;
        // 动态拼接数据
        var tr = "";
        // 遍历结果集
        $.each(result.list, function(i,n){
            tr += '<div class="content" data-id = "' + n.id + '">'+
                    '<div class="layui-col-lg11">'+
                        '<p class="content-p1">' + n.title + '</p>' +
                        '<p class="content-p2">'+
                            '<span style="margin-right: 30px;">发布时间：' +toDateString(n.articledate) + '</span>'+
                        '</p>'+
                    '</div>'+
                    '<div class="layui-col-lg1">'+
                        '<button class="layui-btn layui-btn-primary layui-btn-sm content-btn update">修改</button>'+
                        '<button class="layui-btn layui-btn-primary layui-btn-sm content-btn deleteById">删除</button>'+
                    '</div>'+
                    '<hr>'+
                  '</div>';
        });
        $("#tbody").html(tr);
    }

    // 2.分页插件操作
    function toPage() {
        layui.use(['laypage', 'layer'], function () {
            var laypage = layui.laypage,
                layer = layui.layer;

            //总页数低于页码总数
            laypage.render({
                elem: 'demo0', //注意，这里的 page 是 ID，不用加 # 号
                limits: [5, 10, 20, 30, 40, 50],
                prev: "上一页",
                next: "下一页",
                layout: ['count', 'prev', 'page', 'next', 'limit', 'skip'],
                count: total,
                curr: page,
                limit: limit,
                jump: function (data, first) {
                    page = data.curr;
                    limit = data.limit;
                    if (!first) { //点击右下角分页时调用
                        studentInfo();
                    }
                }

            });

            $(".layui-card-body").off("click", '.update')
            $(".layui-card-body").on("click", '.update', function () {
                // 获取markdown文章ID
                var id = $(this).parent().parent().attr("data-id");
                // 控制父页面body高度(根据iframe的高度来确定父页面的高度)
                $('body').css("height","721px");
                var index = layer.open({
                    type: 2,
                    // 最大化，标题默认为'信息'
                    title: "提示消息：本编辑器支持Markdown编辑，左边编写，右边预览",
                    content: '${webPath}/article/jumpMdInfo.do?id='+id,
                    // 1、先打开800x600的子窗口 2、设置子窗口最大化 3、如果不设置此属性，子窗口默认非常小，然后再最大化，非常不协调
                    area: ['800px', '600px'],
                    // 不固定
                    fix: false,
                    // 显示最大小化按钮
                    maxmin: true
                });
                // 最大化iframe子页面
                layer.full(index);
            });

            $(".layui-card-body").off("click", '.deleteById');
            $(".layui-card-body").on("click", '.deleteById', function () {
                var id = $(this).parent().parent().attr("data-id");
                layer.confirm("是否确认删除？", {shade: 0}, function (index) {
                    $.ajax({
                        url: "${webPath}/article/delMdInfo.do",
                        type: "post",
                        data: {id: id},
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
                                    time: 2000
                                }, function(){
                                    // 刷新页面
                                    refresh();
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
            });

            $("#add").off("click");
            $("#add").on("click", function () {
                // 控制父页面body高度(根据iframe的高度来确定父页面的高度)
                $('body').css("height","721px");
                var index = layer.open({
                    type: 2,
                    // 最大化，标题默认为'信息'
                    title: "提示消息：本编辑器支持Markdown编辑，左边编写，右边预览",
                    content: '${webPath}/article/jumpMdInfo.do',
                    // 1、先打开800x600的子窗口 2、设置子窗口最大化 3、如果不设置此属性，子窗口默认非常小，然后再最大化，非常不协调
                    area: ['800px', '600px'],
                    // 不固定
                    fix: false,
                    // 显示最大小化按钮
                    maxmin: true,
                    // 弹出后的成功回调方法
                    success: function (layero, index) {

                    },
                    // 层销毁后触发的回调
                    end: function () {

                    },
                    // 右上角关闭按钮触发的回调
                    cancel: function () {

                    }
                });
                // 最大化iframe子页面
                layer.full(index);
            });

            $(".layui-card-body").off("click", '.content-p1');
            $(".layui-card-body").on("click", '.content-p1', function () {
                // 获取markdown文章ID
                var id = $(this).parent().parent().attr("data-id");
                // 控制父页面body高度(根据iframe的高度来确定父页面的高度)
                $('body').css("height","721px");
                var index = layer.open({
                    type: 2,
                    title: "详情",
                    content: '${webPath}/article/jumpMdView.do?id='+id,
                    area: ['800px', '600px'],
                    // 不固定
                    fix: false,
                    // 显示最大小化按钮
                    maxmin: true
                });
                // 最大化iframe子页面
                layer.full(index);
            });

        });
    }

    /**
     * templet自定义函数
     * @d Date时间对象
     * @format 想要转成的格式字符串
     * 作用：将后台的date时间对象转换为format字符串
     */
    function toDateString(d, format){
        var date = new Date(d || new Date())
            ,ymd = [
                digit(date.getFullYear(), 4)
               ,digit(date.getMonth() + 1)
               ,digit(date.getDate())
        ]
            ,hms = [
                digit(date.getHours())
               ,digit(date.getMinutes())
               ,digit(date.getSeconds())
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
    function digit(num, length, end){
        var str = '';
        num = String(num);
        length = length || 2;
        for(var i = num.length; i < length; i++){
            str += '0';
        }
        return num < Math.pow(10, length) ? str + (num|0) : num;
    };

</script>

</html>