<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
         <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
    <c:set var="ctx" value="${pageContext.request.contextPath}" />
          <%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="UTF-8">
		<title>个人中心</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/css/personal.css"/>
	</head>
	<body>

		<div class="body">
			<div class="text">个人信息</div>
			<hr class="hr"/>
			<div class="person_name">
				<div class="name_left">昵称</div>
				<div class="name_right">${user.userName }</div>
			</div>
			<div class="person_name">
				<div class="name_left">性别</div>
				<div class="name_right">${user.gender }</div>
			</div>
			<div class="person_name">
				<div class="name_left">个人描述</div>
				<div class="name_right">${user.userDiscription }</div>
			</div>	
		</div>

	</body>
</html>