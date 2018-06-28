<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
           <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
        <c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <script src="${ctx }/js/jquery.zui.js"></script>
    <script src="${ctx }/js/zui.min.js"></script>
     <link rel="stylesheet" type="text/css" href="${ctx }/css/zui.min.css">
     <script type="text/javascript">
     	$(function(){
     		new $.zui.Messager('您没有权限', {
     		    icon: 'warning-sign',
     		   type: 'warning', // 定义颜色主题
     		    placement: 'top', // 定义显示位置
     		}).show();
     	});
     </script>
<title>Insert title here</title>
</head>
<body>
您没有权限
</body>
</html>