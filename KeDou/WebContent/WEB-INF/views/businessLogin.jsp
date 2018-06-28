<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
       <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
    <c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>商家登录</title>
		<link rel="icon" href="${ctx }/img/favicon.ico" type="image/x-icon" />
   		<link rel="shortcut icon" href="${ctx }/img/favicon.ico">
   		<link rel="Bookmark" href="${ctx }/img/favicon.ico">
   		<link rel="SHORTCUT ICON" href="${ctx }/img/favicon.ico"/>
        <link rel="stylesheet" href="${ctx }/css/bootstrap.min.css">
        <link rel="stylesheet" href="${ctx }/css/busfont-awesome.min.css">
        <link rel="stylesheet" href="${ctx }/css/busform-elements.css">
       <link rel="stylesheet" href="${ctx }/css/busstyle.css">
  		
  		        <!-- Javascript -->
        
        <script src="${ctx }/js/jquery-2.1.1.min.js"></script>
        <script src="${ctx }/js/bootstrap.min.js"></script>
        <script src="${ctx }/js/jquery.backstretch.min.js"></script>
        <script src="${ctx }/js/scripts.js"></script>
                <script src="${ctx }/js/md5.js"></script>  
        <script type="text/javascript">
        $(window).load(function() {
        	var error = "${error}";
        	
          	if(error!=null&&error!="") {
          		alert(error)
        		if(error=="NoAcountErro") {
        			ifsubmit[0] = false;
              	  addWarn($('#alertwarning'),"账号不存在！")
        		}else if(error=="PwdErro") {
        			ifsubmit[1] = false;
              	  	 addWarn($('#alertwarning'),"密码错误！")
        		}
        		
        	}
        	
            
          });
        
   			function refresh(obj) {
   			 	obj.src = "${ctx }/common/validatecade?"+Math.random();
    		}
   			function keyDown(e) {
   				var keynum;
   				keynum = window.event?e.keyCode:e.which;
   				if(keynum ==13) {
   					return false;
   				}
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
      				 if(isTrue=="-1") {
   						//验证码不正确
   						   ifsubmit[2] = false;
   					}else if(isTrue=="1"){
   						//验证码正确
							ifsubmit[2] = true;
   					 		 
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
        <!-- Top content -->
        <div class="top-content">
        	
            <div class="inner-bg">
                <div class="container">
                    <div class="row">
                        <div class="col-sm-8 col-sm-offset-2 text">
                            <h1>登录&nbsp<strong>课兜</strong></h1>
                            <div class="description">
                            	<p>
	                            	请认真填写您的真实信息, 以便快速通过我们的审核!
                            	</p>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-6 col-sm-offset-3 form-box">
                        	<c:if test="${info eq 'stateActiveErro' }">
                        			<div class="form-top">
		                        		<div class="form-top-left">
		                        			<h3>账号未激活</h3>
		                        		</div>
		                        	</div>
		                        	<div class="form-bottom">
		                        		<h4>请耐心等待审核...</h4>
		                        		<button type="button" class="btn" onclick="window.location='${ctx}/common/toindex'">返回首页</button>
		                        	</div>
                        	</c:if>
                        	   	<c:if test="${info eq 'stateLockErro' }">
                        			<div class="form-top">
		                        		<div class="form-top-left">
		                        			<h3>账号已被锁定</h3>
		                        		</div>
		                        	</div>
		                        	<div class="form-bottom">
		                        		<h4>请与客服联系...</h4>
		                        		<button type="button" class="btn" onclick="window.location='${ctx}/common/toindex'">返回首页</button>
		                        	</div>
                        		</c:if>
                        		
                        	<c:if test="${empty info  }">
                        	<form role="form" action="${ctx }/business/login" method="post" onkeydown="return keyDown(event)" class="registration-form">
                        		
                        		<fieldset style="display: block;">
		                        	<div class="form-top">
		                        		<div class="form-top-left">
		                        			<h3>欢迎登录</h3>
		                        		</div>
		                            </div>
		                            <div class="form-bottom">
				                    	<div class="form-group">
				                    		<label class="sr-only" for="form-first-name">用户名</label>
				                        	<input type="text" name="busAccount" placeholder="用户名" class="form-first-name form-control" id="busAccount">
				                        </div>
				                        <div class="form-group">
				                        	<label class="sr-only" for="form-last-name">密码</label>
				                        	<input type="password" name="busPwd" placeholder="密码" class="form-last-name form-control" id="busPwd">
				                        </div>
				                        <div class="form-group">
        									<input type="text" name="randomCode" placeholder="验证码" class="form-last-name form-control" id="veCode" onblur="verifyCodeIsTrue(this.value)" ><span>&nbsp&nbsp&nbsp</span>
        									<img title="点击更换" onclick="javascript:refresh(this);" src="${ctx }/common/validatecade" >
				                        </div><br/>
				                         <div class="alert alert-warning" id="alertwarning" style="display: none;">
				                        	<a href="#" class="close" data-dismiss="alert">&times;</a>
				                        	<!--填写敬告信息-->
				                        	<strong></strong>
				                        </div>
				                        <button type="submit" class="btn">完成</button>
				                        <span>没有账号？<a href="${ctx }/business/toregiste">注册</a></span>
				                    </div>
			                    </fieldset>
		                    </form>
		                   </c:if>
						</div>
					</div>
				</div>
			</div>    
        </div>


	<div class="backstretch" style="
					left: 0px; top: 0px; 
					margin: 0px; 
					padding: 0px; 
					height: 588px; 
					width: 100%; 
					z-index: -999999; 
					position: fixed;">
	<img src="${ctx }/img/blue.jpg" style="
					position: absolute; 
					margin: 0px; 
					padding: 0px; 
					border: none; 
					width: 100%; 
					height: 900px; 
					max-height: none; 
					max-width: none; 
					z-index: -999999; 
					left: 0px; 
					top: -155.667px;">
	</div>
	</body>
</html>