<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
 <c:set var="userImgPath" value="/file/personal" />
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="courseImgPath" value="/file/course" />
    <c:set var="userImgPath" value="/file/personal" />
<!DOCTYPE html>
<html>
	<head>
		<title>课兜儿网——商品搜索结果页</title>
		<link rel="icon" href="${ctx }/img/favicon.ico" type="image/x-icon" />
   		<link rel="shortcut icon" href="${ctx }/img/favicon.ico">
   		<link rel="Bookmark" href="${ctx }/img/favicon.ico">
   		<link rel="SHORTCUT ICON" href="${ctx }/img/favicon.ico"/>
		<meta charset="utf-8" name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" type="text/css" href="${ctx }/css/banner.css">
		<link rel="stylesheet" type="text/css" href="${ctx }/css/content.css">
		<link rel="stylesheet" href="${ctx }/css/zui.min.css">
		   <link rel="stylesheet" type="text/css" href="${ctx }/css/autocomplete.css">
		<script src="${ctx }/js/jquery.zui.js"></script>
		<script type="text/javascript" src="${ctx}/js/zui.min.js"></script>
    	<script type="text/javascript" src="${ctx }/js/zui.chart.min.js"></script>
    	<script type="text/javascript" src="${ctx }/js/chart.js"></script>
    	   <script src="${ctx }/js/jquery.autocomplete.min.js"></script>
        <script src="${ctx }/js/autocomplete.js"></script>
    	<script type="text/javascript">
      	function ButtonCollectionShow(courseId) {
    		if($('#collection'+courseId).hasClass("btn-danger")) {
    			if('${sessionScope.loginUser}'!='') {
    				$('#collection'+courseId).removeClass("btn-danger");
        	    	$('#collection'+courseId).text("已收藏");
        	    	$.ajax({
        			    url:'${ctx}/collection/saveCollection',
        			    type:'GET',
        			    data:{courseid:courseId},  
        			    success:function(data){
        			    },
        			    error:function(xhr,textStatus){
        			       	alert(textStatus);
        			    }
        			});
    			}else {
    				new $.zui.Messager('请先登陆。。。', {
    				    type: 'warning', // 定义颜色主题
    				    icon: 'warning-sign' // 定义消息图标
    				}).show();
    			}
    			
    		}else {
    			if('${sessionScope.loginUser}'!='') {
    			$('#collection'+courseId).addClass("btn-danger");
    			$('#collection'+courseId).html("<i class='icon-heart'></i>  喜欢");
    			$.ajax({
    			    url:'${ctx}/collection/deleteCollection',
    			    type:'GET',
    			    data:{courseid:courseId},  
    			    success:function(data){
    			    },
    			    error:function(xhr,textStatus){
    			       	alert(textStatus);
    			    }
    			});
    			}else {
    				new $.zui.Messager('请先登陆。。。', {
    				    type: 'warning', // 定义颜色主题
    				    icon: 'warning-sign' // 定义消息图标
    				}).show();
    			}
    		}
    	}
    		$(document).ready(function(){
    			$("button[id^='collection']").each(function() {
    				if($(this).val()==1) {
    					$(this).removeClass("btn-danger");
				    	$(this).text("已收藏");
    				}
    			})
    		});
    	function buttonHref(courseId) {
    		alert(courseId)
    		window.location='${ctx}/torder/toStartOrder?courseId='+courseId;
    	}
    	</script>
	</head> 
	<body style="">
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
								<input id="index-find" type="search" name="search"  class="form-control search-input" value="${searchSentence}"/>
							
								<label for="index-find" class="input-control-icon-left search-icon">
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
						 <c:if test="${not empty sessionScope.loginUser}">
					 	<div id="myPhoto">
						<img src="${userImgPath}/${sessionScope.loginUser.userIcon}" onerror="this.src='${ctx }/img/logo.jpg'"/>
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
		<script>
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
		<div style="background-color: #F9F8F7;width: 100%;height: 100%;">
			<div class="container" style="background-color: white;">
				<div id="content">
					<div id="content-left">
						<!--课程列表-->
						<div id="content-left-top">
							<div id="content-nav">
								<ul class="nav nav-tabs">
								<li><a href="${ctx }/course/searchCourseListAsc?page=${page}&totalCount=${totalCount}&search=${searchSentence}">价格</a></li>
								</ul>
							</div>
							<div id="course-list">
							<c:if test="${error eq 'NoResult' }">
								<span>抱歉，没有搜索结果。。。</span>
							</c:if>
							<c:if test="${empty error }">
								<c:forEach  items="${cl }" var="cl" varStatus="i">
									<div class="course">
									<a href="${ctx }/course/toCourseDetail?courseId=${cl.course.courseId}" style="color: #666560;">
										<div class="course-img">
											<img src="${courseImgPath}/${cl.course.courseImg }" style="border-radius: 8px;" />
										</div>
									</a>
										<div class="course-info">
											<div class="course-name">
												<a href="${ctx }/course/toCourseDetail?courseId=${cl.course.courseId}" style="color: #666560;">
													${cl.course.courseName }
												</a>
											</div>
											<div class="course-abstract">
												<span class="abstract">简介：</span>
												<p>${cl.course.description }</p>
											</div>
											<div class="sourse-price">
												¥${cl.course.coursePrice }
											</div>
											<div class="sourse-order">
											<button type="button" class="btn btn-danger" value="${cl.collection}"  id="collection${cl.course.courseId}" onclick="ButtonCollectionShow(${cl.course.courseId})"><i class="icon-heart"></i> 喜欢</button>
													<a class="btn btn-primary" href="${ctx }/torder/toStartOrder?courseId=${cl.course.courseId}">立即预约</a>
											</div>
										</div>
									</div>
								
								</c:forEach>
							</c:if>
								
							</div>
						</div>
						<!--数据对比-->
						<div id="content-left-bottom">
							<canvas id="myChart" width="741" height="400"></canvas>
						</div>
						<!--分页-->
					<div id="content-left-paging">
						<ul id="myPager" class="pager"  data-ride="pager" data-page="${page}" data-rec-Total="${totalCount }" data-rec-Per-Page="3" data-link-creator="${ctx }/course/searchCourseList?page={page}&totalCount={recTotal}&search=${searchSentence}" ></ul>				
					</div>
					</div>
					<!-- 广告-->
					<div id="content-right">
						<div id="content-right-top">
							<c:forEach items="${cal }" var="cal">
								<div class="content-adcard">
									<div class="card">
										<a  href="${ctx }/course/toCourseDetail?courseId=${cal.course.courseId }">
										<img src="${courseImgPath}/${cal.course.courseImg }" >
										</a>
										<div class="card-heading"><strong>${cal.course.courseName }</strong></div>
										<div class="card-content text-muted">${cal.course.description }</div>
										<div class="card-actions">
										<button type="button" class="btn btn-danger" value="${cal.collection}"  id="collection${cal.course.courseId}" onclick="ButtonCollectionShow(${cal.course.courseId})"><i class="icon-heart"></i> 喜欢</button>
										<a class="btn btn-primary" href="${ctx }/torder/toStartOrder?courseId=${cal.course.courseId}">立即预约</a>
										</div>
									</div>

								</div>	
							</c:forEach>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="container-fluid" style="background-color: #F9F8F7;">
			<div id="footer">
				<nav class="navbar" role="navigation">
					<div class="container-fluid">
						<!-- 导航头部 -->
						<div class="navbar-header">
							<!-- 品牌名称或logo -->
							<a class="navbar-brand" href="your/nice/url">KeDou</a>
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
		<script type="text/javascript">
		<c:if test="${empty error }">
			func1(${gsonCourseList });
		</c:if>
		</script>
	</body>
</html>	