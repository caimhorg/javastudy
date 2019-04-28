<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/common.jsp" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<style>
body{
	padding : 0 15px;
}
</style>
</head>
<body>
<fieldset class="layui-elem-field layui-field-title">
	<legend>开发日志</legend>
</fieldset>
<ul class="layui-timeline">
	<li class="layui-timeline-item">
	    <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
	    <div class="layui-timeline-content layui-text">
	      <h3 class="layui-timeline-title">2017年10月31日</h3>
	      <p>
	      	后台权限模块开发完毕。
	      	<br>其他模块继续开发中。
	      </p>
	    </div>
    </li>
	<li class="layui-timeline-item">
	    <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
	    <div class="layui-timeline-content layui-text">
	      <h3 class="layui-timeline-title">2017年国庆</h3>
	      <p>
	      	项目基础工程搭建完成。
	      	<ul>
	      		<li>前台采用LayUI模块化框架，简单、快捷。</li>
	      		<li>后台采用springMVC、hibernate架构。</li>
	      		<li>数据库采用mysql。</li>
	      	</ul>  
	      </p>
	    </div>
    </li>
  	<li class="layui-timeline-item">
	    <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
	    <div class="layui-timeline-content layui-text">
	      <div class="layui-timeline-title">过去</div>
	    </div>
  	</li>
</ul>
</body>
