<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ page import="java.util.*"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="articleImgPath" value="/file/article" />
    <c:set var="userImgPath" value="/file/personal" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta charset="UTF-8">
   <title>课兜儿网——社区论坛</title>
		<link rel="icon" href="${ctx }/img/favicon.ico" type="image/x-icon" />
   		<link rel="shortcut icon" href="${ctx }/img/favicon.ico">
   		<link rel="Bookmark" href="${ctx }/img/favicon.ico">
   		<link rel="SHORTCUT ICON" href="${ctx }/img/favicon.ico"/>
    <link rel="stylesheet" type="text/css" href="${ctx }/css/amazeui.min.css">
    <link rel="stylesheet" type="text/css" href="${ctx }/css/forum-public.css">
    <link rel="stylesheet" type="text/css" href="${ctx }/css/banner.css">
    <link rel="stylesheet" type="text/css" href="${ctx }/css/forum-reset.css">
    <link rel="stylesheet" type="text/css" href="${ctx }/css/forum-index.css">
    <link rel="stylesheet" href="${ctx }/css/zui.min.css">
        <script type="text/javascript" src="${ctx }/js/jquery.zui.js"></script>
	<script type="text/javascript" src="${ctx}/js/zui.min.js"></script>
    <script type="text/javascript" src="${ctx }/js/script.js"></script>
    <script>
    function collect(item){
    	var keyword = item.getAttribute("id");
    	alert(keyword);
    	var params={};
    	params.keyword=keyword;    	
    	$.ajax({
    		url : "http://localhost:8080/KeDou/forum/collect",
    		type : "POST",
    		data :params,
    		dataType:'json',
    		success : function(data) {
    			if (data == "yes") {
    				alert("收藏成功");
    			} else if (data == "no") {
    				alert("收藏失败");
    			}
    		},error: function(data){
    			alert("error");
    		}
    	});
    }
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
								<input id="inputSearchExample3" type="search" name="search"  class="form-control search-input" value="${searchSentence}"/>
							
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
			
						 <c:if test="${not empty sessionScope.loginUser}">
					 	<div id="myPhoto">
						<img src="${userImgPath}/${sessionScope.loginUser.userIcon}" onerror="this.src='${ctx }/img/logo.jpg'"/>
						</div>
						<div class="popover bottom" id="myPopover" style="top:3%;">
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
					 	 <c:if test="${ not empty sessionScope.loginBusiness}">
					 	<div id="myPhoto">
						<img src="${ctx }/img/label-5.jpg" onerror="this.src='${ctx }/img/logo.jpg'"/>
						</div>
						<div class="popover bottom" id="myPopover" style="top:3%;">
							<div class="arrow"></div>
								<h3 class="popover-title"><a href="${ctx }/business/tobusadmin"><i class="icon icon-home"></i>&nbsp&nbsp个人中心</a></h3>
								<div class="popover-content">
									<ul>
										<li class="menu-li"><a href="javascript:;" onclick="realData(${busid})"><i class="icon icon-envelope"></i>&nbsp&nbsp店铺数据</a></li>
										<li class="menu-li"><a href="javascript:;" onclick="basicInfo(${busid})"><i class="icon icon-history"></i>&nbsp&nbsp我的信息</a></li>
									</ul>
								</div>
						</div>
					 </c:if>
					 	<c:if  test="${ empty sessionScope.loginBusiness}">
					 			<div id="loginregiste">
							<a href="${ctx }/user/toiframe"><font size="4">登陆 </font></a><font size="5" color="blue"> / </font><a href="${ctx }/user/toiframe?option=regist"><font size="4"> 注册</font></a>
						</div>
					 	</c:if>
					 
					 </c:if>
					</div>
				</div>
				<!--导航条-->
				<div id="navigation">
					<ul class="nav nav-secondary">
						<li><a href="${ctx }/common/toindex" class="nav-a">首页</a></li>
						<li><a href="${ctx }/calendar/list" class="nav-a">考试日历</a></li>
						<li><a href="${ctx }/forum/showforum" class="nav-a">论坛 </a></li>
						<li><a href="${ctx }/forum/touseraddArticle" class="nav-a">写文章 </a></li>
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
 <!--  列表区 -->
  <div class="main">
    <div class="main-inner body-width">
      <div class="banner clearfix">
        <div class="slider" id="slider">
          <ul class="slider-wrapper">  
           <c:forEach items="${piclist }" var="a">         
            <li class="item" data-title="${a.title }" data-author="by ${a.author }">
              <a href="${ctx }/forum/details?keyid=${a.id }" class="pic"><img src="${articleImgPath }/${a.titleimg}" alt="#"></a>
            </li> 
           </c:forEach>           
          </ul>
          <a href="javascript:;" class="slider-prev"></a>
          <a href="javascript:;" class="slider-next"></a>
          <div class="slider-title">
            <h2></h2>
            <span></span>
          </div>
          <div class="slider-btns">
            <span class="item"></span>
            <span class="item"></span>
            <span class="item"></span>
            <span class="item"></span>
            <span class="item"></span>
          </div>
        </div>
        <div class="banner-info">
          <div class="news body-border">
            <ul>
              <li class="title">社区热点</li>
              <li class="link" >
              <a href="#">#19MPAcc备考指南#</a>
              </li>
               <li class="link" >
                 <a href="#">#what`s CPA ?#</a>
                 </li>
               <li class="link" >
                 <a href="#">#语法学得会 蒙都能蒙对#</a>
                 
                  <li class="link" >
                 <a href="#">#考研那点事#</a>
                  </li>
                  <li class="link" >
                 <a href="#">#不是马路杀手#</a>
                  </li>
                  <li class="link" >
                 <a href="#">#雅思我们分手吧#</a>
                  </li>
                  <li class="link" >
                 <a href="#">#六级大通关#</a>
                  </li>
                  <li class="link" >
                 <a href="#">#驾照万事通#</a>
                  </li>
            </ul>
          </div>
          <div class="ad1">
            <a href="http://www.koolearn.com/"><img src="${ctx }/img/label-5.jpg" ></a></div>
        </div>
      </div>

      <!-- 专辑列表 -->
      <div class="main-cont main-album">
        <div class="main-cont__title">
          <h3>专辑精选</h3>
          <a href="#" class="more">往期回顾 ></a>
        </div>
        <ul class="main-cont__list clearfix">
        <c:forEach items="${superArticlelist}" var="a">
          <li class="item">
            <a href="${ctx }/forum/details?keyid=${a.article.id }" class="pic"><img src="${articleImgPath}/${a.article.titleimg}" alt="#"></a>
            <div class="info">
              <a href="${ctx }/forum/details?keyid=${a.article.id }" class="title">${a.article.title }</a>
              <p>${a.article.likes }个赞 · ${a.collectionNum }人收藏</p>
              <p>by <a href="#" class="author">${a.article.author}</a></p>
            </div>
          </li>
        </c:forEach>
        </ul>
      </div>

      <!-- 其他内容列表区 -->
      <div class="main-cont main-waterfall main-album">
        <div class="main-cont__title">
          <h3>大家正在逛</h3>
          <a href="#" class="more">浏览更多></a>
        </div>
        <ul class="main-cont__list clearfix">
		<c:forEach items="${relist}" var="m">
          <li class="item item-cur">
            <a href="details.html" class="pic">
              <img src="${articleImgPath }/${m.titleimg}" alt="#">
            </a>
            <div class="waterfall-info">
              <p class="title" style="display: block;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;">${m.title}</p>
              <p class="icon"><span class="icon-star"></span><i class="icon icon-heart" style="color:red">${m.likes}</i></p>
            </div>
            <div class="info">
             <!--   <a href="details.html" class="headImg"><img src="images/cont/waterfall_headImg1.jpeg" alt="#"></a>-->
              <p class="title">by <a href="#"  class="author">${m.author}</a></p>
            </div>
          </li>
         </c:forEach>           
        </ul>
      </div>
    </div> 
  </div>

<footer>
    <div class="content">
        <ul class="am-avg-sm-5 am-avg-md-5 am-avg-lg-5 am-thumbnails">
            <li><a href="#">联系我们</a></li>
            <li><a href="#">加入我们</a></li>
            <li><a href="#">合作伙伴</a></li>
            <li><a href="#">广告及服务</a></li>
            <li><a href="#">友情链接</a></li>
        </ul>
        <p>Amaze UI出品<br>© 2016 AllMobilize, Inc. Licensed under MIT license. Developed with WebStorm.</p>
    </div>
</footer>
</body>
</html>