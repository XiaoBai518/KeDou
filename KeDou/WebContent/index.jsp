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
	<a href="${ctx }/busregistry1.jsp">进入商家注册流程</a><br/>
	<a href="${ctx }/business/findAll">查询所有商家</a><br/>
	<a href="${ctx }/announcement/getAll">查询所有公告</a><br/>
	<a href="${ctx }/insertart.jsp">查询所有用户</a><br/>
	<a href="${ctx }/article/findAll">查询所有推荐文章</a>
</body>
</html>