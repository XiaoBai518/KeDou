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
			<td>公告ID</td>
			<td>内容</td>
			<td>标题</td>
			<td>推荐时间</td>
			<td>被推荐用户</td>
			<td>操作</td>
		</tr>
		<c:forEach items="${artlist }" var="list">
		<tr>
			<td>${list.artId }</td>
			<td>${list.title }</td>
			<td>${list.content }</td>
			<td>${list.publish }</td>
			<td>${list.userId }</td>
			<td><a href="${ctx }/article/toedit?artId=${list.artId}">修改</a></td>
			<td><a href="${ctx }/article/del?artId=${list.artId}">修改</a></td>
		</tr>
		</c:forEach>
	</table>
</body>
</html>