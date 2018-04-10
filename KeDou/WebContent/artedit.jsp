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
	<form action="${ctx }/article/edit" method="get">
		文章ID:<input type="text" name="artId" value="${art.artId }"/><br/>
		文章内容:<input type="text" name="title" value="${art.title }"/><br/>
		文章标题：<input type="text" name="content" value="${art.content }"/><br/>
		推荐时间：<input type="text" name="publish" value="${art.publish }"/><br/>
		被推荐用户：<input type="text" name="userId" value="${art.userId }"/><br/>
		<input type="submit" value="保存"/>
	</form>
</body>
</html>