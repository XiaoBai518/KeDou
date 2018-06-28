<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
             <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
      <%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>  
    <c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>未处理预约</title>
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
	    $("th[class^='untreatedName']").each(function(){
	    	  var name = $(this).html();  
	    	  var mname = name.substr(0,1)+'**';
	    	  $(this).text(mname);  
	    });
	    $("th[class^='untreatedTel']").each(function(){
	    	 var tel = $(this).html();   
	 	    var mtel = tel.substr(0, 3) + '****' + tel.substr(7);  
	 	   $(this).text(mtel);
	    });
	    $("th[class^='createtime']").each(function(){
	    	 var date = new Date( $(this).html());
	    	 $(this).text(dateFtt("yyyy年MM月dd日 hh时mm分",date));
	    });  
	});  
	</script>
</head>
<body>
	<div class="container-fluid">
		<h2 style="text-align: center;">未处理预约</h2><br/>
		<table class="table table-hover">
		  <thead>
		    <tr>
		      <th>编号</th>
		      <th>预约人姓名</th>
		      <th>联系电话</th>
		      <th>预约课程编号</th>
		      <th>预约课程名称</th>
		      <th>价格</th>
		      <th>人数</th>
		      <th>备注</th>
		      <th >生成时间</th>
		      <th>处理</th>
		    </tr>
		  </thead>
		  <tbody>
		  <c:forEach items="${torderlist }" var="t">
		    <tr>
		      <td>${t.torder.id }</td>
		      <th class="untreatedName${t.torder.id }">${t.torder.reserveName }</th>
		      <th class="untreatedTel${t.torder.id }">${t.torder.reserveTel }</th>
		      <th>${t.torder.courseId }</th>
		      <th>${t.course.courseName }</th>
		      <th>${t.torder.price }</th>
		      <th>${t.torder.reserveNum }</th>
		      <th>${t.torder.userNote }</th>
		      <th class="createtime${t.torder.id }">${t.torder.orderCreateTime }</th>
			  <td><a href="${ctx }/torder/treatedTorder?orderId=${t.torder.id}" target="_top"><i class="icon icon-edit"></i></a></td>
		    </tr>
		    </c:forEach>
		  </tbody>
	
		</table>
		<!--分页-->
		<div style="text-align: center;">
<ul class="pager" data-ride="pager" data-page="${page }" data-rec-total="${pagecount }" data-max-nav-count="6" data-elements="prev_icon,nav,next_icon"ata-link-creator="${ctx }/torder/tobusuntreatedtorder?page={page}&p=untreated"></ul>		</div>
	</div>

</body>
</html>