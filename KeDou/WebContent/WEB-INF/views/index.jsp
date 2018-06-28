<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
     <%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
    <c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
   <meta charset="utf-8"> 
   <title>课兜儿网——首页</title>
   <link rel="icon" href="${ctx }/img/favicon.ico" type="image/x-icon" />
   <link rel="shortcut icon" href="${ctx }/img/favicon.ico">
   <link rel="Bookmark" href="${ctx }/img/favicon.ico">
   <link rel="SHORTCUT ICON" href="${ctx }/img/favicon.ico"/>
   <link rel="stylesheet" href="${ctx }/css/zui.min.css">
   <link rel="stylesheet" type="text/css" href="${ctx }/css/index.css">
   <script src="${ctx }/js/jquery-2.1.1.min.js"></script>
   <script src="${ctx }/js/zui.min.js"></script>
   <script src="${ctx }/js/index.js"></script>
</head>
<body>
      <div id="myCarousel" class="carousel slide">
         <!-- 轮播（Carousel）指标 -->
         <ol class="carousel-indicators">
            <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
            <li data-target="#myCarousel" data-slide-to="1"></li>
            <li data-target="#myCarousel" data-slide-to="2"></li>
         </ol>   
         <!-- 轮播（Carousel）项目 -->
         <div class="carousel-inner"  >
            <div class="item active" id="imgRun">
            <img src="${ctx }/img/01.jpg"  alt="First slide" id="imgRun1">
            </div>
            <div class="item" id="imgRun">
            <img src="${ctx }/img/02.jpg"  alt="Second slide" id="imgRun2">
            </div>
            <div class="item" id="imgRun">
            <img src="${ctx }/img/03.jpg"  alt="Third slide" id="imgRun3">
            </div>
         </div>
         <!-- 轮播（Carousel）导航 -->
         <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
             <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
             <span class="sr-only">Previous</span>
         </a>
         <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
             <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
             <span class="sr-only">Next</span>
         </a>
      </div>
      <div id="center-search">
         <!-- logo -->
         <img src="${ctx }/img/logo.png" id="logo">
         <iframe src="${ctx }/user/switchMode?mode=optional" frameborder="0" name="search_box"></iframe>
      </div>
      <!-- 右下角导航 -->
      <div id="navigation">
         <a href="#" id="index-nav" title="主菜单">
            <img src="${ctx }/img/indexball.png" id="indexball"></a>
            
            <div id="nav_div" style="bottom: 0px;right:0px;">
            <!-- 用户个人中心 -->
            <shiro:hasRole name="user">  
				 <a href="${ctx }/user/person"><img src="${ctx }/img/user.png" class="nav" title="个人中心"></a>
			</shiro:hasRole> 
			<!-- 游客身份登录 -->
          	<shiro:lacksRole name="user">  
				 <a href="${ctx }/user/toiframe"><img src="${ctx }/img/login.gif" class="nav" title="用户登录"></a>
			</shiro:lacksRole>   
            </div>
            
            
             <div id="nav_div" style="bottom: 0px;right:0px;">
              <!-- 商家个人中心 -->
           <shiro:hasRole name="business">  
           		<a href="${ctx }/business/tobusadmin" title="商家登录"><img src="${ctx }/img/business.png" class="nav"></a>
          	</shiro:hasRole>
          	<shiro:lacksRole name="business">  
				 <a href="${ctx }/business/tologin" title="商家登录"><img src="${ctx }/img/login.png" class="nav"></a>
			</shiro:lacksRole>
          	</div>
          	  <div id="nav_div" style="bottom: 0px;right:0px;">
               <a href="${ctx }/calendar/list"><img src="${ctx }/img/calendar.gif" class="nav" title="考试日历"></a> 
            </div>
          	 <shiro:user> 
            <div id="nav_div" style="bottom: 0px;right:0px;">
               <a href="${ctx }/user/logout"><img src="${ctx }/img/logout.gif" class="nav" title="退出登录"></a>
            </div>
            </shiro:user>
      </div>
   <script>
      $(document).ready(function(){ 
    	  var whlogodiv = (window.innerWidth-800)/2;
    	  var whlogo = (800-370)/2;
    	  $('#center-search').css('left',whlogodiv);
    	  $('#logo').css('margin-left',whlogo);
         $('#myCarousel').carousel({interval:4000});//每隔5秒自动轮播 

      }); 
      window.onresize=function(){
    	  var whlogodiv = (window.innerWidth-800)/2;
    	  var whlogo = (800-370)/2;
    	  $('#center-search').css('left',whlogodiv);
    	  $('#logo').css('margin-left',whlogo);
  
          $("img[id^='imgRun']").css('height',window.innerHeight);
          $("img[id^='imgRun']").css('width',window.innerWidth);
      }
      // 右下角导航
      $(document).ready(function(){
         $("#navigation").hover(function(){
            $(".nav").show();
         });
          $("#navigation").mouseleave(function(){
            $(".nav").hide();
         });
         circle();
      });
      
   </script>
</body>
</html>