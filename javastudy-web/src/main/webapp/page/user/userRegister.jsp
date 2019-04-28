<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/common.jsp" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>免费注册</title>
<style>
body{
	background: url("${ctx}/public/images/login/night.jpg");
}
#main{
	margin: 0 auto;
	width: 35%;
}
.layui-form-item{
	margin-top: 45px;
	text-align: center;
}
.layui-form-item label{
	width: 100px!important;
}

</style>
</head>
<body>
<div style="width: 100%;height: 50px;">

</div>
<div id="main">
	<form class="layui-form layui-form-pane" action="" id="submitForm">
		<div class="layui-form-item">
		    <label class="layui-form-label">用户名</label>
		    <div class="layui-input-inline">
		      <input type="text" name="userName" lay-verify="required" autocomplete="off" placeholder="请输入用户名" class="layui-input" style="width:350px;">
		    </div>
  		</div>
  		<div class="layui-form-item">
		    <label class="layui-form-label">密码</label>
		    <div class="layui-input-inline">
		      <input type="password" name="password" lay-verify="required" autocomplete="off" placeholder="请输入密码" class="layui-input" style="width:350px;">
		    </div>
  		</div>
  		<div class="layui-form-item">
		    <label class="layui-form-label">确认密码</label>
		    <div class="layui-input-inline">
		      <input type="password" name="confirmPassword" lay-verify="required|confirmPassword" autocomplete="off" placeholder="请再次输入密码" class="layui-input" style="width:350px;">
		    </div>
		  </div>
  		<div class="layui-form-item">
		    <label class="layui-form-label">身份证号</label>
		    <div class="layui-input-inline">
		      <input type="text" name="idCard" lay-verify="required|identity" autocomplete="off" placeholder="" class="layui-input" style="width:350px;" >
		    </div>
  		</div>
  		<div class="layui-form-item">
		    <label class="layui-form-label">邮箱</label>
		    <div class="layui-input-inline">
		      <input type="text" name="email" lay-verify="required|email" autocomplete="off" placeholder="" class="layui-input" style="width:350px;" >
		    </div>
  		</div>
  		<div class="layui-form-item">
		    <label class="layui-form-label">手机号</label>
		    <div class="layui-input-inline">
		      <input type="text" name="phone" lay-verify="required|phone" autocomplete="off" placeholder="" class="layui-input" style="width:350px;" >
		    </div>
  		</div>
  		<div class="layui-form-item">
			<button lay-submit="" lay-filter="userSubmit" type="button" class="layui-btn">注册</button>
			<button type="button" class="layui-btn" style="margin-left: 100px;" onclick="javascript:window.location.href='${ctx}/page/login/login.jsp'">取消</button>
		</div>
	</form>
</div>
</body>
<script type="text/javascript">
	layui.use(['form', 'layer'], function() {
		var form = layui.form;
		var layer = layui.layer;
		form.verify({
			confirmPassword : function(value, item) {
				if (value != $('input[name="password"]').val()) {
					return '必须与所填密码相同';
				}
			}
		})
		form.on('submit(userSubmit)', function(data) {
// 			alert(JSON.stringify(data.field));
			var index = layer.load(2);
			$('#submitForm').ajaxSubmit({
				type : "POST",
				dataType : "JSON",
				url : ctx + "/user/userRegister.do",
				data : {},
				success : function(data) {
					if (data && data.result) {
						layer.close(index);
						layer.msg(data.msg, {anim : 0}, function() {
							location.href = ctx + '/page/login/login.jsp';
						});
					} else {
						layer.close(index);
						layer.msg(data.msg);
					}
				}
			})
		})
	})
</script>
