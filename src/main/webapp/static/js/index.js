/**
 * @name index
 * @info 描述：
 * @author Hellbao <1036157505@qq.com>
 * @datetime 2018-10-29 16:42:24
 */


//全局变量---------------------------------------------------------------------------------------


//初始化-----------------------------------------------------------------------------------------
$(function () {
    resize();
});
$(window).resize(function () {          //当浏览器大小变化时
    resize();
});
//重置窗口大小
function resize() {
    mainh = $(window).height() - 200;
    $('#rightMain').css('height', mainh + 'px');
}

//函数--------------------------------------------------------------------------------------------
