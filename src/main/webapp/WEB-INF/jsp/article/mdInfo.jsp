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
    <%--<blockquote class="layui-elem-quote layui-text">
        提示消息：本编辑器支持Markdown编辑，左边编写，右边预览
    </blockquote>--%>

    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
        <legend>编辑文章</legend>
    </fieldset>

    <div class="layui-form news_list">
        <div style="margin: 20px;">
            <form class="layui-form layui-form-pane" id="form" method="post" lay-filter="example" action="${webPath}/article/saveMdInfo.do">
                <input type="hidden" name="id" class="layui-input" value="${articles.id}">

                <div class="layui-form-item">
                    <input type="text" name="title" lay-verify="required" placeholder="输入文章标题" autocomplete="off" class="layui-input" value="${articles.title}">
                </div>

                <div id="content-editormd" class="layui-form-item">
                    <textarea style="display:none;" id="content-editormd-markdown-doc" name="content-editormd-markdown-doc">${articles.content}</textarea>
                </div>

                <div class="layui-form-item">
                    <button class="layui-btn" lay-submit="" lay-filter="submit">提交</button>
                </div>
            </form>
        </div>

    </div>
</body>
<script type="text/javascript" src="${webPath}/static/jquery.min.js"></script>
<script type="text/javascript" src="${webPath}/static/editor.md-master/editormd.min.js"></script>
<script type="text/javascript" src="${webPath}/static/layui/layui.js"></script>
<script type="text/javascript" src="${webPath}/static/js/form_js/jquery.form.js"></script>

<script type="text/javascript">
    $(function() {
        editormd("content-editormd", {
            width   : "100%",
            height  : 600,
            syncScrolling : "single",
            // 这里建议写绝对路径,寻址到webapp下的static,完整地址:localhost:8080/static..;如果写相对路径,寻址到controller中,localhost:8080/article/static..
            path    : "/static/editor.md-master/lib/",
            saveHTMLToTextarea : true, // 保存HTML到Textarea
            // theme : "dark", 工具栏主题
            // previewTheme : "dark", 右侧展示主题
            editorTheme : "pastel-on-dark", // 左侧编辑主题
            // 图片上传
            imageUpload : true,
            imageFormats: ["jpg","jpeg","gif","png","bmp","webp"],
            imageUploadURL: "${webPath}/article/saveMdImg.do"
        });
    });

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

    })
</script>
</html>
