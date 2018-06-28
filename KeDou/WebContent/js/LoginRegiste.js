
	istrue = new Array(-1,-1,-1,-1);
	//判断输入信息
		function textblur(a,name) {
			//邮箱正则
			var regemail = /^[0-9a-zA-Z_]{5,12}@(163|126|qq|yahoo|gmail|sina)\.(com|com\.cn|cn|la)$/;
			//手机号正则
			var regtel = /^[1][0-9][0-9]{9}$/; 
			//密码正则 (6-16位数字和字母的组合）
			var regpwd = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$/;
			//账号框
			if(name=="email") {
				//正则为手机号正则模式
				var re = new RegExp(regtel); 
				   //如果框内数据为空
					if(!a.value) {
						document.getElementById(name+'msg').innerHTML = '手机号或邮箱不能为空';
						istrue[0]=-1;
					}else if(a.value.length==11) { //如果框内长度为11 判定为手机号    （此处有BUG 日后修改）
						if (!re.test(a.value)) { 
							//不满足手机号正则表达式
							document.getElementById(name+'msg').innerHTML = '请输入正确的手机号格式！';
							document.getElementById(name).innerHTML = '';	
							istrue[0]=-1;
						}else{
							//满足正则表达式
							document.getElementById(name+'msg').innerHTML = '';
							//判断账户是否存在
							acountIsExist(a.value,"tel");
						}
					}else {
						//切换正则为邮箱正则模式
						var re = new RegExp(regemail); 
						if(!re.test(a.value)){
							//不满足正则表达式
								document.getElementById(name+'msg').innerHTML = '邮箱错误！';
								document.getElementById(name).innerHTML = '';
								istrue[0]=-1;
						}else{
							document.getElementById(name+'msg').innerHTML = '';
							//判断账户是否存在
							acountIsExist(a.value,"email");
						}
					}
			}else if(name=="pwd") {
				//切换正则为密码模式
				var re = new RegExp(regpwd);
				if(!a.value) {
					document.getElementById(name+'msg').innerHTML = '密码不能为空';
				}else if(!re.test(a.value)) {
					//不满足密码正则表达式
					document.getElementById(name+'msg').innerHTML = '请输入6-16位数字和字母的组合';
					document.getElementById(name).innerHTML = '';
					istrue[1]=-1;
				}else {
					istrue[1] = 0;
					document.getElementById(name+'msg').innerHTML = '';
					document.getElementById(name).innerHTML = '';
					
				}
				
			}
					
		};
		//是否同意条款
		function check(id) {
			var checkbox = document.getElementById(id);//选中checkbox的id；
			if(checkbox.checked==true){//按钮已选中
				istrue[3] = 0;
			     }else{
				istrue[3] = -1;
			            }

		};		
		//消除提示信息
		function textfocus(name) {
			document.getElementById(name).innerHTML = '';
			document.getElementById(name+'msg').innerHTML = '';
		};
		//设置自动登陆
			function autoLogin() {
				var al = document.getElementById("autologin");
				if(al.value=="true") {
					al.value="false";
				}else if(al.value=="false") {
					al.value="true"
				}
			}
		//登录时判断输入框是否为空
			function textEmpty(obj,name) {
				if(obj.value.replace(/(^\s*)|(\s*$)/g, "")=="") {
				
					if(name=="email") {
						istrue[0]=-1;
						document.getElementById(name+'msg').innerHTML = '账号不能为空';
					}
					if(name=="pwd") {
						istrue[1]=-1;
						document.getElementById(name+'msg').innerHTML = '密码不能为空';
					}
				}else {
					if(name=="email") {
						istrue[0]=0;
					}
					if(name=="pwd") {
						istrue[1]=0;
					}
				}
			        
			}
			//登录前检查
			  function endsubmit() {
		          	for(var i=0;i<3;i++) {
		          		if(istrue[i]!=0) {
		          			alert("有项填写错误");
		          			return false;
		          		}

		          	}
		          	var spwd = document.getElementById("inputpwd").value;
		          	document.getElementById("inputpwd").value = md5(spwd);
		          	return true;
		          }
			  //手机号注册时检测进入输入短信验证码界面
			  function telsubmit() {
				  istrue[1]=0;istrue[3]=0;
		          		for(var i=0;i<4;i++) {
			          		if(istrue[i]!=0) {
			          			alert("有项填写错误");
			          			return false;
			          		}
			          		if(istrue[0]==0&&istrue[2]==0) {
			          			$("#inputregiste input").val("");
			          			return true;
			          		}
		          		}
		          	return true;
		          }
			  //手机号注册时检测进入加入数据库
			  function endtelsubmit() {
				  istrue[0]=0;
	          		for(var i=0;i<4;i++) {
		          		if(istrue[i]!=0) {
		          			alert("有项填写错误");
		          			return false;
		          		}
	          		}
			          	var spwd = document.getElementById("userpwd").value;
			          	document.getElementById("userpwd").value = md5(spwd);
			          	return true;
		          }
			  //输入验证码倒计时，更新显示剩余时间
			    function update(number){
			            var endsceond=end-number;
			            document.getElementById("text_1").innerHTML=endsceond+"秒";
			            if(endsceond==0){
			            	document.getElementById("seconddiv").style.display='none';
			            	document.getElementById("sendangin").style.display='block';

			            }else{
			            	document.getElementById("sendangin").style.display='none';
			            	document.getElementById("seconddiv").style.display='block';

			            }
			    }
			    

			