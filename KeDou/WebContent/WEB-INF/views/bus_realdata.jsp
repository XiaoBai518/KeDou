<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
         <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
      <%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>  
    <c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>添加课程</title>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" type="text/css" href="${ctx }/css/zui.min.css">
	<link rel="stylesheet" type="text/css" href="${ctx }/css/realdata.css">
	<script src="${ctx }/js/jquery.zui.js"></script>
	<script src="${ctx }/js/zui.min.js"></script>
	<script src="${ctx }/js/zui.chart.min.js"></script>
	<script src="${ctx }/js/realdata.js"></script>
</head>
<body>
	<div class="container-fluid" style="padding: 20px; background-color: #F9F8F7;">
		<div class="container" style="background-color: white; padding: 20px; border-radius: 8px;box-shadow: #E5E4E1 2px 2px 12px;">
			<div style="width: 100%;overflow: hidden;">
				<h2 style="text-align: center;color: #2B91DE;">我的店铺</h2><br/>
				<div id="realdata">
					<div class="dataitem">
						<div class="small"><img src="${ctx }/img/order.jpg"></div>
						<div class="smalltext">
							<p class="item">今日预约成功：</p>
							<label class="itemnum">${todayTorderCount }</label>
						</div>
					</div>
					<div class="dataitem">
						<div class="small"><img src="${ctx }/img/follower.jpg"></div>
						<div class="smalltext">
							<p class="item">关注人数：</p>
							<label class="itemnum">526</label>
						</div>
					</div>
					<div class="dataitem">
						<div class="small"><img src="${ctx }/img/visitor.jpg"></div>
						<div class="smalltext">
							<p class="item">今日店铺访问人数：</p>
							<label class="itemnum">${visitNumber }</label>
						</div>
					</div>
					<div class="dataitem">
						<div class="small"><img src="${ctx }/img/historyorder.jpg"></div>
						<div class="smalltext">
							<p class="item">历史预约成功：</p>
							<label class="itemnum">${allTorderCount }</label>
						</div>
					</div>
				</div>
			</div>
			<h2 style="text-align: center;color: #2B91DE;">近一年预约数据</h2><br/>
			<canvas id="myChart" width="780" height="400"></canvas>
		</div>
	</div>
		<script type="text/javascript">
	
		realdata(${gsonSuperChart});
		</script>
</body>
</html>