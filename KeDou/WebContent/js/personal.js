
	istrue2 = new Array(-1,-1,-1);

$(document).ready(function() {
	//禁用更改密码提交按钮
	$("#submitButton").attr("disabled", true);
	
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
		
		/**全选按钮功能*/
		$("input[name='tempString']").on('click', function(){
			//设置选择效果
			var k = 0;
			var l = 0;
			$("input[name='tempString']").each(function(){
				//没有选中
				if(!$(this).is(':checked')) {
					k = 1;
				}else {
					//被选中
					l = 1;
				}
			});
			if(k==0){
				$("#allCheckBox").prop("checked",true);
			}else{
				$("#allCheckBox").removeAttr("checked");
			}
			
			if(l==1){
				$("button[name='delete']").attr("disabled",false);
				$("button[name='delete']").css("cursor","pointer");
				$("button[name='delete']").css("opacity","1");
			}else{
				$("button[name='delete']").attr("disabled",true);
				$("button[name='delete']").css("cursor","not-allowed");
				$("button[name='delete']").css("opacity","0.6");
			}
			
			
		});
		$("input[id='allCheckBox']").on('click', function(){
			//设置全选效果
			$("input[name='tempString']").each(function(){
				if($("#allCheckBox").is(':checked')) {
					$(this).prop("checked",'true');
				}else {
					$(this).removeAttr('checked');
				}
				
			});
			
			if($(this).is(':checked')) {
				$("button[name='delete']").attr("disabled",false);
				$("button[name='delete']").css("cursor","pointer");
				$("button[name='delete']").css("opacity","1");
			}else{
				$("button[name='delete']").attr("disabled",true);
				$("button[name='delete']").css("cursor","not-allowed");
				$("button[name='delete']").css("opacity","0.6");
			}
		});
			window.onload = function() { 
			$("button[name='delete']").attr("disabled",true);
			$("button[name='delete']").css("cursor","not-allowed");
			$("button[name='delete']").css("opacity","0.6");
			};

});


function getAlertText(type,imgpath,name,gender1,gender2,des) {
	if(type=="personal") {
		var oLogin=document.createElement("div");
		oLogin.id="messagechange";
		oLogin.innerHTML="<div id='message'>"+
		                     "<div id='close'>&times;</div>"
		                     +"<div class='change_meg'>编辑个人信息</div>"
		                     +	"<form  action='/KeDou/user/updateUserMessage' onkeydown='return keyDown(event)' method='post'>"
		                     +		"<div class='user_name'>"
		                     +			"<div class='user_name1'>昵称：</div>"
		                     +          "<div class='user_name3'><input type='text' name='username' class='user_name2' maxlength='10' value='"+name+"'/>"
		                     +				"<input type='hidden' name>"
		                     +			"</div>"
		                     +      "</div>"
		                     +		"<div class='user_gender'>"
		                     +			"<div class='user_gender1'>性别：</div>"
		                     +			"<div class='user_gender2'/><input type='radio' checked='"+gender1+"'  name='gender' value='男'/>男</div>"
		                     +          "<div class='user_gender2'/><input type='radio' value='"+gender2+"' name='gender' value='女'/>女</div>"
		                     +      "</div>"
		                     +      "<div class='textarea'>"
		                     +			"<div class='textarea1'>个人描述：</div>"
		                     +			"<div class='textarea2'><textarea name='userDiscription' rows='4' cols='38' class='textarea3'   onchange='this.value=this.value.substring(0, 128)' onkeydown='this.value=this.value.substring(0, 128)' onkeyup='this.value=this.value.substring(0, 128)'>"+des+"</textarea></div>"
		                     +		"</div>"
		                     +      "<div class='button0'>"
		                     +           "<div><input type='submit' id='id' value='确定' class='button1'/></div>"
		                     +         	 "<div id='cancel' class='button2'/>取消</div>"
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
	}else if(type=="changepwd") {
		var oLogin=document.createElement("div");
		oLogin.id="messagechange";
		oLogin.innerHTML="<div class='messageChangeMail'>"+
							"<div id='close'>&times;</div>"
							+"<div class='title-type'>修改密码</div>"
							+"<div class='text-type' id='changepwdtext'>"
							+	"<div class='div-input-type'>"
							+		"<span class='div-span-type'>输入原始密码：</span><input id='oldpwd' class='inputType' type='password' name='useroldpwd' onfocus='textfocus()'>"
							+	"<div class='div-sspan-type'><span style='font-size:12px;color:darkred;margin-left: 100px;' id='pwdmsg'></span></div><!-- 密码信息提示位置 -->"
							+	"</div>"
							+	"<div class='div-input2-type'>"
							+		"<span class='div-span-type'>输入新密码：</span><input id='newpwd1' class='inputType' type='password' name='usernewpwd1' onfocus='textfocus()'>"
							+	"<div class='div-sspan-type'><span style='font-size:12px;color:darkred;margin-left: 100px;' id='pwdnewmsg'></span></div><!-- 密码信息提示位置 -->"
							+	"</div>"
							+	"<div class='div-input2-type'>"
							+		"<span class='div-span-type'>确认新密码：</span><input id='newpwd2' class='inputType' type='password' name='usernewpwd2' onfocus='textfocus()'>"
							+	"<div class='div-sspan-type'><span style='font-size:12px;color:darkred;margin-left: 100px;' id='pwdcheckmsg'></span></div><!-- 密码信息提示位置 -->"
							+	"</div>"
							+"</div>"
							+"<div class='button-type' id='buttonshow'>"
							+"<button class='submit-button' id='submitButton' onclick='changepwd()'></button>"
							+"<div  id='button3' class='submit-button' onclick='P2()'></div>"
							+"<div  class='button1' onclick='P1()'>确定</div>"
							+"<div id='cancel' class='button2'>取消</div>"
							+"</div>"
							+"<div  id='button-checkpwd' class='submit-button' onclick='P3()'></div>"
							+"<div class='messageChangeMailBogo' id='finishchangepwd'>"
							+"<div id='close'>&times;</div>"
							+"<div class='finishText'>密码修改成功</div>"
							+"<div class='submitbogo'><img src='/KeDou/img/sbumitbogo.jpg' width='100%' height='100%'></div>"
						+"</div>"

	}else if(type=="bingMail") {
		var oLogin=document.createElement("div");
		oLogin.id="messagechange";
		oLogin.innerHTML="<div class='messageChangeMail'>"+
							"<div id='close'>&times;</div>"
							+"<div class='title-type'>绑定邮箱</div>"
							+"<div class='text-type-bingmail' id='changemailtext' style='display:block;'>"
							+	"<div class='div-input-type2'>"
							+		"<span class='div-span-type'>输入邮箱账号：</span><input id='newmail' class='inputType' type='text' onfocus='cleantextfocus2()' onblur='checkmailstyle(this)'>"
							+	"</div>"
							+	"<div class='div-wrong-type'>"
							+		"<span style='font-size:15px;color:darkred;' id='checkmailstylemsg'></span></br>"
							+	"</div>"							
							+	"<div class='div-input-code'>"
							+		"<span class='div-span-type'>输入验证码：</span><input type='text' class='inputcode' placeholder='请输入验证码' id='input_size1'onfocus='cleantextfocus3()' onblur='verifyCodeIsTrue(this.value)'/>"
							+			"<!--验证码码输入的位置-->"
							+		"<div id='span_position'>"
							+			"<img onclick='javascript:refreshImg(this);' src='/KeDou/common/validatecade' width='100%' height='100px'>"
							+		"</div><!--验证码图片的位置-->"
							+	"</div>"
							+	"<div class='div-code-type'>"
							+		"<span style='font-size:15px;color:darkred;' id='verifyCodemsg'></span></br>"
							+	"</div>"
							+	"<div class='button-type-bingmail'>"
							+		"<div  class='button1' onclick='tomailsubmit()'>确定</div>"
							+		"<div id='cancel'  class='button2'>取消</div>"
							+	"</div>"							
							+"</div>"
							+"<div class='text-tochengetel' id='tosubmitmail'  style='display:none;'>"
							+"<div class='title2-type'>验证码已发送到您的邮箱</div>"
							+"<div class='telregisteinputchange'>"
							+	"<input type='text' id='telcode' placeholder='请输入验证码' name='TelCheckNum' class='Telinputsize' onblur='checkMailCode(this)'/>"
							+	"<div class='thetime'>"
					        +		"<div id='texttime'  name='textss'>60秒</div>"
					        +	"</div>"
							+	"<div class='telchecknumspanchange' id='seconddiv'>后点击重新发送</div>"
							+	"<div class='telchecknumspanchange2' id='sendangin' onclick='sendmailcode()'>后点击重新发送</div>"
							+"</div>"
							+	"<div class='div-wrong-type2'>"
							+		"<span style='font-size:13px;color:darkred;' id='checkmailstyle'></span></br>"
							+	"</div>"							
							+	"<div class='button-type-tel2'>"
							+	"<div  class='button1' onclick='changeUserMail()'>确定</div>"
							+	"<div id='cancel'  class='button2'>取消</div>"
							+	"</div>"
							+"</div>"
							+"<input id='returnmailcode' type='hidden'>"
							+"<div class='messageChangetel' id='finish'>"
							+	"<div class='finishText'>密码修改成功</div>"
							+	"<div class='submitbogo'><img src='/KeDou/img/sbumitbogo.jpg' width='100%' height='100%'></div>"
							+"</div>"

	}else if(type=="changemail") {
		var oLogin=document.createElement("div");
		oLogin.id="messagechange";
		oLogin.innerHTML="<div class='messageChangeMail'>"+
							"<div id='close'>&times;</div>"
							+"<div class='title-type'>修改邮箱</div>"
							+"<div class='text-type' id='changemailtext' style='display:block;'>"
							+	"<div class='div-input-type'>"
							+		"<span class='div-span-type'>输入旧邮箱：</span><input id='oldmail' class='inputType' type='text' name='useroldpwd' onblur='checkmail()' onfocus='textfocus()'>"
							+	"</div>"
							+	"<div class='div-wrong-type'>"
							+		"<span style='font-size:15px;color:darkred;' id='checkmailnummsg'></span></br>"
							+	"</div>"							
							+	"<div class='div-input-type2'>"
							+		"<span class='div-span-type'>输入新邮箱：</span><input id='newmail' class='inputType' type='text' onfocus='cleantextfocus2()' onblur='checkmailstyle(this)'>"
							+	"</div>"
							+	"<div class='div-wrong-type'>"
							+		"<span style='font-size:15px;color:darkred;' id='checkmailstylemsg'></span></br>"
							+	"</div>"							
							+	"<div class='div-input-code'>"
							+		"<span class='div-span-type'>输入验证码：</span><input type='text' class='inputcode' placeholder='请输入验证码' id='input_size1'onfocus='cleantextfocus3()' onblur='verifyCodeIsTrue(this.value)'/>"
							+			"<!--验证码码输入的位置-->"
							+		"<div id='span_position'>"
							+			"<img onclick='javascript:refreshImg(this);' src='/KeDou/common/validatecade' width='100%' height='100px'>"
							+		"</div><!--验证码图片的位置-->"
							+	"</div>"
							+	"<div class='div-code-type'>"
							+		"<span style='font-size:15px;color:darkred;' id='verifyCodemsg'></span></br>"
							+	"</div>"
							+"<div class='button-type-tel'>"
							+"<button type='submit' class='submit-button' id='submitButton'></button>"
							+		"<div  class='button1' onclick='tomailsubmit()'>确定</div>"
							+		"<div id='cancel' class='button2'>取消</div>"
							+"</div>"
							+"</div>"
							+"<div class='text-tochengetel' id='tosubmitmail'  style='display:none;'>"
							+"<div class='title2-type'>验证码已发送到您的邮箱</div>"
							+"<div class='telregisteinputchange'>"
							+	"<input type='text' id='telcode' placeholder='请输入验证码' name='TelCheckNum' class='Telinputsize' onblur='checkMailCode(this)'/>"
							+	"<div class='thetime'>"
					        +		"<div id='texttime'  name='textss'>60秒</div>"
					        +	"</div>"
							+	"<div class='telchecknumspanchange' id='seconddiv'>后点击重新发送</div>"
							+	"<div class='telchecknumspanchange2' id='sendangin' onclick='sendmailcode()'>后点击重新发送</div>"
							+"</div>"
							+	"<div class='div-wrong-type2'>"
							+		"<span style='font-size:13px;color:darkred;' id='checkmailstyle'></span></br>"
							+	"</div>"							
							+	"<div class='button-type-tel2'>"
							+	"<div  class='button1' onclick='changeUserMail()'>确定</div>"
							+	"<div id='cancel'  class='button2'>取消</div>"
							+	"</div>"
							+"</div>"
							+"<input id='returnmailcode' type='hidden'>"
							+"<div class='messageChangetel' id='finish'>"
							+	"<div class='finishText'>密码修改成功</div>"
							+	"<div class='submitbogo'><img src='/KeDou/img/sbumitbogo.jpg' width='100%' height='100%'></div>"
							+"</div>"							

	}else if(type=="bingTel") {
		var oLogin=document.createElement("div");
		oLogin.id="messagechange";
		oLogin.innerHTML="<div class='messageChangeMail'>"+
							"<div id='close'>&times;</div>"
							+"<div class='title-type'>修改手机号</div>"
							+"<div class='text-type' id='changetel' style='display:block;'>"
							+	"<div class='div-input-type2'>"
							+		"<span class='div-span-type'>输入新手机号：</span><input id='newtel' class='inputType' type='text' onfocus='cleantextfocus2()' onblur='checktelstyle(this)'>"
							+	"</div>"
							+	"<div class='div-wrong-type'>"
							+		"<span style='font-size:15px;color:darkred;' id='checktelstylemsg'></span></br>"
							+	"</div>"							
							+	"<div class='div-input-code'>"
							+		"<span class='div-span-type'>输入验证码：</span><input type='text' class='inputcode' placeholder='请输入验证码' id='input_size1'onfocus='cleantextfocus3()' onblur='verifyCodeIsTrue(this.value)'/>"
							+			"<!--验证码码输入的位置-->"
							+		"<div id='span_position'>"
							+			"<img onclick='javascript:refreshImg(this);' src='/KeDou/common/validatecade' width='100%' height='100px'>"
							+		"</div><!--验证码图片的位置-->"
							+	"</div>"
							+	"<div class='div-code-type'>"
							+		"<span style='font-size:15px;color:darkred;' id='verifyCodemsg'></span></br>"
							+	"</div>"
							+	"<div class='button-type-tel'>"
							+		"<div  class='button1' onclick='tobingtelsubmit()'>确定</div>"
							+		"<div id='cancel' class='button2'>取消</div>"
							+	"</div>"							
							+"</div>"
							+"<div class='text-tochengetel' id='tosubmit' style='display:none;'>"
							+"<div class='title2-type'>验证码已发送到您的手机</div>"
							+"<div class='telregisteinputchange'>"
							+	"<input type='text' id='telcode' placeholder='请输入验证码' name='TelCheckNum' class='Telinputsize' onblur='checkTelCode(this)'/>"
							+	"<div class='thetime'>"
					        +		"<div id='texttime'  name='textss'>60秒</div>"
					        +	"</div>"
							+	"<div class='telchecknumspanchange' id='seconddiv'>后点击重新发送</div>"
							+	"<div class='telchecknumspanchange2' id='sendangin' onclick='sendcode()'>后点击重新发送</div>"
							+"</div>"
							+	"<div class='div-wrong-type2'>"
							+		"<span style='font-size:13px;color:darkred;' id='checktelstyle'></span></br>"
							+	"</div>"							
							+	"<div class='button-type-tel2'>"
							+	"<div  class='button1' onclick='changeUserTel()'>确定</div>"
							+	"<div id='cancel'  class='button2'>取消</div>"
							+	"</div>"
							+"</div>"
							+"<input id='returncode' type='hidden'>"
							+"<div class='messageChangetel' id='finish'>"
							+	"<div class='finishText'>密码修改成功</div>"
							+	"<div class='submitbogo'><img src='/KeDou/img/sbumitbogo.jpg' width='100%' height='100%'></div>"
							+"</div>"
	}else if(type=="changeTel") {
		var oLogin=document.createElement("div");
		oLogin.id="messagechange";
		oLogin.innerHTML="<div class='messageChangeMail'>"+
							"<div id='close'>&times;</div>"
							+"<div class='title-type'>修改手机号</div>"
							+"<div class='text-type' id='changetel' style='display:block;'>"
							+	"<div class='div-input-type'>"
							+		"<span class='div-span-type'>输入原手机号：</span><input id='oldtel' class='inputType' type='text'  onfocus='cleantextfocus1()' onblur='checktel()'>"
							+	"</div>"
							+	"<div class='div-wrong-type'>"
							+		"<span style='font-size:15px;color:darkred;' id='checktelnummsg'></span></br>"
							+	"</div>"
							+	"<div class='div-input-type2'>"
							+		"<span class='div-span-type'>输入新手机号：</span><input id='newtel' class='inputType' type='text' onfocus='cleantextfocus2()' onblur='checktelstyle(this)'>"
							+	"</div>"
							+	"<div class='div-wrong-type'>"
							+		"<span style='font-size:15px;color:darkred;' id='checktelstylemsg'></span></br>"
							+	"</div>"							
							+	"<div class='div-input-code'>"
							+		"<span class='div-span-type'>输入验证码：</span><input type='text' class='inputcode' placeholder='请输入验证码' id='input_size1'onfocus='cleantextfocus3()' onblur='verifyCodeIsTrue(this.value)'/>"
							+			"<!--验证码码输入的位置-->"
							+		"<div id='span_position'>"
							+			"<img onclick='javascript:refreshImg(this);' src='/KeDou/common/validatecade' width='100%' height='100px'>"
							+		"</div><!--验证码图片的位置-->"
							+	"</div>"
							+	"<div class='div-code-type'>"
							+		"<span style='font-size:15px;color:darkred;' id='verifyCodemsg'></span></br>"
							+	"</div>"
							+	"<div class='button-type-tel'>"
							+		"<div  class='button1' onclick='return totelsubmit()'>确定</div>"
							+		"<div id='cancel'  class='button2'>取消</div>"
							+	"</div>"							
							+"</div>"
							+"<div class='text-tochengetel' id='tosubmit' style='display:none;'>"
							+"<div class='title2-type'>验证码已发送到您的手机</div>"
							+"<div class='telregisteinputchange'>"
							+	"<input type='text' id='telcode' placeholder='请输入验证码' name='TelCheckNum' class='Telinputsize' onblur='checkTelCode(this)'/>"
							+	"<div class='thetime'>"
		                    +		"<div id='texttime'  name='textss'>60秒</div>"
		                    +	"</div>"
							+	"<div class='telchecknumspanchange' id='seconddiv'>后点击重新发送</div>"
							+	"<div class='telchecknumspanchange2' id='sendangin' onclick='sendcode()'>后点击重新发送</div>"
							+"</div>"
							+	"<div class='div-wrong-type2'>"
							+		"<span style='font-size:13px;color:darkred;' id='checktelstyle'></span></br>"
							+	"</div>"							
							+	"<div class='button-type-tel2'>"
							+	"<div  class='button1' onclick='changeUserTel()'>确定</div>"
							+	"<div id='cancel'  class='button2'>取消</div>"
							+	"</div>"
							+"</div>"
							+"<input id='returncode' type='hidden'>"
							+"<div class='messageChangetel' id='finish'>"
							+	"<div class='finishText'>密码修改成功</div>"
							+	"<div class='submitbogo'><img src='/KeDou/img/sbumitbogo.jpg' width='100%' height='100%'></div>"
							+"</div>"
	}else if(type=="changepwdFinish") {
		var oLogin=document.createElement("div");
		oLogin.id="messagechange";
		oLogin.innerHTML="<div class='messageChangeMail'>"+
							"<div id='close'>&times;</div>"
							+"<div class='finishText'>密码修改成功</div>"
							+"<div class='submitbogo'><img src='/KeDou/img/sbumitbogo.jpg' width='100%' height='100%'></div>"
						+"</div>"

	}
	
	                  return oLogin;
}
function check(type,imgpath,name,gender,des){  
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
			if(gender!=null) {
				if(gender=="男") {
					var gender1 = true;
					var gender2 = false;
				}
				if(gender=="女") {
					var gender1 =false;
					var gender2 = true;
				}
			}
			var oLogin = getAlertText(type,imgpath,name,gender1,gender2,des);
			document.body.appendChild(oLogin);
			
		//获取login的高度和宽度
		var dHeight=oLogin.offsetHeight;
		var dWidth=oLogin.offsetWidth;
		oLogin.style.left=(sWidth-dWidth)/2+"px";
		oLogin.style.top=(wHeight-dHeight)/2+"px";	
		
		//弹框关闭按钮
		var oClose = document.getElementById("close");
		var oCancel = document.getElementById("cancel");

			oMask.onclick=function (){
				document.body.removeChild(oMask);
				document.body.removeChild(oLogin);
			};
			oClose.onclick=function (){
				document.body.removeChild(oMask);
				document.body.removeChild(oLogin);
			};
			oCancel.onclick=function (){
				document.body.removeChild(oMask);
				document.body.removeChild(oLogin);
			};
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

//密码更改js

v=0;   //变量 保证只有原始密码和新密码都输入值是才进行一下两次新密码输入是否一致
//这里不能一个函数调用两个函数，所以分开调用，模拟按钮点击事件,验证输入的密码格式是否正确
function P1(){
	document.getElementById('button3').click();
	checkmessage('oldpwd','pwd');
}
function P2(){
	checkmessage('newpwd1','pwdnew');
}
//判断输入信息
function checkmessage(a,name) { 
	v=v+1;
	//密码正则 (6-16位数字和字母的组合）
	var regpwd = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$/;
	//获取input中的值
	var inputValue = document.getElementById(a).value;
	//获取name中前三个字母值
	var str = name.substring(0, 3);
	//账号框
	 if(str=="pwd") {
		//切换正则为密码模式
		var re = new RegExp(regpwd);
		if(inputValue==null) {
			v=0;
			document.getElementById(name+'msg').innerHTML = '请输入6-16位数字和字母的组合';
			
		}else if(!re.test(inputValue)) {
			//不满足密码正则表达式
			v=0;
			document.getElementById(name+'msg').innerHTML = '请输入6-16位数字和字母的组合';		
		}else {
			if(v==2){
				v=0;
				checkpwdidentical('newpwd1','newpwd2','pwdcheck');
			}
			document.getElementById(name+'msg').innerHTML = '';
		}
		
	}
			
};
//判断两次密码输入是否一致
function checkpwdidentical(a,b,name){
	var inputValue1 = document.getElementById(a).value;
	var inputValue2 = document.getElementById(b).value;
	if(inputValue1==inputValue2){
		//模拟点击事件运用ajax检验原密码输入是否正确
		document.getElementById('button-checkpwd').click();
		document.getElementById(name+'msg').innerHTML = '';	
	}else{
		document.getElementById(name+'msg').innerHTML = '两次密码输入不一致';
	}

};
//清空提示信息	
function textfocus(name) {
	document.getElementById("pwdmsg").innerHTML = '';
	document.getElementById("pwdnewmsg").innerHTML = '';
	document.getElementById("pwdcheckmsg").innerHTML = '';
};
//模拟点击事件运用ajax检验原密码输入是否正确
function P3(){
	var spwd = document.getElementById('oldpwd').value;
  	document.getElementById("oldpwd").value = md5(spwd);
  	var spwdMd5 = document.getElementById('oldpwd').value;
	  var xmlhttp;
	  if (window.XMLHttpRequest)
	  {
	    xmlhttp=new XMLHttpRequest();
	  }
	  else
	  {
	    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	  }
	  xmlhttp.onreadystatechange=function()
	  {
	    if (xmlhttp.readyState==4 && xmlhttp.status==200)
	    {
	    var soult =xmlhttp.responseText;
	   
	    if(soult=="false"){
	    	document.getElementById("pwdmsg").innerHTML="密码错误";
	    }else{
	    	//md5加密新输入的密码
	    	var newpwd1 = document.getElementById("newpwd1").value;
	    	document.getElementById('newpwd1').value = md5(newpwd1);
	      	var newpwd2 = document.getElementById("newpwd2").value;
	      	document.getElementById('newpwd2').value = md5(newpwd2);
	      	//设置提交按钮是否可以点击
	    	document.getElementById("submitButton").disabled=false;
	    	document.getElementById('submitButton').click();
	    	document.getElementById("pwdmsg").innerHTML="";
	    }
	    }
	  }
	  xmlhttp.open("GET","/KeDou/InformationBinding/checkpwd?pwd="+spwdMd5,true);
	  xmlhttp.send();
	}
//运用ajax更改用户密码
function changepwd(){
	alert("更改完成");
	var pwd = document.getElementById('newpwd1').value;
	  var xmlhttp;
	  if (window.XMLHttpRequest)
	  {
	    xmlhttp=new XMLHttpRequest();
	  }
	  else
	  {
	    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	  }
	  xmlhttp.onreadystatechange=function()
	  {
	    if (xmlhttp.readyState==4 && xmlhttp.status==200)
	    {
	    var soult =xmlhttp.responseText;
	   if(soult==1){
		   document.getElementById('finishchangepwd').style.display = 'block';
			document.getElementById('changepwdtext').style.display = 'none';
			document.getElementById('buttonshow').style.display = 'none';
	   }else{
		 document.getElementById('finishchangepwd').style.display = 'none';
			document.getElementById('changepwdtext').style.display = 'block';
			document.getElementById('buttonshow').style.display = 'block';
	   }

	    }
	  }
	  xmlhttp.open("GET","/KeDou/InformationBinding/changepwd?usernewpwd1="+pwd,true);
	  xmlhttp.send();
	}
//运用ajax检验旧手机号输入是否正确
function checktel(){
	var tel = document.getElementById('oldtel').value;
	  var xmlhttp;
	  if (window.XMLHttpRequest)
	  {
	    xmlhttp=new XMLHttpRequest();
	  }
	  else
	  {
	    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	  }
	  xmlhttp.onreadystatechange=function()
	  {
	    if (xmlhttp.readyState==4 && xmlhttp.status==200)
	    {
	    var soult =xmlhttp.responseText;
	   
	    if(soult=="false"){
	    	document.getElementById("checktelnummsg").innerHTML="手机号输入错误";
	    	istrue2[0]=-1;
	    }else{
	    	document.getElementById("checktelnummsg").innerHTML="";
	    	istrue2[0]=0;
	    }
	    }
	  }
	  xmlhttp.open("GET","/KeDou/InformationBinding/checktel?tel="+tel,true);
	  xmlhttp.send();
	}
//运用ajax检验旧邮箱账号输入是否正确
function checkmail(){
	var mail = document.getElementById('oldmail').value;
	  var xmlhttp;
	  if (window.XMLHttpRequest)
	  {
	    xmlhttp=new XMLHttpRequest();
	  }
	  else
	  {
	    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	  }
	  xmlhttp.onreadystatechange=function()
	  {
	    if (xmlhttp.readyState==4 && xmlhttp.status==200)
	    {
	    var soult =xmlhttp.responseText;
	   
	    if(soult=="false"){
	    	document.getElementById("checkmailnummsg").innerHTML="邮箱账号输入错误";
	    	istrue2[0]=-1;
	    }else{
	    	document.getElementById("checkmailnummsg").innerHTML="";
	    	istrue2[0]=0;
	    }
	    }
	  }
	  xmlhttp.open("GET","/KeDou/InformationBinding/checkmail?mailcount="+mail,true);
	  xmlhttp.send();
	}
//验证手机号输入格式是否正确
		function checktelstyle(a) {
			//手机号正则
			var regtel = /^[1][0-9][0-9]{9}$/; 
				//正则为手机号正则模式
				var re = new RegExp(regtel); 
				   //如果框内数据为空
					if(!a.value) {
						document.getElementById('checktelstylemsg').innerHTML = '手机号不能为空';
						istrue2[1]=-1;
					}else if (!re.test(a.value)) { 
							//不满足手机号正则表达式
							document.getElementById('checktelstylemsg').innerHTML = '请输入正确的手机号格式！';
							istrue2[1]=-1;
						}else{
							//满足正则表达式
							document.getElementById('checktelstylemsg').innerHTML = '';
							istrue2[1]=0;
							//判断账户是否存在
							checkIsExist(a.value,"tel");
						}
			};
//验证邮箱输入格式是否正确
			function checkmailstyle(a) {
				//邮箱正则
				var regemail = /^[0-9a-zA-Z_]{5,12}@(163|126|qq|yahoo|gmail|sina)\.(com|com\.cn|cn|la)$/; 
					var re = new RegExp(regemail); 
					   //如果框内数据为空
						if(!a.value) {
							document.getElementById('checkmailstylemsg').innerHTML = '邮箱账号不能为空';
							istrue2[1]=-1;
						}else if (!re.test(a.value)) { 
								//不满足邮箱正则表达式
								document.getElementById('checkmailstylemsg').innerHTML = '请输入正确的邮箱格式！';
								istrue2[1]=-1;
							}else{
								//满足正则表达式
								document.getElementById('checkmailstylemsg').innerHTML = '';
								istrue2[1]=0;
								//判断账户是否存在
								checkIsExist(a.value,"mail");
							}
				};			
	//账户是否存在
			  function checkIsExist(acount,type) {
	          	var xmlhttp;
	     		 if(window.XMLHttpRequest) {
	     			 xmlhttp = new XMLHttpRequest();
	     		 }else {
	     			 xmlhttp = new ActiveXObjec("Microsoft.XMLHTTP");
	     		 }
	     		 xmlhttp.onreadystatechange = function() {
	     			 if(xmlhttp.readyState==4&&xmlhttp.status==200) {
	     				var isExist = xmlhttp.responseText;
	     					if(isExist=="1"){
	     						//账户可以注册   没被使用
	     						document.getElementById('check'+type+'stylemsg').innerHTML = '';
	     						istrue2[2]=0;
	     						
	     					}else if(isExist=="-1") {
	     						//账户不可以注册 已被使用
	     						document.getElementById('check'+type+'stylemsg').innerHTML = '账户已存在';
	     						istrue2[2]=-1;
	     					}
	     				
	     			 }
	     		 }
	     		 xmlhttp.open("GET","/KeDou/user/isexist?acount="+acount,true);
	     		 xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
	     		 xmlhttp.send();
	          }		
		//消除提示信息
				function cleantextfocus1() {
					document.getElementById('checktelnummsg').innerHTML = '';
				};	
				function cleantextfocus2() {
					document.getElementById('checktelstylemsg').innerHTML = '';
				};
				function cleantextfocus3() {
					document.getElementById('verifyCodemsg').innerHTML = '';
				};
		//跳转给邮箱发送验证码的页面，检测
				  function tomailsubmit() {
					  		istrue2[0]=0;
			          		for(var i=0;i<3;i++) {
				          		if(istrue2[i]!=0) {
				          			alert("有项填写错误");
				          		}
			          		}
			          		if(istrue2[0]==0&&istrue2[1]==0&&istrue2[2]==0){
			          			document.getElementById('changemailtext').style.display='none';
			          			document.getElementById('tosubmitmail').style.display = 'block';
			          			sendmailcode();
			          		}
			          }					
		//跳转给更改新手机号发送验证码的页面，检测
			  function totelsubmit() {
		          		for(var i=0;i<3;i++) {
			          		if(istrue2[i]!=0) {
			          			alert("有项填写错误");
			          		}
		          		}
		          		if(istrue2[0]==0&&istrue2[1]==0&&istrue2[2]==0){
		          			document.getElementById('changetel').style.display='none';
		          			document.getElementById('tosubmit').style.display = 'block';
		          			sendcode();
		          		}
		          }
		//跳转给绑定新手机号发送验证码的页面，检测
			  function tobingtelsubmit() {
				  istrue2[0]=0;
		          		for(var i=0;i<3;i++) {
			          		if(istrue2[i]!=0) {
			          			alert("有项填写错误");
			          		}
		          		}
		          		if(istrue2[0]==0&&istrue2[1]==0&&istrue2[2]==0){
		          			document.getElementById('changetel').style.display='none';
		          			document.getElementById('tosubmit').style.display = 'block';
		          			sendcode();
		          		}
		          }	
		//页面倒计时	  
			  function time(){
			    var end=60;//初始化给个值为30
			    for(var i=1;i<=end;i++){
			        window.setTimeout("update2("+i+")",i*1000); //当i=30时执行update方法
			    }	
			  }
			  function update2(number){
				
		            var endsceond=60-number;
		            document.getElementById("texttime").innerHTML=endsceond+"秒";
		            if(endsceond==0){
		            	document.getElementById("seconddiv").style.display='none';
		            	document.getElementById("sendangin").style.display='block';

		            }else{
		            	document.getElementById("sendangin").style.display='none';
		            	document.getElementById("seconddiv").style.display='block';

		            }
		    }
		//调用ajax给手机号发验证码
			  function sendcode(){
				  time();
			  	var tel = document.getElementById('newtel').value;
			  	  var xmlhttp;
			  	  if (window.XMLHttpRequest)
			  	  {
			  	    xmlhttp=new XMLHttpRequest();
			  	  }
			  	  else
			  	  {
			  	    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
			  	  }
			  	  xmlhttp.onreadystatechange=function()
			  	  {
			  	    if (xmlhttp.readyState==4 && xmlhttp.status==200)
			  	    {
			  	    var soult =xmlhttp.responseText;
			  	    document.getElementById("returncode").value=soult;

			  	    }
			  	  }
			  	  xmlhttp.open("GET","/KeDou/InformationBinding/sendtelcode?tel="+tel,true);
			  	  xmlhttp.send();
			  	}
		//调用ajax给邮箱发验证码
			  function sendmailcode(){
				  time();
			  	var mailcount = document.getElementById('newmail').value;
			  	  var xmlhttp;
			  	  if (window.XMLHttpRequest)
			  	  {
			  	    xmlhttp=new XMLHttpRequest();
			  	  }
			  	  else
			  	  {
			  	    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
			  	  }
			  	  xmlhttp.onreadystatechange=function()
			  	  {
			  	    if (xmlhttp.readyState==4 && xmlhttp.status==200)
			  	    {
			  	    var soult =xmlhttp.responseText;
			  	    document.getElementById("returnmailcode").value=soult;

			  	    }
			  	  }
			  	  xmlhttp.open("GET","/KeDou/InformationBinding/sendmailcode?mailcount="+mailcount,true);
			  	  xmlhttp.send();
			  	}
	//验证邮箱验证码输入是否正确
			  function checkMailCode(a){
				  var str1=document.getElementById("returnmailcode").value;
				  if(a.value==str1){
					  document.getElementById("checkmailstyle").innerHTML="";
				  }else{
					  document.getElementById("checkmailstyle").innerHTML="验证码输入错误";
				  }
			  }			  
	//验证手机验证码输入是否正确
			  function checkTelCode(a){
				  var str1=document.getElementById("returncode").value;
				  if(a.value==str1){
					  document.getElementById("checktelstyle").innerHTML="";
				  }else{
					  document.getElementById("checktelstyle").innerHTML="验证码输入错误";
				  }
			  }
	//运用ajax更改用户手机号
			  function changeUserTel(){
			  	var tel = document.getElementById('newtel').value;
			  	  var xmlhttp;
			  	  if (window.XMLHttpRequest)
			  	  {
			  	    xmlhttp=new XMLHttpRequest();
			  	  }
			  	  else
			  	  {
			  	    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
			  	  }
			  	  xmlhttp.onreadystatechange=function()
			  	  {
			  	    if (xmlhttp.readyState==4 && xmlhttp.status==200)
			  	    {
			  	    var soult =xmlhttp.responseText;
			  	   if(soult==1){
			  		   document.getElementById('finish').style.display = 'block';
	          			document.getElementById('changetel').style.display = 'none';
	          			document.getElementById('tosubmit').style.display = 'none';
	          			document.getElementById('usermessage5').click();
			  	   }else{
			  		 document.getElementById('finish').style.display = 'none';
	          			document.getElementById('changetel').style.display = 'none';
	          			document.getElementById('tosubmit').style.display = 'block';
			  	   }

			  	    }
			  	  }
			  	  xmlhttp.open("GET","/KeDou/InformationBinding/changetel?tel="+tel,true);
			  	  xmlhttp.send();
			  	}	
	//运用ajax绑定邮箱
			  function changeUserMail(){
			  	var mailcount = document.getElementById('newmail').value;
			  	  var xmlhttp;
			  	  if (window.XMLHttpRequest)
			  	  {
			  	    xmlhttp=new XMLHttpRequest();
			  	  }
			  	  else
			  	  {
			  	    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
			  	  }
			  	  xmlhttp.onreadystatechange=function()
			  	  {
			  	    if (xmlhttp.readyState==4 && xmlhttp.status==200)
			  	    {
			  	    var soult =xmlhttp.responseText;
			  	   if(soult==1){
			  		   document.getElementById('finish').style.display = 'block';
	          			document.getElementById('changemailtext').style.display = 'none';
	          			document.getElementById('tosubmitmail').style.display = 'none';
	          			document.getElementById('usermessage5').click();
			  	   }else{
			  		 document.getElementById('finish').style.display = 'none';
	          			document.getElementById('changetelmailtext').style.display = 'none';
	          			document.getElementById('tosubmitmail').style.display = 'block';
	          			
			  	   }

			  	    }
			  	  }
			  	  xmlhttp.open("GET","/KeDou/InformationBinding/bingmail?mailcount="+mailcount,true);
			  	  xmlhttp.send();
			  	}			  