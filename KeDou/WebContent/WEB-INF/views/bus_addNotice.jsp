<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
         <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
      <%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>  
    <c:set var="ctx" value="${pageContext.request.contextPath}" />
        <c:set var="courseImgPath" value="/file/course" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>新增公告</title>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" type="text/css" href="${ctx }/css/zui.min.css">
	<link href="${ctx }/css/datetimepicker.min.css" rel="stylesheet">
	<link href="${ctx }/css/zui.uploader.min.css" rel="stylesheet">


	<!-- jQuery (ZUI中的Javascript组件依赖于jQuery) -->
	<script src="${ctx }/js/jquery.zui.js"></script>
	<!-- ZUI Javascript组件 -->
	<script src="${ctx }/js/zui.min.js"></script>
	<script src="${ctx }/js/datetimepicker.min.js"></script>
	<script src="${ctx }/js/zui.uploader.min.js"></script>
	<script src="${ctx }/js/kindeditor.min.js"></script>
	<script type="text/javascript" src="${ctx }/js/busFormSubmit.js"></script>
	<script>
		//表单日历
		$(document).ready(function(){
			$(".form-date").datetimepicker(
			{
				language:  "zh-CN",
				weekStart: 1,
				todayBtn:  1,
				autoclose: 1,
				todayHighlight: 1,
				startView: 2,
				minView: 2,
				forceParse: 0,
				format: "yyyy-mm-dd"
			});

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
			    ]
			});
		});
	</script>
</head>
<body>
	<div class="container">
		<div style="width: 100%;overflow: hidden;">
			<h2 style="text-align: center;">请填写公告内容</h2><br/>
			<form class="form-horizontal" action="${ctx }/businessnotice/addNotice" method="post"  id="addNoticeForm">
				<input type="hidden" id="Img" name="img" value="">
				<input type="hidden" name="busId" value="${sessionScope.loginBusiness.busId }">
				<div class="form-group">
					<label class="col-sm-2">图片</label>
					<div class="col-md-8 col-sm-10">
						<div class="uploader" id="noticeImgUpLoader" data-ride="uploader" data-auto-upload="false" data-file_data_name="noticeImg" data-url="${ctx }/businessnotice/uploadImg">
							<div class="uploader-message text-center">
							<div class="content"></div>
							<button type="button" class="close">×</button>
							</div>
							<div class="uploader-files file-list file-list-grid"></div>
							<div>
								<hr class="divider">
								<div class="uploader-status pull-right text-muted"></div>
								<button type="button" class="btn btn-link uploader-btn-browse"><i class="icon icon-plus"></i> 选择文件</button>
							</div>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2">公告标题</label>
					<div class="col-md-3 col-sm-10">
						<input type="text" class="form-control" id="title" name="title" placeholder="标题"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2">相关课程类别</label>
					<div class="col-md-3 col-sm-10">
						<div class="radio">
							<label>
								<input type="radio" name="exampleRadioOption1"/> 四六级&nbsp
							</label>
							<label>
								<input type="radio" name="exampleRadioOption1"/> 考研&nbsp
							</label>
							<label>
								<input type="radio" name="exampleRadioOption1"/> 雅思&nbsp
							</label>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2">公告正文</label>
					<div class="col-md-6 col-sm-10">
						<textarea id="contentSimple" name="content" class="form-control kindeditorSimple"  style="height:150px;"></textarea>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2">发布时间</label>
					<div class="col-md-3 col-sm-10">
						<input type="text" class="form-control form-date" autoComplete="off" placeholder="选择或者输入一个日期" name="createtime" >
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-9 col-sm-10">
						<button type="button" class="btn btn-primary" onclick="formsubmit('addNoticeForm','noticeImgUpLoader')">保存</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</body>
</html>