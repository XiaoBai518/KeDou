<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
    <c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

		<link rel="stylesheet" type="text/css" href="${ctx }/css/LoginRegiste.css"/>
		<title></title>
		<script src="${ctx }/js/jquery-1.11.1.min.js"></script>
		<script src="${ctx }/js/LoginRegiste.js"></script>
		<script src="${ctx }/js/md5.js"></script>
		<script type="text/javascript">
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
 						istrue[2]=0
 					}else if(isTrue=="-1") {
 						//验证码不正确
 						document.getElementById("verifyCodemsg").innerHTML = '验证码不正确！';
 						istrue[2]=-1
 					}
    			 }
    		 }
    		 xmlhttp.open("GET","${ctx }/common/istrue?verifyCode="+verifyCode,true);
    		 xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
    		 xmlhttp.send();
         }
		function keyDown(e) {
			var keynum;
			keynum = window.event?e.keyCode:e.which;
			if(keynum ==13) {
				return false;
			}
					}
		</script>
</head>
<body>
      <div id="two_ifr">
		<div id="logo"><img src="${ctx }/img/logo.jpg"></div><!--logo的位置-->
		   <div id="input">
			 <div>&nbsp;</div>
				<form name="myform" action="${ctx }/user/login" method="post" onkeydown="return keyDown(event)" onsubmit="return endsubmit()" target="_top">
					<div>
						<input type="text" name="acount" placeholder="请输入账号" class="input_size" onblur="textEmpty(this,'email')" onfocus="textfocus('email')"/>
					</div><!--账号密码的位置-->
					<!-- 账号错误信息提示位置 -->
					<span style="font-size:15px;color:darkred;" id="emailmsg">
						<c:if test="${error eq 'stateAtiveErro' }">
							账号未激活
						</c:if>
						<c:if test="${error eq 'stateLockErro' }">
							账号被锁定
						</c:if>
						<c:if test="${error eq 'NoAcountErro' }">
							账号不存在
						</c:if>
					
					</span>
					<span style="font-size:15px;color:darkgreen;" id="email"></span></br><!-- 账号信息提示位置 -->
					
					<div>
						<input type="password" name="userpwd" placeholder="请输入密码" onblur="textEmpty(this,'pwd')" onfocus="textfocus('pwd')" class="input_size" id="inputpwd"/>
					</div><!--账号密码的位置-->
					
					<span style="font-size:15px;color:darkred;" id="pwdmsg">
					<c:if test="${error eq 'PwdErro'}">
						密码错误
					</c:if>
					</span><!-- 密码信息提示位置 -->
					<span style="font-size:15px;color:darkgreen;" id="pwd"></span></br><!-- 密码信息提示位置 -->
					
					<div id="input_size2">
						<div>
						   <input type="text"  placeholder="请输入验证码" id="input_size1" onfocus="textfocus('verifyCode')" onblur="verifyCodeIsTrue(this.value)"/><!--验证码码输入的位置-->
						   <div id="span_position">
							  <img  onclick="javascript:refreshImg(this);" src="${ctx }/common/validatecade">
						  </div><!--验证码图片的位置-->
						</div>
					</div>
					
					<span style="font-size:15px;color:darkred;" id="verifyCodemsg"></span><span style="font-size:15px;color:darkgreen;" id="verifyCode"></span></br>
					<span ><input type="checkbox" id="al" onclick="autoLogin()" checked="checked" >7天自动登录</span></br>
					<input type="hidden" id="autologin" name="autologin" value="true">
				<div>
						<input type="submit" value="登录" id="id" class="input_position"/>
				</div>
				</form>
		 <div id="fond_style">没账号:&nbsp;<a href="${ctx }/user/toregist" target="box">注册</a></div>
	</div>
	</div>
</body>
</html>