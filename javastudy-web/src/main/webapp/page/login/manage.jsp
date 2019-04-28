<%@page import="com.javastudy.contants.CommonContants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file='../common/common.jsp'%>
<%@page import="com.javastudy.system.MenuService"%>
<%@page import="com.javastudy.contants.CommonContants"%>
<%@page import="java.util.List"%>
<%
	String userId = (String)request.getSession().getAttribute(CommonContants.USER_ID);
	List<String> userMenuIds = (List<String>)request.getSession().getAttribute(CommonContants.USER_MENU_IDS);
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Java进阶平台</title>
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
  <div class="layui-header">
    <div class="layui-logo"><i class="layui-icon" style="font-size: 18px;">&#xe609;</i>&nbsp;Java进阶平台</div>
    <!-- 头部区域（可配合layui已有的水平导航） -->
<!--     <ul class="layui-nav layui-layout-left"> -->
<!--       <li class="layui-nav-item"><a href="">控制台</a></li> -->
<!--       <li class="layui-nav-item"><a href="">商品管理</a></li> -->
<!--       <li class="layui-nav-item"><a href="">用户</a></li> -->
<!--       <li class="layui-nav-item"> -->
<!--         <a href="javascript:;">其它系统</a> -->
<!--         <dl class="layui-nav-child"> -->
<!--           <dd><a href="">邮件管理</a></dd> -->
<!--           <dd><a href="">消息管理</a></dd> -->
<!--           <dd><a href="">授权管理</a></dd> -->
<!--         </dl> -->
<!--       </li> -->
<!--     </ul> -->
    <ul class="layui-nav layui-layout-right">
      <li class="layui-nav-item">
        <a href="javascript:;">
          <img src="http://t.cn/RCzsdCq" class="layui-nav-img">
          贤心
        </a>
        <dl class="layui-nav-child">
          <dd><a href="">基本资料</a></dd>
          <dd><a href="">安全设置</a></dd>
        </dl>
      </li>
      <li class="layui-nav-item"><a href="${ctx}/page/login/login.jsp">退出</a></li>
    </ul>
  </div>
  
  <div class="layui-side layui-bg-black" >
    <div class="layui-side-scroll">
      <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
      <ul class="layui-nav layui-nav-tree"  lay-filter="test">
      		<li class="layui-nav-item">
      			<a class="" href="javascript:doAction('login/index.do');">首页</a>
      		</li>
			<%= MenuService.initMenusNew(userId, userMenuIds)%>
      </ul>
    </div>
  </div>
  <div class="layui-body" style="bottom:0px;">
    <iframe src="" id="mainiframe" frameborder="0" style="border: none; width: 100%;height: 100%;">

     </iframe>
  </div>
    
</div>
<script>
$(function() {
	layui.use(['element'], function() {
		var element = layui.element;
	})
	firstClick();
})

function doAction(url) {
  	$('#mainiframe').attr('src', ctx + '/'+ url +'');
}

function firstClick() {
	var $_firstli = $('.layui-nav-tree').find('.layui-nav-item').first();
	$_firstli.on('click', function() {
		doAction('/login/index.do');
	})
	$_firstli.trigger('click');
	if (!$_firstli.hasClass('layui-this')) {
		$_firstli.addClass('layui-this');
	}
}

</script>
