<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file='../common/common.jsp'%>    
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录</title>
<style>
body{
	background: url("${ctx}/public/images/login/night.jpg");
}
.titleFont{
	font-size: 60px;
	font-style: normal;
	font-weight: 800;
	color: #009688;
}
</style>
</head>
<body>
<div class="layui-fluid" style="height: 100%">
	<div class="layui-row" style="height: 20%;">
	</div>
	<div class="layui-row" style="height: 40%;text-align: center;">
<!-- 		<span class="titleFont">Java&nbsp;&nbsp;进&nbsp;&nbsp;阶&nbsp;&nbsp;平&nbsp;&nbsp;台</span> -->
	</div>
	<div class="layui-row" style="height: 35%">
		<div class="layui-col-md12" style="height: 100%;width: 30%;margin-left: 35%;">
			<form action="${ctx}/login/login.do" method="post" class="layui-form layui-form-pane" id="loginForm">
				<div class="layui-form-item">
					<label class="layui-form-label">用户名</label>
					<div class="layui-input-block">
					<input type="text" name="userName" lay-verify="required" placeholder="" class="layui-input">
					</div>
				</div>
				<div class="layui-form-item" style="margin-top: 25px;">
					<label class="layui-form-label">密码</label>
					<div class="layui-input-block">
					<input type="text" name="password" lay-verify="required" placeholder="" class="layui-input">
					</div>
				</div>
				<div class="layui-form-item" style="margin-top: 25px;">
				      <button lay-submit="" lay-filter="loginSubmit" type="button" class="layui-btn" style="width:100%">登&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;录</button>
				</div>
				<div style="width:70px;float:right;">
					<a href="${ctx}/user/userRegisterIndex.do"><span style="color:#009688;font-weight: 800;">免费注册</span></a>
				</div>
			</form>
		</div>
	</div>
</div>
</body>
<script type="text/javascript">
	layui.use(['form', 'layer'], function() {
		var form = layui.form;
		var layer = layui.layer;
		form.on('submit(loginSubmit)', function(data) {
			var index = layer.load(2);
			getAjaxResult({
				async : true,
				url : ctx + '/login/loginBefore.do',
				data : data.field,
			}, function(result) {
				if (result && result.result) {
					$('#loginForm').submit();
				} else {
					layer.close(index);
					layer.msg(result.msg);
				}
			})
		})
	})
</script>
