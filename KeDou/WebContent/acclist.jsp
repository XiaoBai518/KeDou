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
			<td>accID</td>
			<td>标题</td>
			<td>内容</td>
			<td>发布时间</td>
			<td>浏览次数</td>
			<td>发布人</td>
		<tr>
		<c:forEach items="${acclist }" var="list">
		<tr>
			<td>${list.accId }</td>
			<td>${list.accTitle }</td>
			<td>${list.accContent }</td>
			<td>${list.accPublish }</td>
			<td>${list.accRead }</td>
			<td>${list.busId }</td>
			<td><a href="${ctx }/announcement/toedit?accId=${list.accId }">修改公告</a></td>
			<td><a href="${ctx }/announcement/del?accId=${list.accId }">删除公告</a></td>
		</tr>
		</c:forEach>
	</table>
</body>
</html>