<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
         <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
    <c:set var="ctx" value="${pageContext.request.contextPath}" />
      <c:set var="courseImgPath" value="/file/course" />  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
		<meta charset="UTF-8">
		<title>Information binding</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/css/InformationBinding.css"/>
		<link rel="stylesheet" href="${ctx}/css/zui.min.css"/>
		<script type="text/javascript" src="${ctx }/js/jquery-2.2.0.min.js"></script>
		<script src="${ctx }/js/personal.js"></script>
	<script type="text/javascript">
	$(document).ready(function() {
		var tel =$('#texttel').text();
		var teltext = tel.substr(0,3)+'****'+tel.substr(7);
		$('#texttel').text(teltext);
		var mail =$('#textmail').text();
		var mailtext = mail.substr(0,3)+'***'+mail.substr(7);
		$('#textmail').text(mailtext);
	});
	</script>
</head>
<body style="background-color: #FAFAFA;">
<div class="loginTime">上次登陆时间：${user.lastLoginTime}</div>
	<div class="wordType">账号绑定 </div>
	<hr class="hrType"/>
	<div class="boxType"> 
		<div class="boxImg"><i class="icon icon-envelope icon-2x"></i></div>
		<div class="boxWordType">
			<div class="box-div1">
				<span class="box-p1">邮箱</span> 
				<c:if test="${user.userEmail==null}"><span class="box-p2">没有绑定</span></c:if>
				<c:if test="${user.userEmail!=null}"><span class="box-p2" id="textmail">${user.userEmail}</span></c:if> 
			</div>
			<div class="box-div2">可用邮箱加密码登录慕课网，可用邮箱找回密码</div>
		</div>
		<div class="boxButton-position">
			<c:if test="${user.userEmail==null}"><button type="button"  class="boxButton-type" onclick="callParent('bingmail')"> &nbsp;&nbsp; 立即绑定 &nbsp; &nbsp; </button></c:if>
			<c:if test="${user.userEmail!=null}"><button type="button"  class="boxButton-type" onclick="callParent('changemail')"> &nbsp;&nbsp; 更改 &nbsp; &nbsp; </button></c:if>		
		</div>
	</div>
	<hr class="hrType"/>
	<div class="boxType"> 
		<div class="boxImg"><i class="icon icon-mobile icon-3x"></i></div>
		<div class="boxWordType">
			<div class="box-div1">
				<span class="box-p1">手机号</span> 
				<c:if test="${user.userTel==null}"><span class="box-p2">没有绑定</span></c:if>
				<c:if test="${user.userTel!=null}"><span class="box-p2" id="texttel">${user.userTel}</span></c:if>
			</div>
			<div class="box-div2">可用邮箱加密码登录课兜网，可用邮箱找回密码</div>
		</div>
		<div class="boxButton-position">
			<c:if test="${user.userTel==null}"><button class="boxButton-type" onclick="callParent('bingTel')"> &nbsp;&nbsp; 立即绑定 &nbsp; &nbsp; </button></c:if>
			<c:if test="${user.userTel!=null}"><button class="boxButton-type" onclick="callParent('changeTel')"> &nbsp;&nbsp; 更改 &nbsp; &nbsp; </button></c:if>	
		</div>
	</div>
	<hr class="hrType"/>
	<div class="boxType"> 
		<div class="boxImg"><i class="icon icon-shield icon-2x"></i></div>
		<div class="boxWordType">
			<div class="box-div1">
				<span class="box-p1">密码</span> 
				<span class="box-p2"></span> 
			</div>
			<div class="box-div2">用于保护账号信息和登录安全</div>
		</div>
		<div class="boxButton-position">
			<button type="button" class="boxButton-type" onclick="callParent('changepwd')"> &nbsp;&nbsp; 修改 &nbsp; &nbsp; </button>
		</div>
	</div>
	<hr class="hrType"/>
	<div class="boxType"> 
		<div class="boxImg"><i class="icon icon-edit icon-2x"></i></div>
		<div class="boxWordType">
			<div class="box-div1">
				<span class="box-p1">第三方登陆</span> 
				<span class="box-p2"></span> </div>
			<div class="box-div2">绑定第三方帐号，可以直接登录，还可以将内容同步到以下平台，与更多好友分享</div>
		</div>
	</div>
	<div class="bottom-Box-Type">
		<div class="thirdLogin-box">
			<div class="imgType"><i class="icon icon-wechat icon-5x"></i></div>
			<div class="imgRightType">
				<span class="spanType1">微信</span><br/>
				<span class="spanType2">未绑定</span><br/>
				<button class="span-buttom-button">绑定</button>
			</div>
		</div>
		<div class="thirdLogin-box">
			<div class="imgType"><i class="icon icon-qq icon-5x"></i></div>
			<div class="imgRightType">
				<span class="spanType1">微信</span><br/>
				<span class="spanType2">未绑定</span><br/>
				<button class="span-buttom-button">绑定</button>
			</div>
		</div>	
	</div>
	<script>
	function callParent(text){
			parent.say(text);
		};
	</script>

</body>
</html>
