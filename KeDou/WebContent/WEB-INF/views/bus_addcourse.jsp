<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
      <%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>  
    <c:set var="ctx" value="${pageContext.request.contextPath}" />
        <c:set var="courseImgPath" value="/file/course" />
<!DOCTYPE html>
<html>
<head>
	<title>添加课程</title>
	
	<link rel="stylesheet" type="text/css" href="${ctx }/css/zui.min.css">
	<link href="${ctx }/css/datetimepicker.min.css" rel="stylesheet">
	<link href="${ctx }/css/zui.uploader.min.css" rel="stylesheet">

	<!-- jQuery (ZUI中的Javascript组件依赖于jQuery) -->
	<script src="${ctx }/js/jquery.zui.js"></script>
	<!-- ZUI Javascript组件 -->
	<script src="${ctx }/js/zui.min.js"></script>
	<script src="${ctx }/js/datetimepicker.min.js"></script>
	<script src="${ctx }/js/zui.uploader.min.js"></script>
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
		});
	</script>
</head>
<body>
	<div class="container">
		<div style="width: 100%;overflow: hidden;">
			<h2 style="text-align: center;">请填写课程信息</h2><br/>
		<form class="form-horizontal" action="${ctx }/course/addcourse" method="post"  id="addcourseForm">
			<input type="hidden" id="courseImg" name="courseImg" value="">
			<input type="hidden" name="busId" value="1">
			<div class="form-group">
				<label class="col-sm-2 required">课程图片</label>
				<div class="col-md-8 col-sm-10">
		     		<div class="uploader" id="courseImgUpLoader" data-ride="uploader" data-auto-upload="false" data-file_data_name="courseImg" data-url="${ctx }/course/uploadImg">
					  <div class="uploader-message text-center">
					    <div class="content"></div>
					    <button type="button" class="close">×</button>
					  </div>
					  <div class="uploader-files file-list file-list-grid"></div>
					  <div>
					    <hr class="divider">
					    <div class="uploader-status pull-right text-muted"></div>
					    <button type="button" class="btn btn-link uploader-btn-browse"><i class="icon icon-plus"></i> 选择文件</button>
					    <button type="button" class="btn btn-link uploader-btn-start"><i class="icon icon-cloud-upload"></i> 开始上传</button>
					  </div>
					</div>
		    	</div>
			</div>
		  <div class="form-group">
		    <label class="col-sm-2 required">课程名称</label>
		    <div class="col-md-3 col-sm-10">
		      <input type="text" class="form-control"  name ="courseName"id="courseName" placeholder="课程名称"/>
		    </div>
		  </div>
		  <div class="form-group">
		    <label class="col-sm-2 required">课程类型</label>
		    <div class="col-md-3 col-sm-10">
		    	<div class="radio">
				    <label>
				      <input type="radio" name="exampleRadioOption1"/> 面授&nbsp
				    </label>
				    <label>
				      <input type="radio" name="exampleRadioOption1"/> 视频&nbsp
				    </label>
				    <label>
				      <input type="radio" name="exampleRadioOption1"/> 资源&nbsp
				    </label>
				</div>
		    </div>
		  </div>
		  <div class="form-group">
		    <label class="col-sm-2">课程描述</label>
		    <div class="col-md-3 col-sm-10">
		    	<textarea type="textarea" class="form-control" name="description" id="description" placeholder="课程描述"></textarea>
		    </div>
		  </div>
		  <div class="form-group">
		    <label class="col-sm-2 required">开始时间</label>
		    <div class="col-md-3 col-sm-10">
		      <input type="text" class="form-control form-date" name="startTime"  placeholder="选择或者输入一个日期" id="courseStartTime" >
		    </div>
		    <label class="col-sm-2 required">结束时间</label>
		    <div class="col-md-3 col-sm-10">
		      <input type="text" class="form-control form-date" name="endTime" placeholder="选择或者输入一个日期" id="courseEndTime"/>
		    </div>
		  </div>
		  <div class="form-group">
		  	<label class="col-sm-2 required">价格</label>
		    <div class="col-md-3 col-sm-10">
		      <input type="text" class="form-control" name="coursePrice" id="coursePrice" placeholder="价格"/>
		    </div>
		  </div>
		  <div class="form-group">
		    <label class="col-sm-2">地址</label>
		    <div class="col-sm-2">
		      <select class="form-control" id="exampleInputAddress1">
		        <option>北京</option>
		        <option>上海</option>
		      </select>
		    </div>
		    <div class="col-sm-2">
		      <input type="text" class="form-control" id="exampleInputAddress2" placeholder="市/县">
		    </div>
		    <div class="col-sm-4">
		      <input type="text" class="form-control" id="exampleInputAddress3" placeholder="详细地址">
		    </div>
		  </div>
		  <div class="form-group">
		    <div class="col-sm-offset-9 col-sm-10">
		      <button type="button" class="btn btn-primary" onclick="formsubmit('addcourseForm','courseImgUpLoader')">保存</button>
		    </div>
		  </div>
		</form>
		</div>
	</div>
		

</body>
</html>