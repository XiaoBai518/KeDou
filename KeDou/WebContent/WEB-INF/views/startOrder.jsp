<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
            <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
      <%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>  
    <c:set var="ctx" value="${pageContext.request.contextPath}" />
        <c:set var="userImgPath" value="/file/personal" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>课兜儿网——预约信息填写</title>
		<link rel="icon" href="${ctx }/img/favicon.ico" type="image/x-icon" />
   		<link rel="shortcut icon" href="${ctx }/img/favicon.ico">
   		<link rel="Bookmark" href="${ctx }/img/favicon.ico">
   		<link rel="SHORTCUT ICON" href="${ctx }/img/favicon.ico"/>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
	    <meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" type="text/css" href="${ctx }/css/zui.min.css">
		<link rel="stylesheet" type="text/css" href="${ctx }/css/banner.css">
   	
		<script src="${ctx }/js/jquery.zui.js"></script>
		<script src="$[ctx]/js/zui.min.js"></script>
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
		<div class="container">
			<div style="width: 100%;overflow: hidden;">
				<h2 style="text-align: center;">请填写预约信息</h2><br/>
				<form class="form-horizontal" action="${ctx }/torder/startOrder" method="post">
					<!-- 课程Id -->
					<input type="hidden" name="courseId" value="${courseId }">
					<!-- 用户Id -->
					<input type="hidden" name="userId" value="${sessionScope.loginUser.userId }">
					<div class="form-group">
						<label class="col-sm-7 required">该信息将发送给商家，用于后续联系，请确保真实有效。</label>
					</div>
					<div class="form-group">
						<label class="col-sm-4 required">用户名显示</label>
					</div>
					<div class="form-group">
						<label class="col-sm-4 required">联系人</label>
						<div class="col-md-3 col-sm-10">
							<input type="text" class="form-control" name="reserveName" placeholder="联系人"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 required">联系方式</label>
						<div class="col-md-3 col-sm-10">
							<input id="number" type="text" name="reserveTel" onkeyup="value=value.replace(/[^\d]/g,'')" placeholder="联系方式" class="form-last-name form-control"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 required">预约人数</label>
						<div class="col-md-3 col-sm-10">
							<input type="text" class="form-control" name="reserveNum" onkeyup="value=value.replace(/[^\d]/g,'')" placeholder="预约人数"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 required">备注</label>
						<div class="col-md-3 col-sm-10">
							<input type="text" class="form-control" name="userNote" placeholder="备注"/>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-7 col-sm-10">
							<button type="submit" class="btn btn-primary" onclick="return isPoneAvailable()"">确定</button>
						</div>
					</div>
				</form>
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
			function isPoneAvailable($poneInput) {  
				var myreg=/^[1][3,4,5,7,8][0-9]{9}$/;  
				var str = document.getElementById("number").value;
				if (!myreg.test(str)) {
					alert("请输入正确的手机号码！")
					return false;
				}else{
					return true;
				}
			}

		</script>

	</body>
</html>