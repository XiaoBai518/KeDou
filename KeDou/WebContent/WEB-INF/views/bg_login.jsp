<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
       <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
    <c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>Wopop</title>
<link href="${ctx }/Wopop_files/style_log.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="${ctx }/Wopop_files/style.css">
<link rel="stylesheet" type="text/css" href="${ctx }/Wopop_files/userpanel.css">
<link rel="stylesheet" type="text/css" href="${ctx }/Wopop_files/jquery.ui.all.css">
<script src="${ctx }/js/md5.js"></script>
<script type="text/javascript">
	function validate(){
		var account=document.bgmyform.bg_account.value;
		var pwd=document.bgmyform.bg_pwd.value;
		if(account!="请输入您的用户名"&pwd!="******"){
			document.bgmyform.bg_pwd.value=md5(pwd);
			return true;
		}else{
			alert("管理员账号和密码不能为空");
		}
		return false;
	}
</script>
</head>

<body class="login" mycollectionplug="bind">
<div class="login_m">
<div class="login_logo"><img src="${ctx }/Wopop_files/logo.png" width="196" height="46"></div>
<div class="login_boder">


<!-- 这是登陆的form -->
<form name="bgmyform" action="${ctx}/bg_admin/bglogin" method="post" onsubmit="return validate()">
<div class="login_padding" id="login_model">

  <h2>用户名</h2>
  <label>
    <input type="text" id="username" name="bg_account" class="txt_input txt_input2" onfocus="if (value ==&#39;请输入您的用户名&#39;){value =&#39;&#39;}" onblur="if (value ==&#39;&#39;){value=&#39;请输入您的用户名&#39;}" value="请输入您的用户名">
  </label>
  <h2>密码</h2>
  <label>
    <input type="password" name="bg_pwd" id="userpwd"  class="txt_input" onfocus="if (value ==&#39;******&#39;){value =&#39;&#39;}" onblur="if (value ==&#39;&#39;){value=&#39;******&#39;}" value="******">
  </label>
 
 


  <p class="forgot"><a id="iforget" href="javascript:void(0);">忘记密码?</a></p>
  <div class="rem_sub">
  <div class="rem_sub_l">
  <input type="checkbox" name="checkbox" id="save_me">
   <label for="checkbox">记住用户</label>
   </div>
    <label>
      <input type="submit" class="sub_button" name="button" id="button" value="登陆"  style="opacity: 0.7;">
      <!--  disabled标签没有true或者false选项 使用时添加disable属性即可 -->
    </label>
  </div>
</div>
</form>


<div id="forget_model" class="login_padding" style="display:none">
<br>

   <h1>Forgot password</h1>
   <br>
   <div class="forget_model_h2">(Please enter your registered email below and the system will automatically reset users’ password and send it to user’s registered email address.)</div>
    <label>
    <input type="text" id="usrmail" class="txt_input txt_input2">
   </label>

 
  <div class="rem_sub">
  <div class="rem_sub_l">
   </div>
    <label>
     <input type="submit" class="sub_buttons" name="button" id="Retrievenow" value="Retrieve now" style="opacity: 0.7;">
     　　　
     <input type="submit" class="sub_button" name="button" id="denglou" value="Return" style="opacity: 0.7;">　　
    
    </label>
  </div>
</div>






<!--login_padding  Sign up end-->
</div><!--login_boder end-->
</div><!--login_m end-->
 <br> <br>




</body></html>