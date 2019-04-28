<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/common.jsp" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<style>
.layui-table-cell .layui-form-checkbox{
	top: 5px!important;
}
</style>
</head>
<body>
	<input type="hidden" value="${layEvent}" id="layEvent">
	<input type="hidden" value="${userId}" id="userId">
	<div style="padding: 0 15px;">
		已分配角色
		<hr>
		<table id="roleAlreadyTable" lay-filter="roleAlreadyFilter"></table>
		<script type="text/html" id="bar">
  		<a class="layui-btn layui-btn-xs" lay-event="detail">查看</a>
		{{# if (d.id != '1') { }}
  		<a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="del">删除</a>
		{{# } }}
		</script>
	</div>
	<div style="padding: 0 15px;">
		角色列表
		<hr>
		<table id="roleNoTable" lay-filter="roleNoFilter"></table>
		<script type="text/html" id="roleNoBar">
  		<a class="layui-btn layui-btn-xs" lay-event="detail">查看</a>
		</script>
	</div>
</body>
<script>
	layui.use(['form', 'table', 'layer'], function() {
		var table = layui.table;
		var layer = layui.layer;
		var hiddenLayEvent = $('#layEvent').val();
		var userId = $('#userId').val();
		var options = {
				id : 'roleAlreadyId',//表格容器的索引
			  	elem : '#roleAlreadyTable',//页面元素
			  	cols : [
			  		[
			  			{field : 'roleName', title : '角色名', width : 140},
			  			{field : 'creatorName', title : '创建人', width : 140},
			  			{title : '操作', align:'center', toolbar: '#bar'}
			  		]
			  	],
			  	url : ctx + '/role/'+ userId +'/findRoleByUserId.do',
			  	method : 'post',
			  	request : {pageName : 'currentPage', limitName : 'pageSize'},
				page : true,
				limit : 5,
				loading : true, //加载条 默认true
				done : function() {
					if ($('#layEvent').val() === 'detail') {
						$('.layui-table-body:eq(0)').find('a:eq(1)').hide();
					}
				}
		}
		var options2 = {
				id : 'roleNoId',//表格容器的索引
			  	elem : '#roleNoTable',//页面元素
			  	cols : [
			  		[
			  		 	{checkbox : true},
			  			{field : 'roleName', title : '角色名', width : 140},
			  			{field : 'creatorName', title : '创建人', width : 140},
			  			{title : '操作', align:'center', toolbar: '#roleNoBar'}
			  		]
			  	],
			  	url : ctx + '/role/findPageRoles.do',
			  	method : 'post',
			  	request : {pageName : 'currentPage', limitName : 'pageSize'},
				page : true,
				limit : 5,
				loading : true, //加载条 默认true
				done : function(res, curr, count) {
					//禁止复选框
					if ($('#layEvent').val() === 'detail') {
						$('.layui-table-header:eq(1)').find('input[type="checkbox"]').each(function() {
							$(this).attr('disabled', 'disabled');
						})
						$('.layui-table-body:eq(1)').find('input[type="checkbox"]').each(function() {
							$(this).attr('disabled', 'disabled');
						})
					}
				}
		}
		//执行渲染
		var tablelns = table.render(options);
		var tableReload = function() {
			tablelns.reload();
		}
		var tablelns2 = table.render(options2);
		var tableReload2 = function() {
			tablelns2.reload();
		}
		var form = layui.form;
		form.render();
		
		//工具条监听
		table.on('tool(roleAlreadyFilter)', function(obj) {
			var data = obj.data; //获得当前行数据
			var layEvent = obj.event; //获得 lay-event 对应的值
			var tr = obj.tr; //获得当前行 tr 的DOM对象
			var roleId = data.id;
			if (layEvent === 'detail') {
				parent.layer.open({
					type : 2,
					title : '查看',
					offset : '50px',
					content : [ctx + '/role/roleNewIndex.do?roleId='+ data.id +'&&layEvent='+ layEvent +'', 'no'],
					area : ['560px', '480px'],
					btn : '关闭',
					btnAlign : 'c',
					yes : function(index, layero) {
						parent.layer.close(index);
					}
				})
			} else if (layEvent === 'del') {
				parent.layer.confirm('确认删除此角色吗', {icon : 3, title : '删除'}, function(index) {
					getAjaxResult({
						async : true,
						url : ctx + '/role/'+ userId +'/'+ roleId +'/delUserRole.do',
					}, function(result) {
						if (result && result.success){
							parent.layer.close(index);
							parent.layer.msg(result.msg);
							tableReload();
						}
					})
				})
			}
		})
		table.on('tool(roleNoFilter)', function(obj) {
			var data = obj.data; //获得当前行数据
			var layEvent = obj.event; //获得 lay-event 对应的值
			var tr = obj.tr; //获得当前行 tr 的DOM对象
			var userId = data.id;
			var userStatus = data.userStatus;
			if (layEvent === 'detail') {
				parent.layer.open({
					type : 2,
					title : '查看',
					offset : '50px',
					content : [ctx + '/role/roleNewIndex.do?roleId='+ data.id +'&&layEvent='+ layEvent +'', 'no'],
					area : ['560px', '480px'],
					btn : '关闭',
					btnAlign : 'c',
					yes : function(index, layero) {
						parent.layer.close(index);
					}
				})
			}
		})
		//获取选中项ids
		window.getCheckedArray = function() {
			var checkedArray = [];
			var checkStatus = table.checkStatus('roleNoId');
			if (checkStatus.data.length > 0) {
				$.each(checkStatus.data, function(i, e) {
					checkedArray.push(e.id);
				})
			}
			return checkedArray;
		}
	})
</script>
