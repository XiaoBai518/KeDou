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
			<td>busID</td>
			<td>商家账号</td>
			<td>商家邮箱</td>
			<td>联系人</td>
			<td>插入公告</td>
		<tr>
		<c:forEach items="${list }" var="list">
		<tr>
			<td>${list.busId }</td>
			<td>${list.busAccount }</td>
			<td>${list.busEmail }</td>
			<td>${list.busContact }</td>
			<td><a href="${ctx }/accinsert.jsp?busId=${list.busId }">插入公告</a></td>
		</tr>
		</c:forEach>
	</table>
</body>
</html>