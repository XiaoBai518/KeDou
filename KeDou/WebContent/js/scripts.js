/*
 *判断用户输入数据是否满足规则
 **/
function istrue(name,value) {
    //邮箱正则
    var regemail = /^[0-9a-zA-Z_]{5,12}@(163|126|qq|yahoo|gmail|sina)\.(com|com\.cn|cn|la)$/;
    //手机号正则
    var regtel = /^[1][0-9][0-9]{9}$/; 
    //密码正则 (6-16位数字和字母的组合）
    var regpwd = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$/;
    //账号正则 5-20位 任选数字、字母、下划线 组合
    var regacount = /^[a-zA-Z]{1}[\w\.]{4,19}$/;
    if(name=="busEmail") {
    //正则为邮箱正则模式
        var re = new RegExp(regemail); 
          if (re.test(value)) {
            return [true,"邮箱"];
          }else {
            return [false,"邮箱"];
          }
    }
    if(name=="busTel") {
    //正则为手机号正则模式
        var re = new RegExp(regtel); 
          if (re.test(value)) {
            return [true,"手机号"];
          }else {
            return [false,"手机号"];
          }
    }
    if(name=="busPwd") {
    //正则为邮箱正则模式
        var re = new RegExp(regpwd); 
          if (re.test(value)) {
            return [true,"密码"];
          }else {
            return [false,"密码"];
          }
    }
    if(name=="busAccount") {
    //正则为邮箱正则模式
        var re = new RegExp(regacount); 
          if (re.test(value)) {
            return [true,"账号"];
          }else {
            return [false,"账号"];
          }
    }
        
    return null;
};
/*
 * 警告信息
 * 
 **/
    function addWarn(warn,value) {
      var wtext = warn.children("strong").text();

        //判断提示信息是否为空
        if(wtext=="") {

                  warn.children("strong").text(value);        
                  warn.show();
        }else {
            //判断提示信息是否已经存在

            if(wtext.indexOf(value)>=0) {
                //信息重复
            }else {
                //信息不重复
                    //获取已经存在的消息
                    var preText = warn.children("strong").text();
                    warn.children("strong").text(preText+" "+value);       
                    warn.show();
            }
        }
            
    }
  
	
	  //账号是否可用 
	  acountIsTrue = true;
	 //是否前往下一页
	  next_step = true;
	 //
	  //是否提交
	  ifsubmit = new Array(false,false,false);
jQuery(document).ready(function() {
    /*
        Fullscreen background
    */
    $.backstretch("assets/img/backgrounds/1.jpg");
    
    $('#top-navbar-1').on('shown.bs.collapse', function(){
    	$.backstretch("resize");
    });
    $('#top-navbar-1').on('hidden.bs.collapse', function(){
    	$.backstretch("resize");
    });
    
    /*
        Form
    */
    $('.registration-form fieldset:first-child').fadeIn('slow');
    
    $('.registration-form input[type="text"], .registration-form input[type="password"], .registration-form textarea').on('focus', function() {
    	$(this).removeClass('input-error');
    });
    
   
    // next step
    $('.registration-form .btn-next').on('click', function() {
    	var parent_fieldset = $(this).parents('fieldset');
    	next_step = true;
        //清楚警告信息
    	 var warn = parent_fieldset.find("#alertwarning");    
            if(!acountIsTrue) {
            	warn.children("strong").text("账号已经被使用!");
            	$('#busAcount').addClass('input-error');
            	next_step = false;
            }else {
            	warn.hide();
            }
    	parent_fieldset.find('input[type="text"], input[type="password"], textarea').each(function() {
            //用户未输入数据
    		if( $(this).val() == "" ) {
               var warn = $(this).parent().siblings().filter("#alertwarning");
                 addWarn(warn,"您有项未填写！")
    			$(this).addClass('input-error');
    			next_step = false;
    			ifsubmit = false;
    		}else {
                //对输入的数据进行规则验证·
                  var results =  istrue($(this).attr("id"),$(this).val());
                   //判断数据是否需要规则判断
                if(results!=null) {
                    var tf = results[0];
                    var text = results[1];
      
                    if(!tf){
                     var warn = $(this).parent().siblings().filter("#alertwarning");
                     addWarn(warn,text+"格式错误！")
                         $(this).addClass('input-error');
                            next_step = false;
                    }
                }else{
                    $(this).removeClass('input-error');
                }
                
            }
    	});
    	
    	if( next_step ) {
    		parent_fieldset.fadeOut(400, function() {
	    		$(this).next().fadeIn();
	    	});
    	}
    	
    });
    
    // previous step
    $('.registration-form .btn-previous').on('click', function() {
    	$(this).parents('fieldset').fadeOut(400, function() {
    		$(this).prev().fadeIn();
    	});
    });
    
    
    // submit
    	
    $('.registration-form').on('submit', function(e) {
    	
    	if($('#veCode').length > 0) {
    	  //验证码元素存在时执行的代码
    		  //清楚警告信息
    		$('#alertwarning').children("strong").text("");        
    		$('#alertwarning').hide();
    		var index = 0;
    		$(this).find('input[type="text"], input[type="password"], textarea').each(function() {
  	      		if( $(this).val() == "" ) {
  
  	      			$(this).addClass('input-error');
  	      			addWarn($('#alertwarning'),"您有项未填写!");
  	      			ifsubmit[index] = false;
  	      		index++;
  	      		}else {
  	      			if(index!=2) {
  	      				ifsubmit[index] = true;
  	      				index++;
  	      			}else {
  	      				if(!ifsubmit[2]) {
  	      					addWarn($('#alertwarning'),"验证码错误");
  	      					$("#veCode").addClass('input-error');
  	      				}
  	      			    index++;
  	      			}
  	      			
  	      		}
  	      	});
    		//判断是否提交
    		for (var i = 0; i < 3; i++) {
				if(!ifsubmit[i]) {
					 e.preventDefault();
					 return;
				}
				if(i==2) {
					var spwd = $('#busPwd').val();
					  $('#busPwd').val(md5(spwd));
				}
			}
    		
    	}else {
    	   	$(this).find('input[type="text"], input[type="password"], textarea').each(function() {
        		if( $(this).val() == "" ) {
        			
        			$(this).addClass('input-error');
        			addWarn($('#alertwarning'),"您有项未填写!");
        			e.preventDefault();
        		}
        		else {
        		   
        			$(this).removeClass('input-error');
        		}
        		
        	});
    		var spwd = $('#busPwd').val();
			  $('#busPwd').val(md5(spwd));
    	}  
		  
 
    	
    });
    
    
});
