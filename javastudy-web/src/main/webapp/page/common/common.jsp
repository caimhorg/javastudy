<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setAttribute("ctx", request.getContextPath());%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${ctx}/public/layui/v2.2.2/css/layui.css">
<link rel="stylesheet" href="${ctx}/public/js/zTree/css/zTreeStyle/zTreeStyle.css">
</head>
<script type="text/javascript" src="${ctx}/public/layui/v2.2.2/layui.js" ></script>
<script type="text/javascript" src="${ctx}/public/js/jquery.js" ></script>
<script type="text/javascript" src="${ctx}/public/js/jquery.form.js" ></script>
<script src="${ctx}/public/js/zTree/js/jquery.ztree.core.min.js" ></script>
<script src="${ctx}/public/js/zTree/js/jquery.ztree.exedit.js" ></script>
<script src="${ctx}/public/js/zTree/js/jquery.ztree.exhide.js" ></script>
<script src="${ctx}/public/js/zTree/js/jquery.ztree.excheck.js" ></script>
<script>
var ctx = '${ctx}';

var getAjaxResult = function(args, callback) {
	$.ajax({
		async : !args.async ? args.async : true,
		type : args.type ? args.type : "POST",
		dataType : args.dataType ? args.dataType : "JSON",
		url : args.url,
		data : args.data,
		success : function(result) {
			callback(result);
		}
	})
}
</script>
