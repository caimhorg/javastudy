<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/common.jsp" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
</head>
<body>
类别管理
</body>
<script type="text/javascript" src="${ctx}/public/js/vender/require.js"></script>
<script type="text/javascript">
	require.config(
        {
            paths: {
                'error405': '${ctx}/page/category/error405'
            }
        }
    );
	require(["error405"], function(error) {
		debugger
		var test = error.test;
	});
</script>
