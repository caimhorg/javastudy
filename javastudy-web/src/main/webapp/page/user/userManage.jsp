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
	<legend>用户管理</legend>
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
				<button id="batchDel" class="layui-btn layui-btn-danger" style="">批量删除</button>
			</div>
		</div>
	</div>
	
	<table id="userTable" lay-filter="userFilter"></table>
	<script type="text/html" id="bar">
  		<a class="layui-btn layui-btn-xs" lay-event="detail">查看</a>
		{{# if (d.userStatus == 0 && d.id != '1') { }}
  		<a class="layui-btn layui-btn-xs layui-btn-warm" lay-event="freeze">冻结</a>
		{{# } else if (d.userStatus == 1 && d.id != '1') { }}
		<a class="layui-btn layui-btn-xs layui-btn-normal" lay-event="unfreeze">解冻</a>
		{{# } 
			if (d.id != '1') {	}}
  		<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
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
//	 		  	height : 400,
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
// 			    where: {userName: $('input[name="userName"]').val(), idCard: $('input[name="idCard"]').val()}, //如果无需传递额外参数，可不加该参数
			  	method : 'post',
			  	request : {pageName : 'currentPage', limitName : 'pageSize'},
//	 		  	response: {} 如果无需自定义数据响应名称，可不加该参数
//	 			data: [{"userName" : "张三"}], //死数据用于测试模板
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
		//批量删除
		$('#batchDel').on('click', function() {
			var checkStatus = table.checkStatus('userTableId');
			console.log(checkStatus.data) //获取选中行的数据
			console.log(checkStatus.data.length) //获取选中行数量，可作为是否有选中行的条件
			if (checkStatus.data.length <= 0) {
				layer.msg('请至少选中一行');
			} else {
				var ids = [];
				var adminFlg = false;
				$.each(checkStatus.data, function(i, e) {
					if (e.id == '1') {
						adminFlg = true;
						layer.msg('超级管理员无法删除');
						return false;
					}
					ids.push(e.id);
				})
				if (!adminFlg) {
					layer.confirm('确认删除这些项吗', {icon : 3, title : '批量删除'}, function(index) {
						getAjaxResult({
							async : true,
							url : ctx + '/user/userDelete.do',
							data : {ids : ids}
						}, function(result) {
							if (result && result.success){
								layer.close(index);
								layer.msg(result.msg);
								tableReload();
							}
						})
					})
				}
			}
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
					offset : '100px',
					content : [ctx + '/user/'+ userId +'/userInfoIndex.do', 'no'],
					area : ['560px', '450px'],
					title : '查看'
				})
			} else if (layEvent === 'freeze' || layEvent === 'unfreeze') {
				var content = layEvent === 'freeze' ? '确定要冻结该用户吗' : layEvent === 'unfreeze' ? '确定要解冻该用户吗' : '';
				var title = layEvent === 'freeze' ? '冻结' : layEvent === 'unfreeze' ? '解冻' : '';
				layer.confirm(content, {icon : 3, title : title}, function(index) {
					getAjaxResult({
						async : true,
						url : ctx + '/user/'+ userId +'/'+ userStatus +'/freeze.do',
					}, function(result) {
						if (result && result.success){
							layer.close(index);
							layer.msg(result.msg);
							tableReload();
						}
					})
				})
			} else if (layEvent === 'del') {
				var ids = [];
				ids.push(data.id);
				layer.confirm('确认删除吗', {icon : 3, title : '删除'}, function(index) {
					getAjaxResult({
						async : true,
						url : ctx + '/user/userDelete.do',
						data : {ids : ids}
					}, function(result) {
						if (result && result.success){
							layer.close(index);
							layer.msg(result.msg);
							tableReload();
						}
					})
				})
			}
			
		})
	})
	
</script>
