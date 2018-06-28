<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
                 <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
      <%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>  
    <c:set var="ctx" value="${pageContext.request.contextPath}" />
        <c:set var="courseImgPath" value="/file/course" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>基本信息</title>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" type="text/css" href="${ctx }/css/zui.min.css">
		<link rel="stylesheet" type="text/css" href="${ctx }/css/basicinfo.css">
			<link href="${ctx }/css/zui.uploader.min.css" rel="stylesheet">
		
		<!-- jQuery (ZUI中的Javascript组件依赖于jQuery) -->
		<script src="${ctx }/js/jquery.zui.js"></script>
		<!-- ZUI Javascript组件 -->
		<script src="${ctx }/js/zui.min.js"></script>
		<script src="${ctx }/js/zui.uploader.min.js"></script>
		<script type="text/javascript">
		$(document).ready(function() {
			$('.form-horizontal').each(function() {
				$(this).submit(function(event){
					event.preventDefault();
					var form = $(this); 
					$.ajax({
					    url:'${ctx}/business/ModifybusInfo',
					    type:'POST',
					    data:form.serialize(),  
					    success:function(data){
					    
					    	var object = {};
					    	 $(form.serializeArray()).each(function(){
					    		 if(this.name=="value") {
					    			 object[this.name] = this.value;
					    		   }else  if(this.name=="key") {
					    			   object[this.name] = this.value;
					    	
					    		   } 
					    		  });
					    	 AfterAjaxShow(object["key"]);
					    	 $('#'+object["key"]).text(object["value"]);
					    },
					    error:function(xhr,textStatus){
					       	alert(textStatus);
					    }
					});
				})
			})
			
		});
		</script>
	</head>
	<body>
		<div class="container-fluid" style="padding: 20px; background-color: #F9F8F7;">
			<div class="container" style="background-color: white; padding: 20px; border-radius: 8px;box-shadow: #E5E4E1 2px 2px 12px;">
				<div id="top">
					<img src="${ctx }/img/cover.jpg">
				</div>
				<div id="content" style="padding: 50px;">
					<!-- 商家头像 -->
					<!--  <div id="buspicture">
						<img src="../images/me.png" style="border-radius: 6px;">
					</div>-->
					 <div id="buspicture ">
					 <img src="${ctx }/img/label-5.jpg"  style="border-radius: 6px;width:180px; height:180px">
					
					</div>
					<div id="basicinfo">
						<h2>${business. busName}</h2><br/>
						<div id="contact" class="autoheight" onmouseover="showContact()" onmouseout="hideContact()">
							<label class="infoitem"  title="点击修改">联系人:</label>
							<div id="editContact">
								<p id="busContact" class="info">${business.busContact }</p>
								<div id="edit1" class="edit">
									<a href="javascript:;" onclick="editContact()">
										<i class="icon icon-pencil">&nbsp;</i>修改
									</a>
								</div>
							</div>
							<div id="contactForm" class="forms">
								<form class="form-horizontal">
									<div class="form-group">
										<div class="col-md-3 col-sm-10">
											<input type="text" class="form-control" name="value" value="${business.busContact }" placeholder="联系人"/><br/>
											<input type="hidden"  name="key" value="busContact">
											<input type="hidden"  name="busid" value="${business.busId }">
											<button type="submit" class="btn btn-primary">保存</button>
											<button type="button" class="btn" onclick="cancelContact()">取消</button>
										</div>
									</div>
								</form>
							</div>
						</div>

						<br/><hr class="divider"><br/>

						<div id="tel" class="autoheight" onmouseover="showTel()" onmouseout="hideTel()">
							<label class="infoitem">电话:</label>
							<div id="editTel">
								<p id="busTel" class="info">${business.busTel }</p>
								<div id="edit2" class="edit">
									<a href="javascript:;" onclick="editTel()">
										<i class="icon icon-pencil">&nbsp;</i>修改
									</a>
								</div>
							</div>
							<div id="telForm" class="forms">
								<form class="form-horizontal">
									<div class="form-group">
										<div class="col-md-3 col-sm-10">
											<input type="text" class="form-control" name="value" value="${business.busTel }" placeholder="电话"/><br/>
											<input type="hidden"  name="key" value="busTel">
											<input type="hidden"  name="busid" value="${business.busId }">
											<button type="submit" class="btn btn-primary">保存</button>
											<button type="button" class="btn" onclick="cancelTel()">取消</button>
										</div>
									</div>
								</form>
							</div>
						</div>

						<br/><hr class="divider"><br/>

						<div id="email" class="autoheight" onmouseover="showEmail()" onmouseout="hideEmail()">
							<label class="infoitem">邮箱:</label>
							<div id="editEmail">
								<p id="busEmail" class="info">${business.busEmail}</p>
								<div id="edit3" class="edit">
									<a href="javascript:;" onclick="editEmail()">
										<i class="icon icon-pencil">&nbsp;</i>修改
									</a>
								</div>
							</div>
							<div id="emailForm" class="forms">
								<form class="form-horizontal">
									<div class="form-group">
										<div class="col-md-3 col-sm-10">
											<input type="text" class="form-control" name="value" value="${business.busEmail}"  placeholder="邮箱"/><br/>
											<input type="hidden"  name="key" value="busEmail">
											<input type="hidden"  name="busid" value="${business.busId }">
											<button type="submit" class="btn btn-primary">保存</button>
											<button type="button" class="btn" onclick="cancelEmail()">取消</button>
										</div>
									</div>
								</form>
							</div>
						</div>

						<br/><hr class="divider"><br/>

						<div id="license" class="autoheight" onmouseover="showLicense()" onmouseout="hideLicense()">
							<label class="infoitem">营业执照编号:</label>
							<div id="editLicense">
								<p id="busLicense" class="info">${business.busLicense}</p>
								<div id="edit4" class="edit">
									<a href="javascript:;" onclick="editLicense()">
										<i class="icon icon-pencil">&nbsp;</i>修改
									</a>
								</div>
							</div>
							<div id="licenseForm" class="forms">
								<form class="form-horizontal">
									<div class="form-group">
										<div class="col-md-3 col-sm-10">
											<input type="text" class="form-control" name="value" value="${business.busLicense}" placeholder="营业执照编号"/><br/>
											<input type="hidden"  name="key" value="busLicense">
											<input type="hidden"  name="busid" value="${business.busId }">
											<button type="submit" class="btn btn-primary">保存</button>
											<button type="button" class="btn" onclick="cancelLicense()">取消</button>
										</div>
									</div>
								</form>
							</div>
						</div>

						<br/><hr class="divider"><br/>

						<div id="address" class="autoheight" onmouseover="showAddress()" onmouseout="hideAddress()">
							<label class="infoitem">公司地址:</label>
							<div id="editAddress">
								<p id="busAddress" class="info">${business.busAddress}</p>
								<div id="edit5" class="edit">
									<a href="javascript:;" onclick="editAddress()">
										<i class="icon icon-pencil">&nbsp;</i>修改
									</a>
								</div>
							</div>
							<div id="addressForm" class="forms">
								<form class="form-horizontal">
									<div class="form-group">
										<div class="col-md-3 col-sm-10">
											<input type="text" class="form-control" name="value" value="${business.busAddress}" placeholder="公司地址"/><br/>
											<input type="hidden"  name="key" value="busAddress">
											<input type="hidden"  name="busid" value="${business.busId }">
											<button type="submit" class="btn btn-primary">保存</button>
											<button type="button" class="btn" onclick="cancelAddress()">取消</button>
										</div>
									</div>
								</form>
							</div>
						</div>

						<br/><hr class="divider"><br/>

						<div id="corporate" class="autoheight" onmouseover="showCorporate()" onmouseout="hideCorporate()">
							<label class="infoitem">公司法人:</label>
							<div id="editCorporate">
								<p id="busCorporate" class="info">${business.busCorporate}</p>
								<div id="edit6" class="edit">
									<a href="javascript:;" onclick="editCorporate()">
										<i class="icon icon-pencil">&nbsp;</i>修改
									</a>
								</div>
							</div>
							<div id="corporateForm" class="forms">
								<form class="form-horizontal">
									<div class="form-group">
										<div class="col-md-3 col-sm-10">
											<input type="text" class="form-control" name="value" value="${business.busCorporate}" placeholder="公司法人"/><br/>
											<input type="hidden"  name="key" value="busCorporate">
											<input type="hidden"  name="busid" value="${business.busId }">
											<button type="submit" class="btn btn-primary">保存</button>
											<button type="button" class="btn" onclick="cancelCorporate()">取消</button>
										</div>
									</div>
								</form>
							</div>
						</div>

						<br/><hr class="divider"><br/>
					</div>
				</div>
			</div>
		</div>
		<script>
			// 联系人
			function showContact(){
				$("#edit1").show();
			}
			function hideContact(){
				$("#edit1").hide();
			}
			function editContact(){
				$("#contactForm").show();
				$("#editContact").hide();
			}
			function cancelContact(){
				$("#contactForm").hide();
				$("#contact").show();
				$("#editContact").show();
			}
			// 电话
			function showTel(){
				$("#edit2").show();
			}
			function hideTel(){
				$("#edit2").hide();
			}
			function editTel(){
				$("#telForm").show();
				$("#editTel").hide();
			}
			function cancelTel(){
				$("#telForm").hide();
				$("#tel").show();
				$("#editTel").show();
			}
			// 邮箱
			function showEmail(){
				$("#edit3").show();
			}
			function hideEmail(){
				$("#edit3").hide();
			}
			function editEmail(){
				$("#emailForm").show();
				$("#editEmail").hide();
			}
			function cancelEmail(){
				$("#emailForm").hide();
				$("#email").show();
				$("#editEmail").show();
			}
			//营业执照号
			function showLicense(){
				$("#edit4").show();
			}
			function hideLicense(){
				$("#edit4").hide();
			}
			function editLicense(){
				$("#licenseForm").show();
				$("#editLicense").hide();
			}
			function cancelLicense(){
				$("#licenseForm").hide();
				$("#license").show();
				$("#editLicense").show();
			}
			//公司地址
			function showAddress(){
				$("#edit5").show();
			}
			function hideAddress(){
				$("#edit5").hide();
			}
			function editAddress(){
				$("#addressForm").show();
				$("#editAddress").hide();
			}
			function cancelAddress(){
				$("#addressForm").hide();
				$("#address").show();
				$("#editAddress").show();
			}
			//公司法人
			function showCorporate(){
				$("#edit6").show();
			}
			function hideCorporate(){
				$("#edit6").hide();
			}
			function editCorporate(){
				$("#corporateForm").show();
				$("#editCorporate").hide();
			}
			function cancelCorporate(){
				$("#corporateForm").hide();
				$("#corporate").show();
				$("#editCorporate").show();
			}
			
			//AJAX更新完显示更新后的数据
			function AfterAjaxShow(type) {
				
				var A_type = type.substring(3);
				var B_type = type.substring(3).toLowerCase();
				
				$("#"+B_type+"Form").hide();
				$("#"+B_type).show();
				$("#edit"+A_type).show();
				
			}
		</script>
	</body>
</html>""