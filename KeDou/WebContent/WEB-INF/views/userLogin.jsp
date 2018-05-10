<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
    <c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>index</title>
    <link rel="stylesheet" type="text/css" href="${ctx }/css/LoginRegiste.css"/>
</head>
<body bgcolor="#AEEEEE">
<div id="one">	
	<!--中间内敛框架的变化部分 -->
	<div id="four_ifr">
		<iframe src="${ctx }/${page }"  frameborder="0" width="100%" scrolling="no" height="100%" name="box"></iframe>
	</div>	
	<!--底部第三方登录-->
	<div id="fond_style2">
		<table cellspacing="10px">
			<tr>
				<td>其他登陆方式</td>
				<td><img src="${ctx }/img/qq3.png"></td> <!--qq登陆-->
				<td><img src="${ctx }/img/weixin.png"></td><!--微信登陆-->
				<td><img src="${ctx }/img/weibo.png"></td><!--微博登陆-->
			</tr>
		</table>
	</div>
</div>
</body>
</html>