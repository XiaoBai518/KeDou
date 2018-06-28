<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>  
<c:set var="ctx" value="${pageContext.request.contextPath}" />
 <c:set var="userImgPath" value="/file/personal" />
<!DOCTYPE html>
<html >
<head>
<meta charset="utf-8">
<title>课兜儿网——选择地址</title>
		<link rel="icon" href="${ctx }/img/favicon.ico" type="image/x-icon" />
   		<link rel="shortcut icon" href="${ctx }/img/favicon.ico">
   		<link rel="Bookmark" href="${ctx }/img/favicon.ico">
   		<link rel="SHORTCUT ICON" href="${ctx }/img/favicon.ico"/>
<link href="${ctx}/css/Address/base.css" rel="stylesheet" type="text/css">
<link href="${ctx}/css/Address/main.css" rel="stylesheet" type="text/css">
<link href="${ctx}/css/Address/select.css" rel="stylesheet" type="text/css">
<link href="${ctx}/css/zui.min.css" rel="stylesheet" type="text/css" >
<link href="${ctx }/css/banner.css" rel="stylesheet" type="text/css">
<script src="${ctx }/js/jquery-2.1.1.min.js"></script>
<script src="${ctx }/js/Address/distpicker.data.js"></script>
<script src="${ctx }/js/Address/main.js"></script>
<script  src="${ctx }/js/Address/aircity.js"></script>
<script  src="${ctx }/js/Address/j.dimensions.js"></script>
<script  src="${ctx }/js/Address/j.suggest.js"></script>
<script src="${ctx }/js/zui.min.js"></script>
<title>选择地址</title>
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
					<p id="address">${sessionScope.userAddress.address }[<a href="#" style="color:#3280fc; text-decoration: none;">切换地址</a>]</p>
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
										<li class="menu-li"><a href="${ctx }/user/person?address=4"><i class="icon icon-folder-close"></i>&nbsp&nbsp我的资料</a></li>
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
<div class="gc">
   <div class="gc_main">
      <div class="demo uldemo">
        <div id="box" style="width: 800px;">
        <form action="${ctx}/useraddress/detail" method="get" onsubmit="return detailOnSubmit()">
        <input type="hidden" name="arrcity_3word" id="arrcity_3word" value="" />
        <label for="arrcity">直接搜索城市：</label><input type="text" name="arrcity" id="arrcity"/>
        <input type="text" name="detail" placeholder="详细地址"/>
        <div id='suggest' class="ac_results"></div>
        <input type="submit" value="确定" name="submit"  id="submitbutton"  style="width: 40px;">
        </form>
        </div>
        <hr color="#F5F5F5" />
        <div id="hotspace"><label>热门地区:</label>
        <a href="${ctx}/useraddress/direct?cityandarea=北京市 海淀区">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;北京市海淀区</a>
        <a href="${ctx}/useraddress/direct?cityandarea=石家庄市 裕华区">&nbsp;&nbsp;&nbsp;&nbsp;石家庄市裕华区</a>
        <a href="${ctx}/useraddress/direct?cityandarea=成都市 金牛区">&nbsp;&nbsp;成都市金牛区</a>
        <a href="${ctx}/useraddress/direct?cityandarea=上海市 黄浦区">&nbsp;&nbsp;上海市黄浦区</a>
        <a href="${ctx}/useraddress/direct?cityandarea=武汉市 武昌区">&nbsp;&nbsp;武汉市武昌区</a>
        <a href="${ctx}/useraddress/direct?cityandarea=南京市 秦淮区">&nbsp;&nbsp;南京市秦淮区</a>
        </div>
        <hr color="#F5F5F5" />
        <div class="info"><label>按省 /市 /区 选择：</label></div>
        <div class="forexample">
              <div data-toggle="distpicker" data-style="li" >
                  <ul class="distpicker_province" data-choose-back-fun="choose_province_fun_back_ul"></ul>
                  <ul class="distpicker_city"  data-choose-back-fun="choose_city_fun_back_ul"></ul>
                  <ul class="distpicker_district"  data-choose-back-fun="choose_district_fun_back_ul"></ul>
                </div>
                <script type="text/javascript">
                    function choose_province_fun_back_ul(data){
                    	var codeid = data.find(".distpicker_province").find('[selected=selected]').attr("data-code");
                    	jQuery(".uldemo .distpicker_district").hide();
                    	jQuery(".uldemo .distpicker_city").show();
                    }
                    function choose_city_fun_back_ul(data){
                    	var codeid = data.find(".distpicker_city").find('[selected=selected]').attr("data-code");
                      	var name = data.find(".distpicker_city").find('[selected=selected]').attr("data-text");
                    	jQuery(".uldemo .distpicker_district").show();
                    }
                    function choose_district_fun_back_ul(data){
                    	var codeid = data.find(".distpicker_district").find('[selected=selected]').attr("data-code");
                    	var namedistrict = data.find(".distpicker_district").find('[selected=selected]').attr("data-text");
                    	var namecity = data.find(".distpicker_city").find('[selected=selected]').attr("data-text");
           			 
                    	window.location.href="${ctx}/useraddress/add?city="+namecity+"&&area="+namedistrict;
                    }
                    </script>
              </div>
          </div>
   </div>
</div>

<script type="text/javascript">
jQuery(function(){
	districts_start();
});
      $(function(){
        $("#arrcity").suggest(citys,{hot_list:commoncitys,dataContainer:'#arrcity_3word',onSelect:function(){$("#city2").click();}, attachObject:'#suggest'});
        $("#city2").suggest(citys,{hot_list:commoncitys,attachObject:"#suggest2"});
      });
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
