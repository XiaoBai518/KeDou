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

<form action="${ctx }/business/registry1" method="post">
	联系人：<input type="text" name="busContact"/><br/>
	电话：<input type="text" name="busTel"/><br/>
	邮箱：<input type="text" name="busEmail"/><br/>
	<input type="submit" value="下一步"/>
	</form>
</body>
</html>