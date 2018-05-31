
function  formsubmit(formId,uploaderId) {
	// 1. 获取 uploader 实例对象
	var uploader = $('#'+uploaderId).data('zui.uploader');

	// 2. 调用 start 方法
	uploader.start();
	
	$('#'+uploaderId).uploader().on('onFileUploaded', function(file,responseObject) {
	   //上传成功
	  if(responseObject.status==5) {
		  //将图片路径写入隐藏域
		  $('#courseImg').attr('value', responseObject.remoteData);
		  $('#'+formId).submit();
	  }else {
		 //上传失败不提交
	  }
	});
}