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
		//重新发送激活邮件
		  function resend(email) {
       	var xmlhttp;
  		 if(window.XMLHttpRequest) {
  			 xmlhttp = new XMLHttpRequest();
  		 }else {
  			 xmlhttp = new ActiveXObjec("Microsoft.XMLHTTP");
  		 }
  		 xmlhttp.onreadystatechange = function() {
  			 if(xmlhttp.readyState==4&&xmlhttp.status==200) {
  				var b = xmlhttp.responseText;
  			
					if(isTrue=="-1") {
						//发送失败
						alert("发送失败");
					}
  			 }
  		 }
  		 xmlhttp.open("GET","${ctx }/common/resend?useremail="+email,true);
  		 xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
  		 xmlhttp.send();
       }
		</script>
</head>
<body>
      <div id="two_ifr">
      <div id="logo"><img src="${ctx }/img/logo.jpg"></div><!--logo的位置-->
       <c:if test="${info eq 'registeSucceed' }">
                      <h2>注册成功</h2></br>
			<h3>我们已将邮件发送到您的邮箱，请前往邮箱${email }进行激活</h3>
			<button onclick="resend(${email})">未收到</button>
           </c:if>
       <c:if test="${info  eq 'registeErro' }">
                      <h2>注册失败</h2></br>
			<h3>请再次进行注册</h3><a href="${ctx }/iframe_registe.jsp"></a>
       </c:if>
       <c:if test="${info  eq 'registeErro' }">
                      <h2>注册失败</h2></br>
			<h3>请再次进行注册</h3><a href="${ctx }/iframe_registe.jsp"></a>
       </c:if>
			
			
	</div>
</body>
</html>