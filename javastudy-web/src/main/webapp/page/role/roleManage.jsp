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
	<legend>角色管理</legend>
	</fieldset>
	<div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">角色名</label>
				<div class="layui-input-inline">
				<input type="text" name="roleName" class="layui-input">
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">创建人</label>
				<div class="layui-input-inline">
				<input type="text" name="creatorName" class="layui-input">
				</div>
			</div>
			<div class="layui-inline">
				<button id="roleSearch" class="layui-btn"><i class="layui-icon">&#xe615;</i>查&nbsp;询</button>
				<button id="roleNew" class="layui-btn"><i class="layui-icon">&#xe61f;</i>新&nbsp;增</button>
				<button id="batchDel" class="layui-btn layui-btn-danger" style="">批量删除</button>
			</div>
		</div>
	</div>
	
	<table id="roleTable" lay-filter="roleFilter"></table>
	<script type="text/html" id="bar">
		{{# if (d.id != '1') { }}
  		<a class="layui-btn layui-btn-xs layui-btn-normal" lay-event="edit">修改</a>
  		<a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="del">删除</a>
		{{# }else { }}
		<a class="layui-btn layui-btn-xs" lay-event="detail">查看</a>
		{{#  } }}
	</script>
	
</body>
<script>
	layui.use(['form', 'table', 'layer'], function() {
		var table = layui.table;
		var layer = layui.layer;
		var options = {
				id : 'roleTableId',//表格容器的索引
			  	elem : '#roleTable',//页面元素
			  	cols : [
			  		[
			  			{checkbox : true},
			  			{field : 'roleName', title : '角色名', width : 200},
			  			{field : 'creatorName', title : '创建人', width : 250},
			  			{field : 'createTime', title : '创建时间', width : 250},
			  			{title : '操作',  align:'center', toolbar: '#bar'}
			  		]
			  	],
			  	url : ctx + '/role/findPageRoles.do', //默认会自动传递两个参数：?page=1&limit=30（该参数可通过 request 自定义）
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
				where : {roleName: $('input[name="roleName"]').val(), creatorName: $('input[name="creatorName"]').val()}
			})
		}
		var form = layui.form;
		form.render();
		
		//查询
		$('#roleSearch').on('click', function() {
			tableReload();
		})
		//批量删除
		$('#batchDel').on('click', function() {
			var checkStatus = table.checkStatus('roleTableId');
			if (checkStatus.data.length <= 0) {
				layer.msg('请至少选中一行');
			} else {
				var ids = [];
				var adminFlg = false;
				$.each(checkStatus.data, function(i, e) {
					if (e.id == '1') {
						adminFlg = true;
						layer.msg('超级管理员角色无法删除');
						return false;
					}
					ids.push(e.id);
				})
				if (!adminFlg) {
					layer.confirm('确认删除这些项吗', {icon : 3, title : '批量删除'}, function(index) {
						getAjaxResult({
							async : true,
							url : ctx + '/role/roleDelete.do',
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
		//新建
		$('#roleNew').on('click', function() {
			var roleId = "";
			layer.open({
				type : 2,
				title : '新增角色',
				offset : '50px',
				content : [ctx + '/role/roleNewIndex.do?roleId='+ roleId +'', 'no'],
				area : ['560px', '480px'],
				btn : ['确认', '取消'],
				btnAlign : 'c',
				yes : function(index, layero) {
					roleNew(index, layero, "");
				},
				btn2 : function(index, layero) {
					layer.close(index);
				}
			})
		})
		//工具条监听
		table.on('tool(roleFilter)', function(obj) {
			var data = obj.data; //获得当前行数据
			var layEvent = obj.event; //获得 lay-event 对应的值
			var tr = obj.tr; //获得当前行 tr 的DOM对象
			if (layEvent === 'edit') {
				layer.open({
					type : 2,
					title : '修改',
					offset : '50px',
					content : [ctx + '/role/roleNewIndex.do?roleId='+ data.id +'&&layEvent='+ layEvent +'', 'no'],
					area : ['560px', '480px'],
					btn : ['确认', '取消'],
					btnAlign : 'c',
					yes : function(index, layero) {
						roleNew(index, layero, data.id);
					},
					btn2 : function(index, layero) {
						layer.close(index);
					}
				})
			} else if (layEvent === 'del') {
				var ids = [];
				ids.push(data.id);
				layer.confirm('确认删除吗', {icon : 3, title : '删除'}, function(index) {
					getAjaxResult({
						async : true,
						url : ctx + '/role/roleDelete.do',
						data : {ids : ids}
					}, function(result) {
						if (result && result.success){
							layer.close(index);
							layer.msg(result.msg);
							tableReload();
						}
					})
				})
			} else if (layEvent === 'detail') {
				layer.open({
					type : 2,
					title : '查看',
					offset : '50px',
					content : [ctx + '/role/roleNewIndex.do?roleId='+ data.id +'&&layEvent='+ layEvent +'', 'no'],
					area : ['560px', '480px'],
					btn : '关闭',
					btnAlign : 'c',
					yes : function(index, layero) {
						layer.close(index);
					}
				})
			}
		})
		//新建角色
		function roleNew(index, layero, roleId) {
			var roleName = layer.getChildFrame('#roleName', index).val();
			if (!roleName) {
				layer.msg('角色名不能为空');
				return false;
			}
			var iframeWin = window[layero.find('iframe')[0]['name']];
			var tree = iframeWin.getTree();
			var selectedNodes = tree.getCheckedNodes();
			if (!selectedNodes || selectedNodes.length <= 0) {
				layer.msg('请选择菜单');
				return false;
			}
			var menuIds = [];
			$.each(selectedNodes, function(i, e) {
				menuIds.push(e.id);
			})
			getAjaxResult({
				async : true,
				url : ctx + '/role/createOrUpdateRole.do',
				data : {menuIds : menuIds, roleName : roleName, roleId : roleId}
			}, function(result) {
				if (result && result.success){
					layer.close(index);
					layer.msg(result.msg);
					tableReload();
				}
			})
		}
	})
	
</script>
