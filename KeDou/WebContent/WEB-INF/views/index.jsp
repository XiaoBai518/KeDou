<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
    <c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
   <meta charset="utf-8"> 
   <title>课兜儿网首页</title>
   <link rel="stylesheet" href="${ctx }/css/bootstrap.min.css">
   <link rel="stylesheet" type="text/css" href="${ctx }/css/index.css">
   <script src="${ctx }/js/jquery-2.1.1.min.js"></script>
   <script src="${ctx }/js/bootstrap.min.js"></script>
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
         <div class="carousel-inner">
            <div class="item active">
            <img src="${ctx }/img/01.jpg" alt="First slide">
            </div>
            <div class="item">
            <img src="${ctx }/img/02.jpg" alt="Second slide">
            </div>
            <div class="item">
            <img src="${ctx }/img/03.jpg" alt="Third slide">
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
         <a href="#" id="index-nav">
            <img src="${ctx }/img/indexball.png" id="indexball"></a>
            
            <div id="nav_div" style="bottom: 0px;right:0px;">
            <c:if test="${ not empty sessionScope.loginUser}">
              <a href="${ctx }/user/person"><img src="${ctx }/img/user.png" class="nav"></a>
            </c:if>
             <c:if test="${ empty sessionScope.loginUser  }">
              <a href="${ctx }/user/toiframe"><img src="${ctx }/img/login.png" class="nav"></a>
            </c:if>
            </div>
            <div id="nav_div" style="bottom: 0px;right:0px;"><a href="${ctx }/business/tologin"><img src="${ctx }/img/business.png" class="nav"></a></div>
            <div id="nav_div" style="bottom: 0px;right:0px;"><a href="${ctx }/user/logout"><img src="${ctx }/img/qrcode.png" class="nav"></a></div>
      </div>
   <script>
      $(document).ready(function(){ 
         $('#myCarousel').carousel({interval:4000});//每隔5秒自动轮播 
      }); 
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