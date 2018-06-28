<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
         <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
      <%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>  
    <c:set var="ctx" value="${pageContext.request.contextPath}" />
        <c:set var="courseImgPath" value="/file/course" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>课程列表</title>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" type="text/css" href="${ctx }/css/zui.min.css">
	<script src="${ctx }/js/jquery-2.1.1.min.js"></script>
		<script src="${ctx }/js/jquery.zui.js"></script>
		<script src="${ctx }/js/zui.min.js"></script>
</head>
<body>
	<div class="container-fluid">
		<h2 style="text-align: center;">所有课程</h2><br/>
		<table class="table table-hover">
		  <thead>
		    <tr>
		      <th>编号</th>
		      <th>课程名称</th>
		      <th>课程描述</th>
		      <th>课程类型</th>
		      <th>课时</th>
		      <th>价格</th>
		      <th>成功预约</th>
		      <th>编辑</th>
		      <th>删除</th>
		    </tr>
		  </thead>
		  <tbody>
		  <c:forEach items="${Scourselist }" var="c">
		  	<tr>
		      <td>${c.course.courseId }</td>
		      <td>${c.course.courseName }</td>
		      <td>${c.course.description }</td>
		      <td>${c.courseType }</td>
		      <td>48</td>
		      <td>${c.course.coursePrice }</td>
		      <td>521</td>
			  <td><a href="#"><i class="icon icon-edit"></i></a></td>
			  <td><a href="#"><i class="icon icon-trash"></i></a></td>
		    </tr>
		  </c:forEach>
		    
		    </tbody>
		</table>
		<!--分页-->
		<div style="text-align: center;">
			<ul class="pager" data-ride="pager" data-page="${page }" data-rec-total="${pagecount }" data-max-nav-count="6" data-elements="prev_icon,nav,next_icon"ata-link-creator="${ctx }/course/bypage?page={page}"></ul>
		</div>
	</div>
		
</body>
</html>