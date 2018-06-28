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
    <meta charset="utf-8">
     <title>课兜儿网——文章详情</title>
		<link rel="icon" href="${ctx }/img/favicon.ico" type="image/x-icon" />
   		<link rel="shortcut icon" href="${ctx }/img/favicon.ico">
   		<link rel="Bookmark" href="${ctx }/img/favicon.ico">
   		<link rel="SHORTCUT ICON" href="${ctx }/img/favicon.ico"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>详情页</title>   

    <link rel="stylesheet" href="${ctx }/css/amazeui.min.css">
    <link rel="stylesheet" href="${ctx }/css/forum-public.css">
    <link rel="stylesheet" type="text/css" href="${ctx }/css/banner.css">
        <link rel="stylesheet" type="text/css" href="${ctx }/css/zui.min.css">
    <link rel="stylesheet" href="${ctx }/css/forum-reset.css">
    <link rel="stylesheet" href="${ctx }/css/forum-index.css">
    <link rel="stylesheet" href="${ctx }/css/forum-review.css">
    <link rel="stylesheet" href="${ctx }/css/com_style1.css">
    <link rel="stylesheet" href="${ctx }/css/comment1.css">
	
    <script src="${ctx }/js/jquery.min.js"></script>
    <script src="${ctx }/js/DateUtil.js"></script>
    <script src="${ctx }/js/amazeui.min.js"></script>
      <script src="${ctx }/js/zui.min.js"></script>
    <script src="${ctx }/js/forum-public.js"></script>    
	<script type="text/javascript" src="${ctx }/js/jquery.flexText.js"></script>	
	<script type="text/javascript" src="${ctx }/js/forum.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			onready(${artdetail.id},'${userImgPath}');
			
			//更改评论时间格式
			$(".comment-time").each(function(){
				 var date = new Date($(this).html());
		    	 $(this).text(dateFtt("yyyy年MM月dd日 hh时mm分ss秒",date));
			});
			//更改 文章
			$(".articleTime").each(function(){
				 var date = new Date($(this).html());
				 $(this).text(dateFtt("yyyy年MM月dd日",date));
			});
			
			//初始化喜欢样式
			if('${sessionScope.articleLike}'!=''&&'${sessionScope.articleLike}'=='${artdetail.id}') {
				$("#likeI").removeClass('icon-default');
				$("#likeI").addClass("like");
			}
			$("#likeA").on('click',function(){
				if($("#likeI").hasClass('like')){
				//文章已经喜欢 点击取消喜欢
					$.ajax({
		          		url : "/KeDou/forum/unlikeArticle",
		          		type : "GET",
		          		data :{articleId: ${artdetail.id}},
		          		success : function(data) {
		          			$("#likeI").removeClass('like');
		    				$("#likeI").addClass("icon-default");
		          		},error:function(data) {
		          			alert("错误");
		          		}
		          	}); 
			}else {
				//文章没有被喜欢 点击喜欢
				$.ajax({
	          		url : "/KeDou/forum/likeArticle",
	          		type : "GET",
	          		data :{articleId: ${artdetail.id}},
	          		success : function(data) {
	          			$("#likeI").removeClass('icon-default');
	    				$("#likeI").addClass("like");
	          		},error:function(data) {
	          			alert("错误");
	          		}
	          	}); 
			}
			});
			//初始化收藏样式
			if('${ifCollection}'!=''&&'${ifCollection}'=='true') {
				$("#collectionI").removeClass('icon-default');
				$("#collectionI").addClass("collection");
			}
			$("#collectionA").on('click',function(){
				
				if($("#collectionI").hasClass('collection')){
				//文章已经收藏  点击取消收藏
					$.ajax({
		          		url : "/KeDou/forum/uncollect",
		          		type : "GET",
		          		data :{articleId: ${artdetail.id}},
		          		success : function(data) {
		          			$("#collectionI").removeClass('collection');
		    				$("#collectionI").addClass("icon-default");
		          		},error:function(data) {
		          			alert("错误");
		          		}
		          	}); 
			}else {
				//文章没有被收藏   点击收藏
				$.ajax({
	          		url : "/KeDou/forum/collect",
	          		type : "GET",
	          		data :{articleId: ${artdetail.id}},
	          		success : function(data) {
	          			$("#collectionI").removeClass('icon-default');
	    				$("#collectionI").addClass("collection");
	          		},error:function(data) {
	          			alert("错误");
	          		}
	          	}); 
			}
			});
		});
		
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
						<div class="popover bottom" id="myPopover" style="top:4%;">
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
<!-- 文章详情 -->
<div class="am-g am-container">
    <div class="am-u-sm-12 am-u-md-12 am-u-lg-8">
        <div class="newstitles">
            <img src="${articleImgPath }/${artdetail.titleimg}" class="face1">
            <br/><br/><br/>
            <p>${artdetail.title }</p>
            <br/><br/>
            <span>作者：${artdetail.author }</span>&nbsp&nbsp
            	时间：<span class="articleTime">${artdetail.dates}</span>  阅读：<span class="articleReade"> ${artdetail.views }</span>
            <span class="slspan"><i class="icon icon-heart icon-2x icon-default" id="likeI"></i><a href="javascript:;" class="sla" id="likeA"> 喜欢</a></span>
              <span class="slspan"><i class="icon icon-star icon-2x icon-default" id="collectionI"></i><a href="javascript:;" class="sla" id="collectionA"> 收藏</a></span>
        </div>
        <div class="contents">
            <br/><br/>
            <font class="con">
            ${artdetail.content }</font><br/><br/>
            <p dir="ltr">            
            	<span>
            	<a href="#">
	            	<c:forEach items="${hotsopts}" var="p">
	           			#${p.name }#
	           		 </c:forEach>
           		 </a>            	</span>           
            </p>
            <!-- <p dir="ltr" style="text-align: justify;"><font color="#585858" face="arial, microsoft yahei, 宋体">本文转自XXX</font></p> -->
        </div>
    </div>
    <div class="am-u-sm-0 am-u-md-0 am-u-lg-4">
        <div data-am-widget="titlebar" class="am-titlebar am-titlebar-default">
            <h2 class="am-titlebar-title ">
                相关笔记
            </h2>
            <nav class="am-titlebar-nav">
                <a href="#more">more &raquo;</a>
            </nav>
        </div>

        <div data-am-widget="list_news" class="am-list-news am-list-news-default right-bg">
            <ul class="am-list"  >
              <c:forEach items="${recommendArticle }" var="r">
              	   <li class="am-g am-list-item-desced am-list-item-thumbed am-list-item-thumb-left">
                    <div class="am-u-sm-4 am-list-thumb">
                        <a href="${ctx }/forum/details?keyid=${r.id }">
                            <img src="${articleImgPath }/${r.titleimg}" class="face"/>
                        </a>
                    </div>

                    <div class=" am-u-sm-8 am-list-main">
                        <h3 class="am-list-item-hd"><a href="${ctx }/forum/details?keyid=${r.id }">${r.title }</a></h3>

                        <div class="am-list-item-text"></div>
                    </div>
                </li>
                <hr data-am-widget="divider" style="" class="am-divider am-divider-default" />   
              </c:forEach>
                        
            </ul>
        </div>
        <!--  <ul class="am-gallery am-avg-sm-1
  am-avg-md-1 am-avg-lg-1 am-gallery-default" data-am-gallery="{ pureview: true }" >
            <li>
                <div class="am-gallery-item">
                    <a href="http://s.amazeui.org/media/i/demos/bing-1.jpg" class="">
                        <img src="${ctx }/img/label-1.jpg"  alt="远方 有一个地方 那里种有我们的梦想"/>
                    </a>
                </div>
            </li>
            <li>
                <div class="am-gallery-item">
                    <a href="http://s.amazeui.org/media/i/demos/bing-2.jpg" class="">
                        <img src="${ctx }/img/label-1.jpg"  alt="某天 也许会相遇 相遇在这个好地方"/>
                    </a>
                </div>
            </li>
        </ul>-->
    </div>
    </div>
</div>

<!-- 用户评论区 -->
<div class="commentAll">
    <!--评论区域 begin-->
    <div class="reviewArea clearfix">
        <textarea class="content comment-input" placeholder="撰写评论&hellip;" id="userbb" onkeyup="keyUP(this)"></textarea>
        <a href="javascript:;" class="plBtn" ">评论</a>
    </div>
    <!--评论区域 end-->
  <!--回复区域 begin-->
    <div class="comment-show">
      <c:forEach items="${superComment}" var="p">
        <div class="comment-show-con clearfix">
            <div class="comment-show-con-img pull-left img_radius">
                <img src="${userImgPath }/${p.comment.userIcon}" alt="">
            </div>
            <div class="comment-show-con-list pull-left clearfix">
                <div class="pl-text clearfix">
                	  <input type="hidden" class="commentid" value="${p.comment.comId }">
                    <a href="#" class="comment-size-name">${p.comment.userName }</a>:
                    <span class="my-pl-con">&nbsp;${p.comment.comments }</span>
                </div>   
               <div class="date-dz">
               		<span class="date-dz-left pull-left comment-time">${p.comment.publicTime }</span>
               		  <div class="date-dz-right pull-right comment-pl-block"> 
                			<a href="javascript:;" class="date-dz-pl pl-hf hf-con-block pull-left">回复</a></span> 
                		</div>
                </div>       
                 <div class="hf-list-con"></div>
                <!-- 对评论的回复 begin -->
              <c:if test="${not empty p.answer }">
                <c:forEach items="${p.answer }" var ="a" >
                 <div class="all-pl-con">
                 <div class="comment-show-con-img pull-left img_radius" >
                <img src="${userImgPath }/${a.userIcon}" alt="">
            	</div>
            	<div class="comment-show-con-list pull-left clearfix">
                   <div class="pl-text hfpl-text clearfix">
                     	<a href="#" class="comment-size-name">${a.userName }: </a>
                     	<span class="my-pl-con">回复@${p.comment.userName }：${a.answers }</span>
                     	</div>
                     <div class="date-dz" > 
                     		<span class="date-dz-left pull-left comment-time">${a.publicTime }</span> 
                     </div>
                     </div>
                  </div>
                </c:forEach>
             </c:if>
              <!-- 对评论的回复 end --> 
            </div>           
        </div>
        </c:forEach>
       </div>
    <!--回复区域 end-->
</div>

<!--textarea高度自适应-->
<script type="text/javascript">
    $(function () {
        $('.content').flexText();
    });
</script>
<!--textarea限制字数-->
<script type="text/javascript">
    function keyUP(t){
        var len = $(t).val().length;
        if(len > 139){
            $(t).val($(t).val().substring(0,140));
        }
    }
</script>


<!-- 页面底部 -->
<footer>
    <div class="content-1">
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