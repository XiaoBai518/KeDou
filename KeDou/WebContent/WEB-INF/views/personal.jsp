<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
    <c:set var="ctx" value="${pageContext.request.contextPath}" />
    <c:set var="userImgPath" value="/file/personal" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>课兜儿网——用户个人中心</title>
		<link rel="icon" href="${ctx }/img/favicon.ico" type="image/x-icon" />
   		<link rel="shortcut icon" href="${ctx }/img/favicon.ico">
   		<link rel="Bookmark" href="${ctx }/img/favicon.ico">
   		<link rel="SHORTCUT ICON" href="${ctx }/img/favicon.ico"/>
    <link rel="stylesheet" type="text/css" href="${ctx }/css/personal.css"/>
 	<link rel="stylesheet" type="text/css" href="${ctx}/css/InformationBinding.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx }/css/banner.css">
       <script src="${ctx }/js/jquery-2.2.0.min.js"></script>
		<script src="${ctx }/js/jquery.zui.js"></script>
		<script src="${ctx }/js/zui.min.js"></script>
		<script src="${ctx }/js/LoginRegiste.js"></script>
		<script src="${ctx }/js/personal.js"></script>
		<script src="${ctx }/js/md5.js"></script>
		<script src="${ctx }/js/common.js"></script>
		   <link rel="stylesheet" type="text/css" href="${ctx }/css/zui.min.css">
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
		<div class="main_left" style="float:left;">
			<div class="image"  id="image"><img src="${userImgPath}/${sessionScope.loginUser.userIcon}" width="100%" height="100%" onerror="this.src='${ctx }/img/logo.jpg'"></div>
			<div class="image_change" id="image_change" onclick="check('chanageImg','${userImgPath}/${sessionScope.loginUser.userIcon}')">
				<img src="${ctx }/img/timg.jpg"width="100%" height="100%">
					<p class="image_text" id="image_text">更换头像</p>	
			</div>
			<div class="username">${sessionScope.loginUser.userName }</div>
			<div class="username_mananger">账户管理</div>
				<div class="usermessage"></div>
				<a style="text-decoration: none;" href="${ctx }/user/personmessage" target="preson_megs"><div class="usermessage1" id="usermessage1" >个人信息</div></a>
				<a style="text-decoration: none;" href="${ctx }/userhistory/personhistory" target="preson_megs"><div class="usermessage2" id="usermessage2">访问历史</div></a>
				<a style="text-decoration: none;" href="${ctx }/torder/persontorder" target="preson_megs"><div class="usermessage3" id="usermessage3">预约课程</div></a>
				<a style="text-decoration: none;" href="${ctx }/collection/personcollection" target="preson_megs"><div class="usermessage4" id="usermessage4">收藏课程</div></a>
				<a style="text-decoration: none;" href="${ctx }/InformationBinding/binding" target="preson_megs"><div class="usermessage5" id="usermessage5">绑定信息</div></a>		
		</div>
		<script>
			$(document).ready(function(){
				$("div[id='usermessage${p}']").attr('class',"usermessage111");
				if(${p }!=1) {
					$('#buton1').attr('style','display: none');
				}
			});
		</script>
		<div class="main_right"  style="float:left;">
			<div id="buton1" class="buton1" onclick="check('personal',0,'${sessionScope.loginUser.userName}','${sessionScope.loginUser.gender}','${sessionScope.loginUser.userDiscription  }')"><label style="line-height :20px;vertical-align:middle;">编辑</label></div>
			<div class="main_message">
				<iframe src="${ctx}/${address}"  frameborder="0" width="100%" scrolling="no" height="100%" name="preson_megs"></iframe>
			</div>
		</div>
		<!-- 调用弹框按钮  被隐藏了 -->
		<button id="bingmail" onclick="check('bingMail')" style="display:none;"></button>
		<button id="changemail" onclick="check('changemail')" style="display:none;"></button>
		<button id="bingTel" onclick="check('bingTel')" style="display:none;"></button>
		<button id="changeTel" onclick="check('changeTel')" style="display:none;"></button>
		<button id="changepwd" onclick="check('changepwd')" style="display:none;"></button>
		<!-- 修改完成弹框按钮 -->
		<!-- <button id="changepwdFinish" onclick="check('changepwdFinish')" style="display:none;"></button> -->
			<script>
			function say(text){
				 document.getElementById(text).click();
			}
			//验证码刷新
			 function refreshImg(obj) {
				 obj.src = "${ctx }/common/validatecade?"+Math.random();
			}			
			//验证码是否正确
			  function verifyCodeIsTrue(verifyCode) {
	          	var xmlhttp;
	     		 if(window.XMLHttpRequest) {
	     			 xmlhttp = new XMLHttpRequest();
	     		 }else {
	     			 xmlhttp = new ActiveXObjec("Microsoft.XMLHTTP");
	     		 }
	     		 xmlhttp.onreadystatechange = function() {
	     			 if(xmlhttp.readyState==4&&xmlhttp.status==200) {
	     				var isTrue = xmlhttp.responseText;
	     					if(isTrue=="1"){
	     						//验证码正确
	     						document.getElementById("verifyCode").innerHTML = '验证码正确！';
	     						istrue[2]=0
	     					}else if(isTrue=="-1") {
	     						//验证码不正确
	     						document.getElementById("verifyCodemsg").innerHTML = '验证码不正确！';
	     						istrue[2]=-1;
	     					}
	     			 }
	     		 }
	     		 xmlhttp.open("GET","${ctx }/common/istrue?verifyCode="+verifyCode,true);
	     		 xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
	     		 xmlhttp.send();
	          }
			  </script>
	</body>
</html>