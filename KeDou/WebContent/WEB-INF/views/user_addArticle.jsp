<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="articleImgPath" value="/file/temp" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<meta charset="utf-8">
<title>课兜儿网——写文章</title>
		<link rel="icon" href="${ctx }/img/favicon.ico" type="image/x-icon" />
   		<link rel="shortcut icon" href="${ctx }/img/favicon.ico">
   		<link rel="Bookmark" href="${ctx }/img/favicon.ico">
   		<link rel="SHORTCUT ICON" href="${ctx }/img/favicon.ico"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">

<link rel="stylesheet" href="${ctx }/css/dropify_demo.css">
<link rel="stylesheet" href="${ctx }/css/dropify.min.css">
<link rel="stylesheet" type="text/css" href="${ctx }/css/zui.min.css">
    <link rel="stylesheet" type="text/css" href="${ctx }/css/banner.css">
<script type="text/javascript" src="${ctx }/js/jquery.zui.js"></script>
<script src="${ctx }/js/zui.min.js"></script>
<script src="${ctx }/js/kindeditor.min.js"></script>
<script src="${ctx }/js/dropify.min.js"></script>


<style>
*{ padding:0; margin:0; list-style:none;}
.header{top:0px; height:47px; width:100%; background:#DCDCDC;float: top;}
.wrapper{min-width:320px; max-width:650px; margin:0 auto; overflow-x:hidden; overflow-y:auto;}
.footer{height:40px; width:100%; background:#DCDCDC;text-align:center;}
.logo1{height: 40px; width:50px; float:left; margin-left: 100px; margin-top: 5px;}
.title1{height: 40px; width:50px; float:left; margin-left: 190px; margin-top: 5px;}
.button1{height: 40px; width:50px;float:left; margin-left: 200px; margin-top: 15px;}
.button2{height: 40px; width:30px;float:left; margin-left:100px; margin-top: 15px;}
.pic{ margin-top: 20px; height: 300px;overflow-y:visible;overflow-x:hidden;}
h1{color:#A9A9A9}
h2{color:#A9A9A9}
input{outline:none;font-size:26px;font-weight: bold;height:70px;width:449px;border:0;background:#F8F8F8;}
.info{margin-top: 5px;margin-bottom: 20px;}
.info1{margin-top: 5px;margin-bottom: 0px;}
.texteditor{margin-top: 10px;margin-left:-10px; width:1000px;}
</style>
<script>
		$(document).ready(function(){
			KindEditor.create('textarea.kindeditorSimple', {
			    basePath: '${ctx}/kindeditor/',
			    bodyClass : 'article-content',
			    resizeType : 1,
			    allowPreviewEmoticons : false,
			    allowImageUpload : false,
			    items : [
			        'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
			        'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
			        'insertunorderedlist', '|', 'emoticons', 'image', 'link'
			    ],
			    afterBlur: function(){this.sync();}
			});
		});
		
	</script>
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
					<form action="${ctx }/course/searchFirstCourseList" id="searchForm" method="get">
						<div class="input-group">
						
							<div class="input-control search-box search-box-circle has-icon-left has-icon-right search-example" id="searchboxExample">
								<input id="inputSearchExample3" type="search" name="search"  class="form-control search-input" value="${searchSentence}"/>
							
								<label for="inputSearchExample3" class="input-control-icon-left search-icon">
									<i class="icon icon-search" style="line-height: 2;"></i>
								</label>
							</div>
							<span class="input-group-btn">
								<button class="btn btn-primary" type="submit">搜索</button>
							</span>
						</div>
					</form>					
				</div>
				<!--定位-->
				<div id="locate">
					<i class="icon icon-map-marker icon-2x" ></i>
					<p id="address">${sessionScope.userAddress.address }[<a href="${ctx }/useraddress/toaddress" style="color:#3280fc; text-decoration: none;">切换地址</a>]</p>
				</div>
				<!--个人头像-->
				<div id="userPad">
						 <c:if test="${not empty sessionScope.loginUser}">
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
						<li><a href="${ctx }/forum/touseraddArticle" class="nav-a">写文章 </a></li>
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
<div  style="width:100%; height:960px; background:#F8F8F8;" >
<div class="wrapper" style="height:1000px;">
	<form action="" name ="form1"enctype="multipart/form-data" method="post">
	<div class="pic" >
     <h2>添加题图</h2>
		 <input type="file" onchange="ChangeFile()" id="input-file-now" class="dropify"  data-allowed-file-extensions="jpg png jpeg" name="Img" data-default-file="${ articleImgPath}/${img }" />
	</div>
	<input type="hidden" id="info" name="info" value="${img}">
	<div class="info1"><input placeholder="请输入标题" name="title" type="text" value="${title }"/></div>
	<div class="texteditor"> 
		<div class="col-md-6 col-sm-5">
			<textarea id="content" name="content" class="form-control kindeditorSimple" style="height:150px;" placeholder="正文内容">${content}</textarea>
		</div>
  	</div>
  	<div class="button1"><button class="btn" type="submit"  onclick="preview()">预览</button></div>
	<div class="button2"><button class="btn" type="submit" onclick="publish()">发布</button></div>
  	</form> 
</div>
</div>
<div class="footer">© 2017-2018 课兜教育 石家庄有限公司版权所有 冀ICP备12011972号-4 站长统计</div>

<script>
			function preview(){
				document.form1.action="${ctx}/forum/preview";
				document.form.submit();
			}
			function publish(){
				document.form1.action="${ctx}/forum/adduserarticle";
				document.form1.submit();
			}
			function ChangeFile(){
				
				var info = document.getElementById("info")
				info.value=""
			}
			${input-file-now}
            $(document).ready(function(){
                // Basic
                $('.dropify').dropify();

                // Translated
                $('.dropify-fr').dropify({
                    messages: {
                   	 default: '插入图片或置换',
                        replace: '插入图片或置换',
						remove:	 'Supprimer',
                        error:   'Désolé, le fichier trop volumineux'
                    }
                });

                // Used events
                var drEvent = $('#input-file-events').dropify();

                drEvent.on('dropify.beforeClear', function(event, element){
                    return confirm("Do you really want to delete \"" + element.file.name + "\" ?");
                });

                drEvent.on('dropify.afterClear', function(event, element){
                    alert('File deleted');
                });

                drEvent.on('dropify.errors', function(event, element){
                    console.log('Has Errors');
                });

                var drDestroy = $('#input-file-to-destroy').dropify();
                drDestroy = drDestroy.data('dropify')
                $('#toggleDropify').on('click', function(e){
                    e.preventDefault();
                    if (drDestroy.isDropified()) {
                        drDestroy.destroy();
                    } else {
                        drDestroy.init();
                    }
                })
            });
      
</script>
</body>