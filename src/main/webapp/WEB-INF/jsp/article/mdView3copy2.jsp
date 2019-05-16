<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/3/14/014
  Time: 11:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<% request.setAttribute("webPath", request.getContextPath());%>
<html>
<head>
    <title>Title</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="${webPath}/static/editor.md-master/css/editormd.css" rel="stylesheet" type="text/css"/>
    <link href="${webPath}/static/layui/css/layui.css" rel="stylesheet" type="text/css"/>

</head>
<body>
    <!-- 宽度1200px,百分比布局 -->
    <div style="width: 1200px;margin: 10px auto 0 auto;">
        <!-- 标题展示区,文字居中 -->
        <div style="text-align: center;">
            <p style="font-size: 30px;font-weight: bold;">${articles.title}</p>
            <p style="margin-top: 10px;">
                时间：<span id="date">${articles.articledate}</span>
                <c:choose>
                    <c:when test="${sessionScope.sessionUsersId == articles.userid}">
                        <a href="${webPath}/article/jumpMdInfo.do?id=${articles.id}" style="color: #86aee8;cursor: pointer;">修改</a>
                        <a class="deleteById" data-id="${articles.id}" style="color: #86aee8;cursor: pointer;">删除</a>
                    </c:when>
                    <c:otherwise>
                        <shiro:hasPermission name="menu:artUpdDel">
                            <a href="${webPath}/article/jumpMdInfo.do?id=${articles.id}" style="color: #86aee8;cursor: pointer;">修改</a>
                            <a class="deleteById" data-id="${articles.id}" style="color: #86aee8;cursor: pointer;">删除</a>
                        </shiro:hasPermission>
                    </c:otherwise>
                </c:choose>
            </p>
            <hr>
        </div>
        <div id="content-editormd">
            <textarea style="display:none;">${articles.content}</textarea>
        </div>
    </div>

</body>
<script type="text/javascript" src="${webPath}/static/jquery.min.js"></script>
<script src="${webPath}/static/layui/layui.all.js" type="text/javascript"></script>

<script type="text/javascript" src="${webPath}/static/editor.md-master/editormd.min.js"></script>
<script type="text/javascript" src="${webPath}/static/editor.md-master/lib/marked.min.js"></script>
<script type="text/javascript" src="${webPath}/static/editor.md-master/lib/prettify.min.js"></script>

<script type="text/javascript" src="${webPath}/static/editor.md-master/lib/raphael.min.js"></script>
<script type="text/javascript" src="${webPath}/static/editor.md-master/lib/underscore.min.js"></script>
<script type="text/javascript" src="${webPath}/static/editor.md-master/lib/sequence-diagram.min.js"></script>
<script type="text/javascript" src="${webPath}/static/editor.md-master/lib/flowchart.min.js"></script>
<script type="text/javascript" src="${webPath}/static/editor.md-master/lib/jquery.flowchart.min.js"></script>


<script type="text/javascript">
    $(function () {
        editormd.markdownToHTML("content-editormd", {
            htmlDecode: "style,script,iframe", //可以过滤标签解码
            emoji: true,
            taskList: true,
            tex: true,               // 默认不解析
            flowChart: true,         // 默认不解析
            sequenceDiagram: true, // 默认不解析
            codeFold: true
        });

        // 将后台的date时间对象转换为format字符串
        // 生成页面之后，就不是时间对象了，而是字符串了。所以转换函数不生效
        // toDateString($("#date").html());
    })

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

    layui.use(['laypage', 'layer'], function () {
        var laypage = layui.laypage, layer = layui.layer;

        $(".deleteById").on("click", function () {
            var id = $(this).attr("data-id");
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
                                // 返回文章列表
                                window.location  = "${webPath}/article/jumpMdList.do";
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
    });
</script>
</html>
