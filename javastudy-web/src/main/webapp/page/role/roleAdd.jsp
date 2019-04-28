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
		<input type="hidden" id="hiddenRoleId" value="${roleId}">
		<input type="hidden" id="hiddenLayEvent" value="${layEvent}">
		<div class="layui-form-item">
		    <label class="layui-form-label">角色名</label>
		    <div class="layui-input-inline">
		      <input type="text" name="" class="layui-input" style="width:350px;" value="" id="roleName">
		    </div>
  		</div>
  		<div style="width: 90%;height: 75%;margin: 0 auto;overflow: auto;border: 1px solid #e6e6e6;">
  			<div style="margin-left: 15px;margin-top: 15px;">
  				<ul id="menus" class="ztree"></ul>
  			</div>
  		</div>
	</div>
</body>
<script>
var hiddenRoleId;
var hiddenLayEvent;
var setting = {
		data : {
			keep : {parent : true},
			key : {name : "menuName"},
			simpleData : {
				enable: true,
				idKey: "id",
				pIdKey: "parentId",
			},
		},
		view : {
			showIcon : false,
			showLine : false,
		},
		check : {
			enable : true,
			chkStyle : "checkbox"
		},
		async : {
			enable: true,
			dataType : "json",
			url: ctx + "/role/getExistMenu.do",
			otherParam : {"roleId" : function() {return hiddenRoleId;}},
			dataFilter : function (treeId, parentNode, result) {
				if (result && result.result) {
					if (result.role) {
						$('#roleName').val(result.role.roleName);
						if (hiddenLayEvent === 'detail') {
							$('#roleName').attr('disabled', true);
						}
					}
					$.each(result.allmenus, function(i, e) {
						e.open = true;
						if (result.existMenuIds.indexOf(e.id) > -1) {
							e.checked = true;
						}
						if (hiddenLayEvent === 'detail') {
							e.chkDisabled = true;
						}
					})
					return result.allmenus;
				}
			},
		},
		callback : {
			beforeAsync : function(treeId, treeNode) {
				hiddenRoleId = $('#hiddenRoleId').val();
				hiddenLayEvent = $('#hiddenLayEvent').val();
			}
		}
};
$.fn.zTree.init($("#menus"), setting, []);

function getTree() {
	return $.fn.zTree.getZTreeObj("menus");
}

</script>
