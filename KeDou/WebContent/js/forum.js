function onready(articleId,userImgPath){
	<!--点击评论创建评论条-->
    $('.commentAll').on('click','.plBtn',function(){
    	var myDate = new Date();
        //获取当前年
        var year=myDate.getFullYear();
        //获取当前月
        var month=("0"+(myDate.getMonth()+1)).slice(-2);
        //获取当前日
        var date=myDate.getDate();
        var h=myDate.getHours();       //获取当前小时数(0-23)
        var m=myDate.getMinutes();     //获取当前分钟数(0-59)
        if(m<10) m = '0' + m;
        var s=myDate.getSeconds();
        if(s<10) s = '0' + s;
        var now=year+'年'+month+"月"+date+"日 "+h+'时'+m+"分"+s+"秒";
        //获取输入内容
        var oSize = $(this).siblings('.flex-text-wrap').find('.comment-input').val();
        console.log(oSize);
     
        var param = {};
        param.comments = oSize;
		param.articleId =articleId;
		param.time = now;
		var this_temp = $(this);
        $.ajax({
    		url : "/KeDou/forum/savecomments",
    		type : "POST",
    		data :param,
    		dataType:'json',
    		success : function(data) {
    			   //动态创建评论模块
    	        oHtml = '<div class="comment-show-con clearfix"><div class="comment-show-con-img pull-left"><img src="'+userImgPath+'/'+data.userIcon+'" alt=""></div> <div class="comment-show-con-list pull-left clearfix"><div class="pl-text clearfix"> <a href="#" class="comment-size-name">'+data.userName+': </a> <span class="my-pl-con">&nbsp;'+ oSize +'</span> </div> <div class="date-dz"> <span class="date-dz-left pull-left comment-time">'+now+'</span> <div class="date-dz-right pull-right comment-pl-block"> <a href="javascript:;" class="date-dz-pl pl-hf hf-con-block pull-left">回复</a> <span class="pull-left date-dz-line">|</span> <a href="javascript:;" class="date-dz-z pull-left"><i class="date-dz-z-click-red"></i>赞 (<i class="z-num">666</i>)</a> </div> </div><div class="hf-list-con"></div></div> </div>';
    	        if(oSize.replace(/(^\s*)|(\s*$)/g, "") != ''){
    	        	this_temp.parents('.reviewArea ').siblings('.comment-show').prepend(oHtml);
    	        	this_temp.siblings('.flex-text-wrap').find('.comment-input').prop('value','').siblings('pre').find('span').text('');
    	        } 
    		},error: function(data){
    		}
    	});
    });
	<!--点击回复动态创建回复块-->
	$('.comment-show').on('click','.pl-hf',function(){
	        //获取回复人的名字
	       // var fhName = $(this).parents('.date-dz-right').parents('.date-dz').siblings('.pl-text').find('.comment-size-name').html();
	        var fhName = $(this).parents('.date-dz-right').parents('.comment-show-con-list').find('.comment-size-name').html();
	        //回复@
	        var fhN = '回复@'+fhName+'：';
	        //var oInput = $(this).parents('.date-dz-right').parents('.date-dz').siblings('.hf-con');
	        var fhHtml = ' <div class="hf-con pull-left"> <textarea id="answerbb" class="content comment-input hf-input" placeholder="'+fhN+'" onkeyup="keyUP(this)"></textarea> <a href="javascript:;" class="hf-pl">评论</a></div>';
	        //显示回复
	        if($(this).is('.hf-con-block')){
	        	
	            $(this).parents('.date-dz-right').parents('.date-dz').append(fhHtml);
	            $(this).removeClass('hf-con-block');
	            $('.content').flexText();
	            $(this).parents('.date-dz-right').siblings('.hf-con').find('.pre').css('padding','6px 15px');
	            //console.log($(this).parents('.date-dz-right').siblings('.hf-con').find('.pre'))
	            //input框自动聚焦
	            $(this).parents('.date-dz-right').siblings('.hf-con').find('.hf-input').val('').focus();
	        }else {
	            $(this).addClass('hf-con-block');
	            $(this).parents('.date-dz-right').siblings('.hf-con').remove();
	        }
	    });
    <!--评论回复块创建-->
    $('.comment-show').on('click','.hf-pl',function(){
        var oThis = $(this);
        var myDate = new Date();
        //获取当前年
        var year=myDate.getFullYear();
        //获取当前月
        var month=myDate.getMonth()+1;
        //获取当前日
        var date=myDate.getDate();
        var h=myDate.getHours();       //获取当前小时数(0-23)
        var m=myDate.getMinutes();     //获取当前分钟数(0-59)
        if(m<10) m = '0' + m;
        var s=myDate.getSeconds(); 
        if(s<10) s = '0' + s;
        var now=year+'年'+month+"月"+date+"日 "+h+'时'+m+"分"+s+"秒";
        //获取输入内容
        var oHfVal = $(this).siblings('.flex-text-wrap').find('.hf-input').val();
        console.log(oHfVal)
        //@ 人的姓名
        var oHfName = $(this).parents('.hf-con').parents('.date-dz').siblings('.pl-text').find('.comment-size-name').html();
        //回复的评论Id
        var comid =   $(this).parents('.hf-con').parents('.date-dz').siblings('.pl-text').find('.commentid').val();
        
        var oAllVal = '回复@'+oHfName;
        if(oHfVal.replace(/^ +| +$/g,'') == '' || oHfVal == oAllVal){

        }else {
            var bbs =$("#answerbb").val();
      		var params={};
      	    params.answers=bbs
      	  	params.comId =comid
      	  	params.time = now;
      	    if (bbs == "") {
      	    	alert("不能为空哦");
      	    }
      		$.ajax({
          		url : "/KeDou/forum/answertocomments",
          		type : "POST",
          		data :params,
          		dataType:'json',
          		success : function(data) {
          			var hfName = $('#answerbb').attr('placeholder');
          			var ooHtml = '<div class="all-pl-con"><div class="comment-show-con-img pull-left img_radius" ><img src="'+userImgPath+'/'+data.userIcon+'"onerror="/KeDou/img/none.jpg"></div><div class="comment-show-con-list pull-left clearfix"><div class="pl-text hfpl-text clearfix"><a href="#" class="comment-size-name">'+data.userName+': </a><span class="my-pl-con">'+hfName+bbs+'</span></div><div class="date-dz" > <span class="date-dz-left pull-left comment-time">'+now+'</span> </div></div></div>';
          		   var oHtml = '<div class="all-pl-con"><div class="pl-text hfpl-text clearfix"><a href="#" class="comment-size-name">'+data.userName +': </a><span class="my-pl-con">'+bbs+'</span></div><div class="date-dz"> <span class="date-dz-left pull-left comment-time">'+now+'</span> <div class="date-dz-right pull-right comment-pl-block">  <span class="pull-left date-dz-line">|</span> <a href="javascript:;" class="date-dz-z pull-left"><i class="date-dz-z-click-red"></i>赞 (<i class="z-num">666</i>)</a> </div> </div></div>';
                   oThis.parents('.hf-con').parents('.comment-show-con-list').find('.hf-list-con').css('display','block').prepend(ooHtml) && oThis.parents('.hf-con').siblings('.date-dz-right').find('.pl-hf').addClass('hf-con-block') && oThis.parents('.hf-con').remove();
          		},error: function(data){
          			alert("no");
          		}
          	}); 
        }
    });
//    <!--删除评论块-->
//    $('.commentAll').on('click','.removeBlock',function(){
//    	var id = $(this).attr("id");
//    	var params = {};
//    	params.id = id;
//        var oT = $(this).parents('.date-dz-right').parents('.date-dz').parents('.all-pl-con');
//        if(oT.siblings('.all-pl-con').length >= 1){
//            oT.remove();
//        }else {
//            $(this).parents('.date-dz-right').parents('.date-dz').parents('.all-pl-con').parents('.hf-list-con').css('display','none')
//            oT.remove();
//        }
//        $(this).parents('.date-dz-right').parents('.date-dz').parents('.comment-show-con-list').parents('.comment-show-con').remove();
//        $.ajax({
//      		url : "/KeDou/forum/deletecomments",
//      		type : "POST",
//      		data :params,
//      		dataType:'json',
//      		success : function(data) {
//      			if (data == "yes") {
//      				
//      			} else if (data == "no") {
//      			}
//      		},error: function(data){
//      			alert("no");
//      		}
//      	}); 
//    });
//<!--点赞-->
//$('.comment-show').on('click','.date-dz-z',function(){
//	var zNum = $(this).find('.icon-thumbs-up').html();
//    if($(this).is('.date-dz-z-click')){
//        zNum--;
//        $(this).removeClass('date-dz-z-click red');
//        $(this).find('.icon-thumbs-up').html(zNum);
//        $(this).find('.date-dz-z-click-red').removeClass('red');
//    }else {
//        zNum++;
//        $(this).addClass('date-dz-z-click');
//        $(this).find('.icon-thumbs-up').html(zNum);
//        $(this).find('.date-dz-z-click-red').addClass('red');
//    }
//
//});
}