$(document).ready(function() {
	//左侧头像的样式变化
    	$("div[id='image']").on('mouseover',function() {
    		$("div[id='image_change']").attr("style",'display: block');
    	});
    	$("div[id='image_change']").on('mouseout',function(){
    		$("div[id='image_change']").attr("style",'display: none');
    	});
   //更换页面的样式变化 	
		$("div[id^='usermessage']").each(function() {
			$(this).click(function(){
				if($(this).attr("id")=="usermessage1") {
					$('#buton1').attr('style','display: block');
				}else {
					$('#buton1').attr('style','display: none');
				}
				$("div[id^='usermessage']").each(function() {
					$(this).removeClass('usermessage111').addClass('usermessage5'); 
				});
				
				$(this).addClass('usermessage111').removeClass('usermessage5'); 
			});
		});
		//判断浏览器是否支持FileReader接口
		if (typeof FileReader == 'undefined') {
		   alert("当前浏览器不支持图片预览功能");
		    //使选择控件不可操作
		    $("xdaTanFileImg").attr("disabled", "disabled");
		}
	
});


function getAlertText(type,imgpath) {
	if(type=="personal") {
		var oLogin=document.createElement("div");
		oLogin.id="messagechange";
		oLogin.innerHTML="<div id='message'>"+
		                     "<div id='close'>&times;</div>"
		                     +"<div class='change_meg'>编辑个人信息</div>"
		                     +	"<form  action='/KeDou/user/updateUserMessage' onkeydown='return keyDown(event)' method='post'>"
		                     +		"<div class='user_name'>"
		                     +			"<div class='user_name1'>昵称：</div>"
		                     +          "<div class='user_name3'><input type='text' name='username' class='user_name2'/></div>"
		                     +      "</div>"
		                     +		"<div class='user_gender'>"
		                     +			"<div class='user_gender1'>性别：</div>"
		                     +			"<div class='user_gender2'/><input type='radio'  name='gender' value='男'/>男</div>"
		                     +          "<div class='user_gender2'/><input type='radio'  name='gender' value='女'/>女</div>"
		                     +      "</div>"
		                     +		"<div class='user_address'>"
		                     +			"<div class='user_address1'>地址：</div>"
		                     +          "<div class='user_address2'><select>"
							 +											"<option value='volvo'>省</option>"
							 +											"<option value='saab'>Saab</option>"
							 +											"<option value='opel'>Opel</option>"
							 +											"<option value='audi'>Audi</option>"
							 +										"</select>"
							 +			"</div>"
		                     +          "<div class='user_address2'><select>"
							 +											"<option value='volvo'>市</option>"
							 +											"<option value='saab'>Saab</option>"
							 +											"<option value='opel'>Opel</option>"
							 +											"<option value='audi'>Audi</option>"
							 +										"</select>"
							 +			"</div>"
		                     +          "<div class='user_address2'><select>"
							 +											"<option value='volvo'>区县</option>"
							 +											"<option value='saab'>Saab</option>"
							 +											"<option value='opel'>Opel</option>"
							 +											"<option value='audi'>Audi</option>"
							 +										"</select>"
							 +			"</div>"
		                     +      "</div>"
		                     +      "<div class='textarea'>"
		                     +			"<div class='textarea1'>个人描述：</div>"
		                     +			"<div class='textarea2'><textarea name='userDiscription' rows='4' cols='38' class='textarea3'  onchange='this.value=this.value.substring(0, 128)' onkeydown='this.value=this.value.substring(0, 128)' onkeyup='this.value=this.value.substring(0, 128)'></textarea></div>"
		                     +		"</div>"
		                     +      "<div class='button0'>"
		                     +           "<div><button  id='id'  class='button1'/>取消</div>"
		                     +           "<div><input type='submit' id='id' value='确定' class='button2'/></div>"
		                     +      "</div>"
		                     +	"</form>"
		                  +"</div>"
	}else if(type=="chanageImg") {
		var oLogin=document.createElement("div");
		oLogin.id="messagechange";
		oLogin.innerHTML="<div id='message'>"+
		                     "<div id='close'>&times;</div>"
		                     +"<div class='change_meg'>更改头像</div>"
		                     +     "<div class='previewImg'><img src="+ imgpath +" id='previewImg'> </div>"
		                     +	   "<form  name='personImg' action='/KeDou/user/imgUpload' onkeydown='return keyDown(event)'  method='post' enctype='multipart/form-data' >"
		                     +     "<div class='inputFile'> <a href='javascript:;' class='file'>上传头像<input type='file' name='personImgFile' onchange='preview(this)' id='personalFile'></a></div>"  
		                     +		"<div class='changeImageSubmit'> <a href='javascript:;' class='file' onclick='document.personImg.submit();'>确定</a></div>"
		                     +      "</form>"
		                     +	"</form>"
		                  +"</div>"
	}
	
	                  return oLogin;
}
function check(type,imgpath){  
	var bton=document.getElementById("buton1");//获取button按钮
		
		//获取页面高度和宽度
		var sHeight=document.documentElement.scrollHeight;
		var sWidth=document.documentElement.scrollWidth;
		
		//可视区域的高度和宽度
		var wHeight=document.documentElement.clientHeight;
		
		var oMask=document.createElement("div");
			oMask.id="mask";
			oMask.style.height=sHeight+"px";
			oMask.style.Width=sWidth+"px";
			document.body.appendChild(oMask);
		
			//获取弹框内容
			var oLogin = getAlertText(type,imgpath);
			document.body.appendChild(oLogin);
			
		//获取login的高度和宽度
		var dHeight=oLogin.offsetHeight;
		var dWidth=oLogin.offsetWidth;
		oLogin.style.left=(sWidth-dWidth)/2+"px";
		oLogin.style.top=(wHeight-dHeight)/2+"px";	
		
		var oClose=document.getElementById("close");
			oMask.onclick=oClose.onclick=function(){
				document.body.removeChild(oMask);
				document.body.removeChild(oLogin);
			}
		

	
}
//预览
function preview(obj){
	  var file = obj.files[0];
	    
	    var reader = new FileReader();

	    reader.onload = function (e) {
	    	
	       var img = document.getElementById("previewImg");
	        img.src = e.target.result;
	        //或者 img.src = this.result;  //e.target == this
	    }

	    reader.readAsDataURL(file);
};
