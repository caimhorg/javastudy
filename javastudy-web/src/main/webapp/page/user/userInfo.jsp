<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/common.jsp" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
.layui-form-item{
	margin-top: 30px;
}
</style>
</head>
<body>
	<div>
		<form class="layui-form" action="" id="submitForm">
			<div class="layui-form-item">
			    <label class="layui-form-label">用户名</label>
			    <div class="layui-input-inline">
			      <input type="text" name="userName" class="layui-input" style="width:350px;" value="${user.userName}" readonly="readonly">
			    </div>
	  		</div>
	  		<div class="layui-form-item">
			    <label class="layui-form-label">身份证号</label>
			    <div class="layui-input-inline">
			      <input type="text" name="idCard" class="layui-input" style="width:350px;" value="${user.idCard}" readonly="readonly">
			    </div>
	  		</div>
	  		<div class="layui-form-item">
			    <label class="layui-form-label">邮箱</label>
			    <div class="layui-input-inline">
			      <input type="text" name="email" class="layui-input" style="width:350px;" value="${user.email}" readonly="readonly">
			    </div>
	  		</div>
	  		<div class="layui-form-item">
			    <label class="layui-form-label">手机号</label>
			    <div class="layui-input-inline">
			      <input type="text" name="phone" class="layui-input" style="width:350px;" value="${user.phone}" readonly="readonly">
			    </div>
	  		</div>
	  		<div class="layui-form-item">
			    <label class="layui-form-label">注册时间</label>
			    <div class="layui-input-inline">
			      <input type="text" name="phone" class="layui-input" style="width:350px;" value="${user.createTime}" readonly="readonly">
			    </div>
	  		</div>
		</form>
	</div>
</body>
<script>
</script>
