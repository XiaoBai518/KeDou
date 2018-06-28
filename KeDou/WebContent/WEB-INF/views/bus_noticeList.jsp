<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
             <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
      <%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>  
    <c:set var="ctx" value="${pageContext.request.contextPath}" />
        <c:set var="courseImgPath" value="/file/course" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>公告列表</title>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" type="text/css" href="${ctx }/css/zui.min.css">
	<script src="${ctx }/js/jquery-2.1.1.min.js"></script>
		<script src="${ctx }/js/jquery.zui.js"></script>
		<script src="${ctx }/js/zui.min.js"></script>
		<script src="${ctx }/js/DateUtil.js"></script>
		<script type="text/javascript">
		$(function() {  
			  
		     var date = new Date($('.createtime').html());
		    $('.createtime').text(dateFtt("yyyy年MM月dd日 hh时mm分",date));
		});
		</script>
</head>
<body>
	<div class="container-fluid">
		<h2 style="text-align: center;">所有公告</h2><br/>
		<table class="table table-hover">
		  <thead>
		    <tr>
		      <th>编号</th>
		      <th>标题</th>
		      <th>内容</th>
		      <th>已读人数</th>
		      <th>发布时间</th>
		      <th>编辑</th>
		      <th>删除</th>
		    </tr>
		  </thead>
		  <tbody>
		  <c:forEach items="${noticeList}" var="n">
		    <tr>
		      <td>${n.id }</td>
		      <td>${n.title }</td>
		      <td>${n.content }</td>
		      <td>26</td>
		      <td class="createtime">${n.createTime }</td>
			  <td><a href="#"><i class="icon icon-edit"></i></a></td>
			  <td><a href="#"><i class="icon icon-trash"></i></a></td>
		    </tr>
		    </c:forEach>
		  </tbody>
		</table>
		<!--分页-->
		<div style="text-align: center;">
			<ul class="pager" data-ride="pager" data-page="2" data-rec-total="89" data-max-nav-count="4" data-elements="prev_icon,nav,next_icon"></ul>
		</div>
	</div>

</body>
</html>