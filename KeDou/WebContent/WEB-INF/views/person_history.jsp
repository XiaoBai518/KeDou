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
<!-- 有访问历史 -->
	<c:if test="${not empty courselist }">
<form action="${ctx}/userhistory/batchdelete?page=${page }" method="post">
		<table cellspacing="4" cellpadding="0" class="table">
					
			<tr>
				<th class="tr-th3" style="display:table-cell; vertical-align:middle">
				<input type="checkbox" style="margin-right: 20px;zoom:80%;"id="allCheckBox"  value="全选" onclick="selectAll()"/>
				</th>			
				<th class="tr-th1" style="vertical-align:middle;">课程</th>
				<th class="tr-th1" style="vertical-align:middle;">课程名称</th>
				<th class="tr-th1" style="vertical-align:middle;">价格</th>
				<th class="tr-th2" style="vertical-align:middle;">操作</th>
			</tr>
			<c:forEach items="${courselist }" var="p">
			<tr class="test" id="${p.courseId }">
					<th class="tr-td1" style="display:table-cell; vertical-align:middle"><input type="checkbox" name="tempString"  style="margin-right: 10px;margin-left: 18px;" value="${p.courseId }" onclick="selectSingle()"/></th>
					<td class="tr-td" onclick="window.open('https://www.baidu.com');" style="display:table-cell; vertical-align:middle"><img src="${courseImgPath }/${p.courseImg}" width="100%" height="100%"></td>
					<td class="tr-td" style="display:table-cell; vertical-align:middle">${p.courseName}</td>
					<td class="tr-td" style="display:table-cell; vertical-align:middle">${p.coursePrice }</td>
					<th  class="tr-td1" id="${p.courseId }" style="display:table-cell; vertical-align:middle"><a href="${ctx}/userhistory/delete?courseid=${p.courseId}&page=${page }"><div class="delete"><i class="icon icon-trash icon-1x"></i></div></a></th>
					
			</tr>
			</c:forEach>
				
		</table>
			</form>
		<div class="page">
			<div class="tbutton">
			<button class="btn btn-primary" name="delete" type="submit" disabled="disabled">删除</button>
			</div>
			<ul id="myPager" class="pager" data-ride="pager" data-page="${page}" data-rec-Total="${pagecount }" data-rec-Per-Page="4" data-link-creator="${ctx }/userhistory/page?page={page}&totalCount={recTotal}" ></ul>
		</div>	
	</c:if>	
			<!-- 无访问历史 -->
	<c:if test="${ empty courselist }">
		<div class="nonecourse">
				
				<div class="nonecourseImg">
					<img  src="${ctx}/img/none.jpg" width="100%" height="100%">
				</div>
				<div class="nonecourseText">没有访问历史。。。</div>
		</div>
	
	</c:if>
</body>
</html>