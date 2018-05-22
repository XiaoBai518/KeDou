<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" name="viewport" content="width=device-width, initial-scale=1">
		<!-- ZUI Javascript 依赖 jQuery -->
		<script src="${ctx }/js/jquery.zui.js"></script>
		<link rel="stylesheet" type="text/css" href="${ctx }/css/banner.css">
		<!-- ZUI 标准版压缩后的 JavaScript 文件 -->
		<script src="${ctx }/js/jquery.zui.js"></script>
		<link rel="stylesheet" type="text/css" href="${ctx }/css/content.css">
		<!-- ZUI 标准版压缩后的 CSS 文件 -->
		<link rel="stylesheet" href="${ctx }/css/zui.min.css">
		<script type="text/javascript" src="${ctx}/js/zui.min.js"></script>
    	<script type="text/javascript" src="${ctx }/js/zui.chart.min.js"></script>
    	<script type="text/javascript" src="${ctx }/js/chart.js"></script>
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
					<form>
						<div class="input-group">
							<div class="input-control search-box search-box-circle has-icon-left has-icon-right search-example" id="searchboxExample">
								<input id="inputSearchExample3" type="search" class="form-control search-input" placeholder="搜索"/>
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
					<p id="address">[<a href="#" style="color:#3280fc; text-decoration: none;">切换地址</a>]</p>
				</div>
				<!--个人头像-->
				<div id="userPad">
					<div id="myPhoto">
						<img src="${ctx }/img/login.png"/>
					</div>
					<div class="popover bottom" id="myPopover">
						<div class="arrow"></div>
							<h3 class="popover-title"><a href="#"><i class="icon icon-home"></i>&nbsp&nbsp个人中心</a></h3>
							<div class="popover-content">
								<ul>
									<li class="menu-li"><a href="#"><i class="icon icon-envelope"></i>&nbsp&nbsp我的消息</a></li>
									<li class="menu-li"><a href="#"><i class="icon icon-history"></i>&nbsp&nbsp我的足迹</a></li>
									<li class="menu-li"><a href="#"><i class="icon icon-check-board"></i>&nbsp&nbsp我的预约</a></li>
									<li class="menu-li"><a href="#"><i class="icon icon-star"></i>&nbsp&nbsp我的收藏</a></li>
								</ul>
							</div>
						</div>
					</div>
				</div>
				
				<!--导航条-->
				<div id="navigation">
					<ul class="nav nav-secondary">
						<li><a href="#" class="nav-a">首页</a></li>
						<li><a href="#" class="nav-a">动态 <span class="label label-badge label-success">4</span></a></li>
						<li><a href="#" class="nav-a">项目 </a></li>
						<li><a href="#" class="nav-a">哈哈</a></li>
					</ul>
				</div>
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
									<li onclick="fc(${page},${totalCount},'${searchSentence}')"><a href="#" >价格</a></li>
									<li><a href="#">综合</a></li>
									<li><a href="#">形式 </a></li>
									<li>
										<a class="#" data-toggle="dropdown" href="your/nice/url">分类 <span class="caret"></span></a>
										<ul class="dropdown-menu">
											<li><a href="#">高考</a></li>
											<li><a href="#">四级</a></li>
											<li><a href="#">六级</a></li>
											<li><a href="#">考研</a></li>
										</ul>
									</li>
								</ul>
							</div>
							<div id="course-list">
								<c:forEach  items="${cl }" var="cl">
									<div class="course">
										<div class="course-img">
											<img src="/file/${cl.courseImg }" style="border-radius: 8px;" />
										</div>
										<div class="course-info">
											<div class="course-name">
												<a href="#" style="color: #666560;">
													${cl.courseName }
												</a>
											</div>
											<div class="course-abstract">
												<span class="abstract">简介：</span>
												<p>${cl.description }</p>
											</div>
											<div class="sourse-price">
												¥${cl.coursePrice }
											</div>
											<div class="sourse-order">
													<a class="btn btn-primary" href="#">立即预约</a>
											</div>
										</div>
									</div>
								</c:forEach>
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
					<div id="content-right">
						<!-- 广告-->
						<div id="content-right-top">
							<c:forEach items="${cal }" var="cal">
								<div class="content-adcard">
									<a class="card" href="###">
										<img src="/file/${cal.courseImg }" alt="">
										<div class="card-heading"><strong>${cal.courseName }</strong></div>
										<div class="card-content text-muted">${cal.description }</div>
										<div class="card-actions">
										<button type="button" class="btn btn-danger"><i class="icon-heart"></i> 喜欢</button>
										<div class="pull-right text-danger"><i class="icon-heart-empty"></i> 520 人喜欢</div>
										</div>
									</a>
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
			func1(${gsonCourseList});
		</script>
		
	</body>
</html>	