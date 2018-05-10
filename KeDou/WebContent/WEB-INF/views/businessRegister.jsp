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
        <title>商家入驻</title>
        <link rel="stylesheet" href="${ctx }/css/bootstrap.min.css">
        <link rel="stylesheet" href="${ctx }/css/busfont-awesome.min.css">
		<link rel="stylesheet" href="${ctx }/css/busform-elements.css">
        <link rel="stylesheet" href="${ctx }/css/busstyle.css">
        
         <!-- Javascript -->
        <script src="${ctx }/js/jquery-1.11.1.min.js"></script>
   		<script src="${ctx }/js/bootstrap.min.js"></script>
        <script src="${ctx }/js/jquery.backstretch.min.js"></script>
        <script src="${ctx }/js/scripts.js"></script>  
        <script src="${ctx }/js/md5.js"></script>  
        <script type="text/javascript">
      //账户是否存在
		  function acountIsExist(acount) {
        	var xmlhttp;
   		 if(window.XMLHttpRequest) {
   			 xmlhttp = new XMLHttpRequest();
   		 }else {
   			 xmlhttp = new ActiveXObjec("Microsoft.XMLHTTP");
   		 }
   		 xmlhttp.onreadystatechange = function() {
   			 if(xmlhttp.readyState==4&&xmlhttp.status==200) {
   				var isExist = xmlhttp.responseText;
   					if(isExist=="-1") {
   						//账户不可以注册 已被使用
   						 addWarn($('#alertwarning'),"账号已经被使用！");
   						$("#busAccount").addClass('input-error');
   					}
   				
   			 }
   		 }
   		 xmlhttp.open("GET","${ctx }/business/isexist?acount="+acount,true);
   		 xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
   		 xmlhttp.send();
        }
        </script>
    </head>
<body>
        <!-- Top content -->
        <div class="top-content">
        	
            <div class="inner-bg">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-sm-8 col-sm-offset-2 text">
                            <h1>入驻&nbsp<strong>课兜</strong>&nbsp&nbsp&nbsp只需<strong>&nbsp3&nbsp</strong>步</h1>
                            <div class="description">
                            	<p>
	                            	请认真填写您的真实信息, 以便快速通过我们的审核!
                            	</p>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-6 col-sm-offset-3 form-box">
                        <c:if test="${info eq 'success' }">
                        			<div class="form-top">
		                        		<div class="form-top-left">
		                        			<h3>信息已提交</h3>
		                        		</div>
		                        	</div>
		                        	<div class="form-bottom">
		                        		<h4>请耐心等待审核...</h4>
		                        		<button type="submit" class="btn">返回首页</button>
		                        	</div>
                        </c:if>
                         <c:if test="${info eq 'bad' }">
                        			<div class="form-top">
		                        		<div class="form-top-left">
		                        			<h3>信息提交出错</h3>
		                        		</div>
		                        	</div>
		                        	<div class="form-bottom">
		                        		<h4>请再次提交...</h4>
		                        		<button type="submit" class="btn">返回首页</button>
		                        	</div>
                        </c:if>
                        <c:if test="${empty info  }">
                        		<form role="form" action="${ctx }/business/registe" method="post" class="registration-form" onsubmit="return endsubmit()">
                        		
                        		<fieldset style="display: block;" >
		                        	<div class="form-top">
		                        		<div class="form-top-left">
		                        			<h3>Step 1 / 3</h3>
		                            		<p>设置用户名和密码</p>
		                        		</div>
		                            </div>
		                            <div class="form-bottom">
				                    	<div class="form-group">
				                    		<label class="sr-only" for="form-first-name">用户名</label>
				                        	<input type="text" name="busAccount" onblur="acountIsExist(this.value)"  placeholder="请输入5-20位 任选数字、字母和下划线的组合" class="form-first-name form-control" id="busAccount">
				                        </div>
				                        <div class="form-group">
				                        	<label class="sr-only" for="form-last-name">密码</label>
				                        	<input type="password" name="busPwd" placeholder="请输入6-16位数字和字母的组合" class="form-last-name form-control" id="busPwd">
				                        </div>
				                        <div class="alert alert-warning" id="alertwarning" style="display: none;">
				                        	<a href="#" class="close" data-dismiss="alert">&times;</a>
				                        	<!--填写敬告信息-->
				                        	<strong></strong>
				                        </div>
				                        <button type="button" class="btn btn-next" id="btn-next1">下一步</button>
				                          <span>已账号？<a href="${ctx }/business/tologin">登录</a></span>
				                    </div>
			                    </fieldset>
			                    
			                    <fieldset >
		                        	<div class="form-top">
		                        		<div class="form-top-left">
		                        			<h3>Step 2 / 3</h3>
		                            		<p>填写联系人信息</p>
		                        		</div>
		                            </div>
		                            <div class="form-bottom">
				                        <div class="form-group">
				                        	<label class="sr-only" for="form-email">联系人姓名</label>
				                        	<input type="text" name="busContact" placeholder="联系人姓名" class="form-email form-control" id="busContact">
				                        </div>
				                        <div class="form-group">
				                    		<label class="sr-only" for="form-password">联系电话</label>
				                        	<input type="text" name="busTel" placeholder="联系电话" class="form-password form-control" id="busTel">
				                        </div>
				                        <div class="form-group">
				                        	<label class="sr-only" for="form-repeat-password">电子邮箱</label>
				                        	<input type="text" name="busEmail" placeholder="电子邮箱" class="form-repeat-password form-control" id="busEmail">
				                        </div>
				                        <div class="alert alert-warning" id="alertwarning" style="display: none;">
				                        	<a href="#" class="close" data-dismiss="alert">&times;</a>
				                        	<!--填写敬告信息-->
				                        	<strong></strong>
				                        </div>
				                        <button type="button" class="btn btn-previous">上一步</button>
				                        <button type="button" class="btn btn-next" id="btn-next2">下一步</button>
				                          <span>已账号？<a href="${ctx }/business/tologin">登录</a></span>
				                    </div>
			                    </fieldset>
			                    
			                    <fieldset >
		                        	<div class="form-top">
		                        		<div class="form-top-left">
		                        			<h3>Step 3 / 3</h3>
		                            		<p>填写商家信息</p>
		                        		</div>
		                            </div>
		                            <div class="form-bottom">
				                    	<div class="form-group">
				                    		<label class="sr-only" for="form-facebook">公司名称</label>
				                        	<input type="text" name="busName" placeholder="公司名称" class="form-facebook form-control" id="busName">
				                        </div>
				                        <div class="form-group">
				                        	<label class="sr-only" for="form-twitter">法人姓名</label>
				                        	<input type="text" name="busLicense" placeholder="法人姓名" class="form-twitter form-control" id="busLicense">
				                        </div>
				                        <div class="form-group">
				                        	<label class="sr-only" for="form-google-plus">营业执照信息</label>
				                        	<input type="text" name="busCorporate" placeholder="营业执照信息" class="form-google-plus form-control" id="busCorporate">
				                        </div>
				                        <div class="form-group">
				                        	<label class="sr-only" for="form-google-plus">公司地址</label>
				                        	<input type="text" name="busAddress" placeholder="公司地址" class="form-google-plus form-control" id="busAddress">
				                        </div>
				                        <div class="alert alert-warning" id="alertwarning" style="display: none;">
				                        	<a href="#" class="close" data-dismiss="alert">&times;</a>
				                        	<!--填写敬告信息-->
				                        	<strong></strong>
				                        </div>
				                        <button type="button" class="btn btn-previous">上一步</button>
				                        <button type="submit" class="btn btn-next" id="btn-next3">完成</button>
				                        <span>已账号？<a href="${ctx }/business/tologin">登录</a></span>
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
					top: -155.667px;"/>
	</div>
	</body>
</html>