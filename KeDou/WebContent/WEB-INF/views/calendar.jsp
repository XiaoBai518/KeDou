<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>考试日历</title>
	<meta charset="utf-8">
		<link rel="icon" href="${ctx }/img/favicon.ico" type="image/x-icon" />
   		<link rel="shortcut icon" href="${ctx }/img/favicon.ico">
   		<link rel="Bookmark" href="${ctx }/img/favicon.ico">
   		<link rel="SHORTCUT ICON" href="${ctx }/img/favicon.ico"/>
	<link href="${ctx }/css/zui.min.css" rel="stylesheet">
	<link href="${ctx }/css/Calendar/doc.min.css" rel="stylesheet">
	<link href="${ctx }/css//zui.calendar.min.css" rel="stylesheet">
	<script src="${ctx }/js/jquery.zui.js"></script>
	<script src="${ctx }/js/zui.calendar.min.js"></script>
 	<script src="${ctx }/js/zui.min.js"></script>
</head>
<body>
	<div hight="80px" width="100%">
		<h1>&nbsp;&nbsp;&nbsp;2018年</h1><br><h1>&nbsp;&nbsp;&nbsp;考试日历</h1><br>
	</div>
	<br>
	<div class="example"><div id="calendar" class="calendar"></div></div>
	<script type="text/javascript">
  		$('#calendar').calendar();
	</script>
	<script type="text/javascript">
		/* 增加多个考试事件 */  
		var calendar = $('#calendar').data('zui.calendar');
		<c:forEach items="${calendar }" var="c">
		var newEvents =
		[
		 {title: '${c.name}',allDay:true, start: new Date('${c.startdate}'), end: new Date('${c.startdate}')}
		];
		calendar.addEvents(newEvents);
		</c:forEach>
	</script>
</body>

</html>