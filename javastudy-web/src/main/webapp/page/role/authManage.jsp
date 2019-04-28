<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/common.jsp" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<style>
.layui-table-cell .layui-form-checkbox{
	top: 5px!important;
}
body{
	padding : 0 15px;
}
</style>
</head>
<body>
	<fieldset class="layui-elem-field layui-field-title">
	<legend>权限管理</legend>
	</fieldset>
	<div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">用户名</label>
				<div class="layui-input-inline">
				<input type="text" name="userName" class="layui-input">
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">身份证</label>
				<div class="layui-input-inline">
				<input type="text" name="idCard" class="layui-input">
				</div>
			</div>
			<div class="layui-inline">
				<button id="userSearch" class="layui-btn"><i class="layui-icon">&#xe615;</i>查&nbsp;询</button>
			</div>
		</div>
	</div>
	
	<table id="userTable" lay-filter="userFilter"></table>
	<script type="text/html" id="bar">
  		<a class="layui-btn layui-btn-xs" lay-event="detail">查看</a>
		{{# if (d.id != '1') { }}
		<a class="layui-btn layui-btn-xs layui-btn-normal" lay-event="distribute">分配角色</a>
		{{# } }}
	</script>
	<script type="text/html" id="userStatusTemplet">
		{{# switch(d.userStatus) {
			case 0: }}
		正常
		{{# break; case 1: }}
		冻结
		{{# break; } }}
	</script>
	
</body>
<script>
	layui.use(['form', 'table', 'layer'], function() {
		var table = layui.table;
		var layer = layui.layer;
		var options = {
				id : 'userTableId',//表格容器的索引
			  	elem : '#userTable',//页面元素
			  	cols : [
			  		[
			  			{checkbox : true},
			  			{field : 'userName', title : '用户名', width : 140},
			  			{field : 'phone', title : '手机', width : 150},
			  			{field : 'idCard', title : '身份证', width : 180},
			  			{field : 'createTime', title : '注册时间', width : 180},
			  			{field : 'userStatus', title : '用户状态', width : 140, templet : '#userStatusTemplet'},
			  			{title : '操作', align:'center', toolbar: '#bar'}
			  		]
			  	],
			  	url : ctx + '/user/findPageUsers.do', //默认会自动传递两个参数：?page=1&limit=30（该参数可通过 request 自定义）
			  	method : 'post',
			  	request : {pageName : 'currentPage', limitName : 'pageSize'},
				page : true,
				limit : 10,
				loading : true //加载条 默认true
		}
		//执行渲染
		var tablelns = table.render(options);
		var tableReload = function() {
			tablelns.reload({
				where : {userName: $('input[name="userName"]').val(), idCard: $('input[name="idCard"]').val()}
			})
		}
		var form = layui.form;
		form.render();
		
		//查询
		$('#userSearch').on('click', function() {
			tableReload();
		})
		//工具条监听
		table.on('tool(userFilter)', function(obj) {
			var data = obj.data; //获得当前行数据
			var layEvent = obj.event; //获得 lay-event 对应的值
			var tr = obj.tr; //获得当前行 tr 的DOM对象
			var userId = data.id;
			var userStatus = data.userStatus;
			if (layEvent === 'detail') {
				layer.open({
					type : 2,
					title : '查看',
					offset : '0px',
					content : [ctx + '/role/'+ userId +'/'+ layEvent +'/authInfoIndex.do'],
					area : ['560px', '600px'],
					btn : '关闭',
					btnAlign : 'c',
					yes : function(index, layero) {
						layer.close(index);
					}
				})
			} else if (layEvent === 'distribute') {
				layer.open({
					type : 2,
					title : '分配角色',
					offset : '0px',
					content : [ctx + '/role/'+ userId +'/'+ layEvent +'/authInfoIndex.do'],
					area : ['560px', '600px'],
					btn : ['确认', '取消'],
					btnAlign : 'c',
					yes : function(index, layero) {
						var iframeWin = window[layero.find('iframe')[0]['name']];
						var checkStatus = iframeWin.getCheckedArray();
						if (checkStatus.length <= 0) {
							layer.msg('请先选择角色');
							return false;
						}
						layer.confirm('确认设置这些角色吗', {icon : 3, title : '分配角色'}, function(index) {
							getAjaxResult({
								async : true,
								url : ctx + '/role/'+ userId +'/distributeRole.do',
								data : {roleIds : checkStatus}
							}, function(result) {
								if (result && result.success){
									layer.closeAll();
									layer.msg(result.msg);
								}
							})
						})
					},
					btn2 : function(index, layero) {
						layer.close(index);
					}
				})
			} 
			
		})
	})
	
</script>
