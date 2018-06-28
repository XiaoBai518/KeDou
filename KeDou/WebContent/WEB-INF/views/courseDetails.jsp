<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
         <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
    <c:set var="ctx" value="${pageContext.request.contextPath}" />
    <c:set var="courseImgPath" value="/file/course" />
        <c:set var="userImgPath" value="/file/personal" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>课兜儿网——商品详情页</title>
		<link rel="icon" href="${ctx }/img/favicon.ico" type="image/x-icon" />
   		<link rel="shortcut icon" href="${ctx }/img/favicon.ico">
   		<link rel="Bookmark" href="${ctx }/img/favicon.ico">
   		<link rel="SHORTCUT ICON" href="${ctx }/img/favicon.ico"/>
		<meta charset="utf-8" name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" type="text/css" href="${ctx }/css/coursedetail.css">
		<link rel="stylesheet" type="text/css" href="${ctx }/css/banner.css">
		<link rel="stylesheet" href="${ctx }/css/zui.min.css">
		<link rel="stylesheet" type="text/css" href="${ctx }/css/autocomplete.css">
		 
		<script src="${ctx }/js/jquery.zui.js"></script>
		<script src="${ctx }/js/zui.min.js"></script>
    	<script src="${ctx }/js/tongji.js"></script>
    	    <script src="${ctx }/js/jquery.autocomplete.min.js"></script>
        <script src="${ctx }/js/autocomplete.js"></script>
		<script type="text/javascript">
			$(function(){
				var time=Date.parse(new Date());
				 var date = new Date($('#expiryDate').html());
				
				 $('#expiryDate').html(parseInt((date-time)/(1000*60*60*24)))
			});
			window.onbeforeunload = function(event) {
		        var dataArr = {
		            'url' : location.href,
		            'time' : second,
		            'refer' : getReferrer(),
		            'timeIn' : Date.parse(new Date()),
		            'timeOut' : Date.parse(new Date()) + (second * 1000),
		            'courseId': ${course.courseId }
		        };
		    	$.ajax({
			  		url : "/KeDou/common/ajaxtest",
			  		type : "GET",
			  		data :{log:JSON.stringify(dataArr)},
			  	}); 
		};
		</script>
	</head> 
	<body>
		<div class="container-fluid" style="background-color: #F9F8F7;">
			<div id="header">
				<!--logo-->
				<div id="logo">
					<img src="${ctx }/img/logo2.png"/>
				</div>
				<!--搜索框-->
				<div id="find">
					<form action="${ctx }/course/searchFirstCourseList" id="searchForm" method="get">
						<div class="input-group">
							<div class="input-control search-box search-box-circle has-icon-left has-icon-right search-example" id="searchboxExample">
								<input id="inputSearchExample3" type="search" name="search"  class="form-control search-input" placeholder="搜索"/>
							
								<label for="inputSearchExample3" class="input-control-icon-left search-icon">
									<i class="icon icon-search" style="line-height: 2;"></i>
								</label>
							</div>
							<span class="input-group-btn">
								<button class="btn btn-primary" type="submit">搜索</button>
							</span>
						</div>
					</form>					
				</div>
				<!--定位-->
				<div id="locate">
					<i class="icon icon-map-marker icon-2x" ></i>
					<p id="address">${sessionScope.userAddress.address }[<a href="${ctx }/useraddress/toaddress" style="color:#3280fc; text-decoration: none;">切换地址</a>]</p>
				</div>
				<!--个人头像-->
				<div id="userPad">
						 <c:if test="${ not empty sessionScope.loginUser}">
					 	<div id="myPhoto">
						<img src="${userImgPath}/${sessionScope.loginUser.userIcon}"/>
						</div>
						<div class="popover bottom" id="myPopover">
							<div class="arrow"></div>
								<h3 class="popover-title"><a href="${ctx }/user/person"><i class="icon icon-home"></i>&nbsp&nbsp个人中心</a></h3>
								<div class="popover-content">
									<ul>
										<li class="menu-li"><a href="${ctx }/user/person"><i class="icon icon-envelope"></i>&nbsp&nbsp我的消息</a></li>
										<li class="menu-li"><a href="${ctx }/user/person?address=1"><i class="icon icon-history"></i>&nbsp&nbsp我的足迹</a></li>
										<li class="menu-li"><a href="${ctx }/user/person?address=2"><i class="icon icon-check-board"></i>&nbsp&nbsp我的预约</a></li>
										<li class="menu-li"><a href="${ctx }/user/person?address=3"><i class="icon icon-star"></i>&nbsp&nbsp我的收藏</a></li>
									</ul>
								</div>
						</div>
					 </c:if>
					 <c:if test="${ empty sessionScope.loginUser}">
					 	<div id="loginregiste">
							<a href="${ctx }/user/toiframe"><font size="4">登陆 </font></a><font size="5" color="blue"> / </font><a href="${ctx }/user/toiframe?option=regist"><font size="4"> 注册</font></a>
						</div>
					 </c:if>
					</div>
				</div>
				
				<!--导航条-->
				<div id="navigation">
				<ul class="nav nav-secondary">
						<li><a href="${ctx }/common/toindex" class="nav-a">首页</a></li>
						<li><a href="${ctx }/calendar/list" class="nav-a">考试日历</a></li>
						<li><a href="${ctx }/forum/showforum" class="nav-a">论坛 </a></li>
					</ul>
				</div>
			</div> 
		<div style="background-color: #F9F8F7; width: 100%;height: 100%;">
			<div class="container" style="background-color: white;">
				<div id="content-left">
					<!--课程详情-->
					<div id="course">
						<div class="course-img">
							<img src="${courseImgPath }/${course.courseImg}" style="border-radius: 8px;" />
						</div>
						<div class="course-info">
							<h2>${course.courseName }</h2>
							<h3>课时：${course.period }时    <span class="expiryDate">有效期：<span id="expiryDate"> ${course.courseEndTime }</span>天</span></h3>
							<div id="info">
								  <p>
									${course.description }
								</p>
								 	
							</div>
							<h2>
							<c:forEach items="${courseType }" var="t" >
								<span class="label label-badge label-warning">${t.typeName}</span>
							</c:forEach>
								<!--  <span class="label label-badge label-warning">英语</span>
								<span class="label label-badge label-primary">四级</span>
								<span class="label label-badge label-success">保航</span>
								<span class="label label-badge label-info">Tony</span>-->
							</h2>
							<div class="sourse-price">¥${course.coursePrice }</div>
							<!--收藏之后改变图标样式为<i class="icon icon-star"></i>-->
							<h4 class="sourse-order">
								课程收藏 <i class="icon icon-star-empty"></i>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<button type="button" class="btn btn-lg btn-primary" data-toggle="modal"  data-target="#myModal" onclick="javascrtpt:window.location.href='${ctx}/torder/toStartOrder?courseId=${course.courseId }'">预约课程</button>
							</h4> 
							<!-- 大对话框 -->
							<!--  <div class="modal fade" id="myModal">
				 				<div class="modal-dialog">
				    				<div class="modal-content">
				      					<div class="modal-header">
				       						<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
				        					<h4 class="modal-title">
				        						<font color="ornflowerBlue">预约课程</font>
				        					</h4>
				      					</div>
				      				</div>
		      						<div class="modal-body">
		        						姓名：<input class="form-control form-focus" autofocus ype="text" placeholder="姓名">
							       		联系电话：<input class="form-control form-focus" autofocus type="text" placeholder="联系电话">
							       		联系邮箱：<input class="form-control form-focus" autofocus type="text" placeholder="联系邮箱">
							       		备注：<textarea class="form-control" rows="3" placeholder="留下你的时间，以及对课程的要求和建议"></textarea>
							    	</div>
							    	<div class="modal-footer">
										<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
										<button type="button" class="btn btn-primary">保存</button>
									</div>
								</div>
							</div>-->
						</div>
						<div><img src="${ctx }/img/123123.png"></div>
					</div>
				</div>
			</div>
		</div>
		<div class="container-fluid" style="background-color: #F9F8F7;">
			<div id="footer">
				<nav class="navbar" role="navigation">
					<div class="container-fluid">
						<!-- footer头部 -->
						<div class="navbar-header">
							<!-- 品牌名称或logo -->
							<a class="navbar-brand" href="${ctx }/common/toindex">KeDou</a>
						</div>
						<!-- 导航项目 -->
						<div class="collapse navbar-collapse navbar-collapse-example">
							<!-- 一般导航项目 -->
							<ul class="nav navbar-nav">
								<li><a href="#">帮助</a></li>
								<li><a href="#">举报</a></li>
								<li><a href="#">用户反馈</a></li>
							</ul>
						</div>
					</div>
				</nav>
			</div>
		</div>
		<script>
			//个人中心面板
			$(document).ready(function(){
				$("#myPhoto").hover(function(){
					$("#myPopover").toggle();
				});
			});
			$(document).ready(function(){
				$("#myPopover").hover(function(){
					$("#myPopover").show();
				});
				$("#myPopover").mouseleave(function(){
					$("#myPopover").hide();
				});
			});
			
			
		</script>
	</body>
</html>	