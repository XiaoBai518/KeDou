<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
         <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
    <c:set var="ctx" value="${pageContext.request.contextPath}" />
    <c:set var="courseImgPath" value="/file/course" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
		<meta charset="UTF-8">
		<title></title>
		<script src="${ctx }/js/jquery.zui.js"></script>
		<link rel="stylesheet" type="text/css" href="${ctx}/css/personal.css"/>
		<link rel="stylesheet" href="${ctx}/css/zui.min.css"/>
		<script type="text/javascript" src="${ctx}/js/zui.min.js"></script>
			<script src="${ctx }/js/personal.js"></script>
</head>
<body>
<!-- 有订单 -->
	<c:if test="${not empty yuyue }">
			<form action="${ctx}/torder/batchdelete?page=${page }" method="post">		
		<table cellspacing="4" cellpadding="0" class="table">
			<tr>
				<th class="tr-th3" style="display:table-cell; vertical-align:middle">
				<input type="checkbox" style="margin-right: 20px;zoom:80%;"id="allCheckBox"  value="全选" >
				</th>			
				<th class="tr-th1" style="vertical-align:middle;">课程</th>
				<th class="tr-th1" style="vertical-align:middle;">课程名称</th>
				<th class="tr-th1" style="vertical-align:middle;">价格</th>
				<th class="tr-th2" style="vertical-align:middle;">操作</th>
			</tr>
			<c:forEach items="${yuyue }" var="p">
			<tr class="test" id="${p.course.courseId }">
					<th class="tr-td1" style="display:table-cell; vertical-align:middle"><input type="checkbox" name="tempString"  style="margin-right: 10px;margin-left: 18px;" value="${p.course.courseId }"></th>
					<td class="tr-td" onclick="window.open('https://www.baidu.com');" style="display:table-cell; vertical-align:middle"><img src="${courseImgPath }/${p.course.courseImg}" width="100%" height="100%"></td>
					<td class="tr-td" style="display:table-cell; vertical-align:middle">${p.course.courseName}</td>
					<td class="tr-td" style="display:table-cell; vertical-align:middle">${p.course.coursePrice }</td>
					<th  class="tr-td1" id="${p.course.courseId }" style="display:table-cell; vertical-align:middle"><a href="${ctx}/torder/delete?courseid=${p.course.courseId}&page=${page }"><div class="delete"><i class="icon icon-trash icon-1x"></i></div></a></th>
					
			</tr>
			</c:forEach>
		</table>
	
	<div class="page">
			<div class="tbutton">
			<button class="btn btn-primary" name="delete" type="submit" disabled="disabled">删除</button>
			</div>
			<ul id="myPager" class="pager" data-ride="pager" data-page="${page}" data-rec-Total="${pagecount }" data-rec-Per-Page="4" data-link-creator="${ctx }/torder/page?page={page}&totalCount={recTotal}" ></ul>
		</div>	
	</form>	
</c:if>
	<!-- 没有订单 -->
	<c:if test="${ empty yuyue }">
		<div class="nonecourse">
				
				<div class="nonecourseImg">
					<img  src="${ctx}/img/none.jpg" width="100%" height="100%">
				</div>
				<div class="nonecourseText">没有预约课程。。。</div>
		</div>
	
	</c:if>
</body>
</html>