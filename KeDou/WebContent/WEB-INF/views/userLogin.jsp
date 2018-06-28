<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
    <c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>课兜儿网——登录注册</title>
		<link rel="icon" href="${ctx }/img/favicon.ico" type="image/x-icon" />
   		<link rel="shortcut icon" href="${ctx }/img/favicon.ico">
   		<link rel="Bookmark" href="${ctx }/img/favicon.ico">
   		<link rel="SHORTCUT ICON" href="${ctx }/img/favicon.ico"/>
    <link rel="stylesheet" type="text/css" href="${ctx }/css/LoginRegiste.css"/>
</head>
<body style="background: url(${ctx}/img/LoginBack.jpg) no-repeat left top; background-size:100%;">
<div id="one">	
	<!--中间内敛框架的变化部分 -->
	<div id="four_ifr">
		<iframe src="${ctx }${url}${error}" frameborder="0" width="100%" scrolling="no" height="100%" name="box"></iframe>
	</div>	
</div>
</body>
</html>