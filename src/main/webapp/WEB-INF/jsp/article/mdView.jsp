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
    <div id="content-editormd">
        ${articles.contenthtml}
    </div>
</body>
<script type="text/javascript" src="${webPath}/static/jquery.min.js"></script>
<script type="text/javascript" src="${webPath}/static/editor.md-master/editormd.min.js"></script>
<script type="text/javascript" src="${webPath}/static/editor.md-master/lib/marked.min.js"></script>
<script type="text/javascript" src="${webPath}/static/editor.md-master/lib/prettify.min.js"></script>

<script type="text/javascript">
    $(function () {
        editormd.markdownToHTML("content-editormd");
    })
</script>
</html>
