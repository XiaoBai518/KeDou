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
	<form action="${ctx }/announcement/add" method="post">	
		<input type="hidden" name="busId" value=<%=Integer.parseInt(request.getParameter("busId")) %> /> 
		标题：<input type="text" name="accTitle"/><br/>
		内容：<input type="text" name="accContent"/><br/>
		<input type="submit" value="完成"/>
	</form>	
		
</body>
</html>