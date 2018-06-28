<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
       <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
    <c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="${ctx }/css/LoginRegiste.css"/>
		<script src="${ctx }/js/LoginRegiste.js"></script>
		<script src="${ctx }/js/md5.js"></script>
		<script type="text/javascript" src="${ctx}/js/zui.min.js"></script>
		<title>telregiste</title>
		<script>
		//验证码是否正确
		  function CheckCodeIsTrue(verifyCode) {
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
     						document.getElementById("verifyCodemsg").innerHTML = '';
     						istrue[2]=0
     					}else if(isTrue=="-1") {
     						//验证码不正确
     						document.getElementById("verifyCodemsg").innerHTML = '验证码不正确！';
     						istrue[2]=-1
     					}
     			 }
     		 }
     		 xmlhttp.open("GET","${ctx }/user/checkcode?telcode="+verifyCode,true);
     		 xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
     		 xmlhttp.send();
          }
		
		</script>
</head>
<body style="width:400px;height:600px;margin-left:-5px;">
		<div id="logotelregiste"><img src="${ctx }/img/logo.jpg"></div><!--logo的位置-->
		<div class="Promptinformation1">填写短信验证码密码完成注册</div>
		<div class="Promptinformation2">短信验证码已发送至:<span style="color:red;">${telnum}</span></div>
		<form name="myform" action="${ctx }/user/registe" method="post" onsubmit="return endtelsubmit()" target="_top">
			<div class="Telinput_position">
				<input type="hidden" name="acount" value="${telnum}">
				<input type="hidden" name="acounttype" value="${acounttype }">
				<div class="telregisteinput1">
					<input type="text" id="code" placeholder="请输入验证码" name="TelCheckNum" class="Telinput_size1" onblur="CheckCodeIsTrue(this.value)"/>
					<div class="time">
                    <div id="text_1"  name="textss">60秒</div>
                    </div>
					<div class="telchecknumspan" id="seconddiv">后点击重新发送</div>
					<a href="${ctx }/user/sendTelverifyCode?telnum=${telnum}"><div class="telchecknumspan2" id="sendangin">后点击重新发送</div></a>
				</div>
				<div class="telcheckpwdstyle" id="verifyCodemsg"></div>
				<div>
					<input type="password" id="userpwd" placeholder="请输入密码，6-16位数字和字母的组合" name="userpwd" class="Telinput_size2" onblur="textblur(this,'pwd')" onfocus="textfocus('pwd')"/>
				</div>
				<div class="telcheckpwdstyle" id="pwdmsg"></div>
			</div>
			<div class="buttominput" >	
					<span >
						<input type="checkbox" id="ch" onclick="check('ch')">我同意协议   
					</span></br>
					<input type="submit" value="注册"  id="id" class="input_position" />
			</div>
		</form>
		<a href="${ctx }/t/returnchangetel" ><div class="returnChange">返回更改手机号</div></a>
		<script type="text/javascript">
	    var end=60;//初始化给个值为30
	    for(var i=1;i<=end;i++){
	        window.setTimeout("update("+i+")",i*1000); //当i=30时执行update方法
	    }
		</script>
</body>
</html>