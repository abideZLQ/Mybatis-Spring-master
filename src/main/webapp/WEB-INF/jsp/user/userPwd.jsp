<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setAttribute("webPath", request.getContextPath());%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>layui</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="${webPath}/static/layui/css/layui.css" media="all">
    <!-- 注意：如果你直接复制所有代码到本地，上述css路径需要改成你本地的 -->
</head>
<body>

<blockquote class="layui-elem-quote layui-text">
    提示消息：请输入新密码
</blockquote>

<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>密码修改</legend>
</fieldset>
<form class="layui-form layui-form-pane" id="form" action="${webPath}/user/saveUserPwd.do" lay-verify="my_info">
    <input type="hidden" name="id" value="${users.id}" />
    <input type="hidden" name="username" value="${users.username}" />
    <div class="layui-form-item">
        <label class="layui-form-label">原密码</label>
        <div class="layui-input-inline">
            <input type="password" lay-verify="required" value="******" placeholder="请输入" readonly="true" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">设置新密码</label>
        <div class="layui-input-inline">
            <input type="password" id="newPwd" name="password" lay-verify="required" placeholder="请输入" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">确认原密码</label>
        <div class="layui-input-inline">
            <input type="password" id="confirmPwd" lay-verify="required" placeholder="请输入" autocomplete="off" class="layui-input">
        </div>
    </div>


    <div class="layui-form-item">
        <button class="layui-btn layui-btn-fluid" lay-submit lay-filter="tj">提交</button>
    </div>
</form>

<script src="${webPath}/static/layui/layui.js" charset="utf-8"></script>
<script src="${webPath}/static/jquery.min.js" type="text/javascript"></script>
<script  type="text/javascript" src="${webPath}/static/js/form_js/jquery.form.js"></script>
<script>
    layui.use(['element','laydate','form','layer','upload'], function(){
        var layer = layui.layer;
        var form = layui.form;
        form.on('submit(tj)', function(data){
            if( $("#newPwd").val() !== $("#confirmPwd").val() ){
                layer.msg('密码输入不对,请重新输入密码', {icon: 2});
                return false;
            }
            $("#form").ajaxForm({
                // server端返回的数据将会被执行，并传进'success'回调函数
                dataType:'json',
                success:function(result){

                    if(result.success){
                        layer.msg(result.info, {
                            // 1 对号 2 错号
                            icon: 1,
                            // 2秒关闭（如果不配置，默认是3秒）
                            time: 2000
                        }, function(){
                            // 更新全部
                            // form.render();
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
</script>

</body>
</html>