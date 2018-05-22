<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
       <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
    <c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="${ctx }/css/LoginRegiste.css"/>
		<title></title>
		<script src="${ctx }/js/jquery-1.11.1.min.js"></script>
		<script src="${ctx }/js/LoginRegiste.js"></script>
		<script src="${ctx }/js/md5.js"></script>
		<script type="text/javascript">
		//验证码刷新
		 function refreshImg(obj) {
			 obj.src = "${ctx }/common/validatecade?"+Math.random();
		}
		//账户是否存在
		  function acountIsExist(acount,type) {
          	var xmlhttp;
     		 if(window.XMLHttpRequest) {
     			 xmlhttp = new XMLHttpRequest();
     		 }else {
     			 xmlhttp = new ActiveXObjec("Microsoft.XMLHTTP");
     		 }
     		 xmlhttp.onreadystatechange = function() {
     			 if(xmlhttp.readyState==4&&xmlhttp.status==200) {
     				var isExist = xmlhttp.responseText;
     					if(isExist=="1"){
     						//账户可以注册   没被使用
     						document.getElementById('email').innerHTML = '账号可以使用！';
     						//设置隐藏域 value 为 用户注册使用的账号类型（邮箱  或 手机号） 
     						document.getElementById('acounttype').value = type;
     						istrue[0]=0
     						
     					}else if(isExist=="-1") {
     						//账户不可以注册 已被使用
     						document.getElementById('emailmsg').innerHTML = '账户已存在';
     						istrue[0]=-1;
     					}
     				
     			 }
     		 }
     		 xmlhttp.open("GET","${ctx }/user/isexist?acount="+acount,true);
     		 xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
     		 xmlhttp.send();
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
     						istrue[2]=-1
     					}
     			 }
     		 }
     		 xmlhttp.open("GET","${ctx }/common/istrue?verifyCode="+verifyCode,true);
     		 xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
     		 xmlhttp.send();
          }
		
		</script>
</head>
<body>
<div id="two_ifr">
			<div id="logo"><img src="${ctx }/img/logo.jpg"></div><!--logo的位置-->
			<div id="input">
				<form name="myform" action="${ctx }/user/registe" method="post" onsubmit="return endsubmit()" >
					<input type="hidden" id="acounttype" name="acounttype" value="">
					<div>&nbsp;</div>
					<div><input type="text" name="acount" onblur="textblur(this,'email')" onfocus="textfocus('email')" placeholder="输入手机号或邮箱账号" class="input_size"/></div><!--账号密码的位置-->
					<span style="font-size:15px;color:darkred;" id="emailmsg"></span><span style="font-size:15px;color:darkgreen;" id="email"></span></br>
					<div><input type="password" name="userpwd" onblur="textblur(this,'pwd')" onfocus="textfocus('pwd')" placeholder="请输入密码" class="input_size" id="inputpwd"/></div><!--账号密码的位置-->
					<span style="font-size:15px;color:darkred;" id="pwdmsg"></span><span style="font-size:15px;color:darkgreen;" id="pwd"></span></br>
					<div id="input_size2">
						<div><input type="text"  placeholder="请输入验证码" id="input_size1"onfocus="textfocus('verifyCode')" onblur="verifyCodeIsTrue(this.value)"/>
						<!--验证码码输入的位置-->
						<div id="span_position"><img onclick="javascript:refreshImg(this);" src="${ctx }/common/validatecade"></div><!--验证码图片的位置-->
						</div>
					</div>
					<span style="font-size:15px;color:darkred;" id="verifyCodemsg"></span><span style="font-size:15px;color:darkgreen;" id="verifyCode"></span></br>
					<span ><input type="checkbox" id="ch" onclick="check()">我同意协议</span></br>
				<div>
					<input type="submit" value="注册"  id="id" class="input_position" />
				</div>
				</form >
		 <div id="fond_style">已账号:&nbsp;<a href="${ctx }/user/tologin" target="box">登录</a></div>
	</div>
	</div>
</body>
</html>