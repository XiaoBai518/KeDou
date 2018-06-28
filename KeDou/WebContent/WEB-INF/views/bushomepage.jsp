<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
		<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>店铺首页</title>
		<link rel="icon" href="${ctx }/img/favicon.ico" type="image/x-icon" />
   		<link rel="shortcut icon" href="${ctx }/img/favicon.ico">
   		<link rel="Bookmark" href="${ctx }/img/favicon.ico">
   		<link rel="SHORTCUT ICON" href="${ctx }/img/favicon.ico"/>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" type="text/css" href="${ctx }/css/homepage.css">
	<link href="${ctx }/css/zui.min.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="${ctx }/css/zui-theme.css">
	<script src="${ctx }/js/jquery.zui.js"></script>
    <!-- ZUI Javascript组件 -->
    <script src="${ctx }/js/zui.min.js"></script>
<title>Insert title here</title>
</head>
<body>
	<div>
		<div id="navigation"></div>
		<div id="shop_bannar">
			<img src="${ctx }/img/shop_banner.png">
		</div>
		<div class="container">
		<div id="content">
			<div id="shop_navigation">
				<a href="${ctx}/course/toBusinessHomes?businessId=${businessId}" id="pro_category">所有课程</a>
				<a href="${ctx}/course/toBusinessHomes?businessId=${businessId}" class="nav_item">首页</a>
				<c:forEach items="${courseTypeList }" var="cl" >
					<a href="${ctx }/course/TypeCoursemore?businessId=${businessId}&courseTypeId=${cl.id}" class="nav_item">${cl.typeName }</a>
				</c:forEach>
			</div>
			<c:forEach items="${courseList }" var="cl" >
				<div id="pro_content">
					<div class="pro_head">
						<div class="pro_title"><strong style="color: white;">${cl.key.typeName }</strong></div>
						<div class="more"><a href="${ctx }/course/TypeCoursemore?businessId=${businessId}&courseTypeId=${cl.key.id}"><strong style="color: white;">more+</strong></a></div>
					</div>
					<div class="prolist">
						<c:forEach items="${cl.value}" var="list">
							<div class="pro">
								<div class="proPicture"><a href="#"><img src="/file/course/${list.courseImg }"></a></div>
								<div class="price">￥${list.coursePrice }</div><div class="sell_count">已售：${list.courseSold }件</div>
								<div class="proName"><a href="#">${list.courseName}</a></div>
								<div class="proInfo"><a href="#">简介：${list.description }</a></div>
								<a href="#" class="pro_order">立即预约</a>
							</div>
						</c:forEach>
					</div>
				</div>
			</c:forEach>
		</div>
		</div>
		<div id="footer"></div>
	</div>

</body>
</html>