<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<table>
		<tr>
			<td>用户</td>
			<td>操作</td>			
		</tr>
		<tr>
			<td>1</td>
			<td><a href="${ctx }/artcha.jsp?userId=1">插入推荐文章</a></td>
		</tr>
		<tr>
			<td>2</td>
			<td><a href="${ctx }/artcha.jsp?userId=2">插入推荐文章</a></td>
		</tr>
	</table>
</body>
</html>