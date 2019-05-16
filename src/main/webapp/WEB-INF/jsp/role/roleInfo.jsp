<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setAttribute("webPath", request.getContextPath());%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>TODO supply a title</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="${webPath}/static/layui/css/layui.css" rel="stylesheet" type="text/css"/>
        <link href="${webPath}/static/css/common.css" rel="stylesheet" type="text/css"/>
        <!-- zTree -->
        <link rel="stylesheet" href="${webPath }/static/zTree/css/demo.css" type="text/css">
        <link rel="stylesheet" href="${webPath }/static/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css">
    </head>
    <body>
        <blockquote class="layui-elem-quote layui-text">
            提示消息：请修改用户角色信息
        </blockquote>

        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
            <legend>角色修改</legend>
        </fieldset>


        <div class="layui-form news_list">
            <div style="margin: 20px;">
                <form class="layui-form layui-form-pane" id="form" method="post" lay-filter="example" action="${webPath}/role/saveRoleInfo.do">
                    <input type="hidden" name="id" class="layui-input">
                    <div class="layui-form-item">
                        <label class="layui-form-label">角色</label>
                        <div class="layui-input-block">
                            <input type="text" name="role" autocomplete="off" placeholder="请输入角色标识" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">角色描述</label>
                        <div class="layui-input-block">
                            <input type="text" name="description" lay-verify="required" placeholder="请输入角色描述" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">角色权限</label>
                        <div class="layui-input-block">
                            <input type="hidden" id="idList" name="idList" class="layui-input">
                            <ul id="treeDemo" class="ztree" style="background:#FFFFFF;"></ul>
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <button class="layui-btn" lay-submit="" lay-filter="submit">提交</button>
                    </div>
                </form>
            </div>

        </div>
    </body>
    


    <script type="text/javascript" src="${webPath}/static/jquery.min.js"></script>
    <script type="text/javascript" src="${webPath}/static/layui/layui.js"></script>
    <script type="text/javascript" src="${webPath}/static/js/form_js/jquery.form.js"></script>
    <!-- zTree -->
    <script type="text/javascript" src="${webPath }/static/zTree/js/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="${webPath }/static/zTree/js/jquery.ztree.excheck.js"></script>


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


            // 表单初始赋值
            // 参数的键值是元素对应的 name 和 value
            form.val('example', {
                "id": "${roles.id}"
                ,"role": "${roles.role}"
                ,"description": "${roles.description}"
            })

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
                        return '密码不一直';
                    }
                }
            });


        });

        // zTree
        var setting = {
            check: {
                // 设置zTree的节点上是否显示 checkbox / radio
                enable: true,
                // [checkbox独有属性]勾选checkbox对于父子节点的关联关系
                chkboxType: {"Y":"", "N":""}
            },
            view: {
                // 双击节点时，是否自动展开父节点的标识
                dblClickExpand: false,
                // 不显示节点图标
                showIcon: false,
                // 不显示连线
                showLine: false
            },
            data: {
                simpleData: {
                    // true / false 分别表示 使用 / 不使用 简单数据模式
                    enable: true
                }
            },
            async: {
                //开启异步加载模式
                enable: true,
                // Ajax 获取数据的 URL 地址
                url: "${webPath}/role/getRoleMenuTree.do?roleid=${roles.id}"
            },
            callback: {
                // 用于捕获 checkbox / radio 被勾选 或 取消勾选的事件回调函数
                onCheck: onCheck
            }
        };

        // 用于捕获 checkbox / radio 被勾选 或 取消勾选的事件回调函数
        function onCheck(e, treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj("treeDemo");
            // 注意：nodes是一个数组
            var nodes = zTree.getCheckedNodes(true);
            var v = "";
            for (var i=0, l=nodes.length; i<l; i++) {
                v += nodes[i].id + ",";
            }
            // 赋值到input中用于传送到后台
            $("#idList").val(v);
        }

        // 初始化页面调用zTree
        $.fn.zTree.init($("#treeDemo"), setting);

    </script>


</html>
