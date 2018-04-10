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
<form action="${ctx }/business/registry2" method="post">
	商家名称：<input type="text" name="busName"/><br/>
	营业执照号：<input type="text" name="busLicense"/><br/>
	公司地址：<input type="text" name="busAddress"/><br/>
	公司法人：<input type="text" name="busCorporate"/><br/>
	<input type="submit" value="注册"/>
	</form>
</body>
</html>