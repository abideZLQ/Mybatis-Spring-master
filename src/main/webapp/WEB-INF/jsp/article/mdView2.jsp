<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/3/14/014
  Time: 11:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
            <p style="font-size: 18px;font-weight: bold;">${articles.title}</p>
            <p style="margin-top: 10px;">时间：<span id="date">${articles.articledate}</span> </p>
        </div>
        <div id="content-editormd">
            <textarea style="display:none;">${articles.content}</textarea>
        </div>
    </div>

</body>
<script type="text/javascript" src="${webPath}/static/jquery.min.js"></script>
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

</script>
</html>
