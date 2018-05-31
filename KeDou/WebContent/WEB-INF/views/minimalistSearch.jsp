<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="${ctx }/css/choose.css">
		<script type="text/javascript" src="${ctx}/js/jquery-2.2.0.min.js"></script>
		<script type="text/javascript">
	
		   window.onload = function () {
			  
             _page1 = document.getElementById('page1');
             _page2 = document.getElementById('page2');
             _fist_div = document.getElementById('div1_fist_div');
             _second_div = document.getElementById('div1_second_div');
             _third_div = document.getElementById('div2_third_div');
             _fourth_div = document.getElementById('div2_fourth_div');
           	 color = new Array("#00FFFF","#DA70D6","#009ACD","#00EEEE","#FFEC8B")
           	 count = 0;
           	 _page1.style.background = "#00FFFF";
           	 _page2.style.background = "#DA70D6";
           	 
           	 _fist_div.onclick = function () {
           		 
              if(count>4) {
              	count=0
              }
              if(count==4) {
              	 _page1.className="tran"
              _page1.style.background = color[4]

               
                _page2.className="scale"
              _page2.style.background = color[0]
              }else {
              	 _page1.className="tran"
              _page1.style.background = color[count]
              
               
                _page2.className="scale"
              _page2.style.background = color[count+1]
             
              }
              count+=1;

            };

           	 _second_div.onclick = function () {
              if(count>4) {
              	count=0
              }
              if(count==4) {
              	 _page1.className="tran"
              _page1.style.backgroundColor = color[4]

               
                _page2.className="scale"
              _page2.style.backgroundColor = color[0]
              }else {
              	 _page1.className="tran"
              _page1.style.backgroundColor = color[count]
              
               
                _page2.className="scale"
              _page2.style.backgroundColor = color[count+1]
             
              }
              count+=1;

            };

            _third_div.onclick = function () {
              if(count>4) {
              	count=0
              }
              if(count==4) {
              	 _page2.className="tran"
              _page2.style.backgroundColor = color[4]

               
                _page1.className="scale"
              _page1.style.backgroundColor = color[0]
              }else {
              	 _page2.className="tran"
              _page2.style.backgroundColor = color[count]
              
               
                _page1.className="scale"
              _page1.style.backgroundColor = color[count+1]
             
              }
              count+=1;

            };

            _fourth_div.onclick = function () {
            	if(count>4) {
              	count=0
              }
              if(count==4){
              	_page2.className="tran";
 			  _page2.style.backgroundColor = color[4]

            	
            	_page1.className="scale";
              _page1.style.backgroundColor = color[0]
              }else {
              	_page2.className="tran";
 			  _page2.style.backgroundColor = color[count]

            	
            	_page1.className="scale";
              _page1.style.backgroundColor = color[count+1]
              }
 				count+=1;            	
            };
           }

		</script>
	</head>
	<body style="height:100%;">	
		<div class="page1" id="page1">
			<div class="question" id="question1"><h1>选择您喜欢的</h1></div>
			<div class="left_div" id="div1_fist_div" ><p class="p_center">极简</p></div>
			<div class="1" id="div1_center1_div"><p class="p_center"></p></div>
			<div class="right_div" id="div1_second_div" onclick="createInput()"><p class="p_center">常规</p></div>
		</div>
		<div class="page2" id="page2" >
			<div class="question" id="question2 "><h1>选择您喜欢的</h1></div>
			<div class="left_div" id="div2_third_div"><p class="p_center"></p></div>
			<div class="1" id="div2_center2_div"><p class="p_center"></p></div>
			<div class="right_div" id="div2_fourth_div"><p class="p_center"></p></div>
		</div>
	</body>
	<script>
			$("div[id^='div1_']").each(function(){
				$(this).click(function(){
					var s=$(this).text();
				    $.ajax({ 
				        type : "GET", //提交方式 
				        url : "${ctx}/minimalist/choosetext?name="+s,//路径 
				        dataType:"json",
				        success : function(data) {//返回数据根据结果进行相应的处理 
				        if(data==null){
				        	window.location.href = "${ctx}/course/searchFirstCourseList?search=${search}";
				        }else{
					        $("#div2_third_div").html('<p class="p_center">'+data[0].answer+'</p>');
					        $("#div2_fourth_div").html('<p class="p_center">'+data[1].answer+'</p>');	
					        if(data.length>2){	
						        $("#div2_center2_div").attr("class","center_div");
						        $("#div2_center2_div").html('<p class="p_center">'+data[2].answer+'</p>');
					        }
				        }
				        } ,
			            error:function() {  
			            	
			            }
				       });
				    });
			})
			$("div[id^='div2_']").each(function(){
				$(this).click(function(){
					var s=$(this).text();
				    $.ajax({ 
				        type : "GET", //提交方式 
				        url : "${ctx}/minimalist/choosetext?name="+s,//路径 
				        dataType:"json",
				        success : function(data) {//返回数据根据结果进行相应的处理 
				        if(data==null){
				        	window.location.href = "${ctx}/course/searchFirstCourseList?search=${search}";
				        }else{
					        $("#div1_fist_div").html('<p class="p_center">'+data[0].answer+'</p>');
					        $("#div1_second_div").html('<p class="p_center">'+data[1].answer+'</p>');
					        if(data.length>2){					        	
						        $("#div1_center1_div").attr("class","center_div");
						        $("#div1_center1_div").html('<p class="p_center">'+data[2].answer+'</p>');
					        }
				        }
				        } ,
			            error:function() {  
			            	 
			            }
				       });
				    });
			})
	</script>
</html>
