<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>课兜儿网——极简模式</title>
		<link rel="icon" href="${ctx }/img/favicon.ico" type="image/x-icon" />
   		<link rel="shortcut icon" href="${ctx }/img/favicon.ico">
   		<link rel="Bookmark" href="${ctx }/img/favicon.ico">
   		<link rel="SHORTCUT ICON" href="${ctx }/img/favicon.ico"/>
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
	<body style="height:100%;overflow-y: hidden">	
		<div class="page1" id="page1">
			<div class="question" id="question1"><h1>${question.question}</h1></div>
			<div class="left_div" id="div1_fist_div" name="${question.id}" ><p class="p_center">${question.option1}</p></div>
			<c:if test="${not empty question.option3}">
			<div class="center_div" id="div1_center1_div" name="${question.id}"><p class="p_center">${question.option2}</p></div>
			<div class="right_div" id="div1_second_div" name="${question.id}" onclick="createInput()"><p class="p_center">${question.option3}</p></div>
			</c:if>
			<c:if test="${empty  question.option3}">
			<div class="1" id="div1_center1_div"><p class="p_center"></p></div>
			<div class="right_div" id="div1_second_div" name="${question.id}" onclick="createInput()"><p class="p_center">${question.option2}</p></div>
			</c:if>
			
		</div>
		<div class="page2" id="page2" >
			<div class="question" id="question2"><h1>选择您喜欢的</h1></div>
			<div class="left_div" id="div2_third_div"><p class="p_center"></p></div>
			<div class="1" id="div2_center2_div"><p class="p_center"></p></div>
			<div class="right_div" id="div2_fourth_div"><p class="p_center"></p></div>
		</div>
	</body>
	<script>
			$("div[id^='div1_']").each(function(){
				$(this).click(function(){
					var id=$(this).attr("name");
				    $.ajax({ 
				        type : "GET", //提交方式 
				        url : "${ctx}/minimalist/choosenext?id="+id,//路径 
				        dataType:"json",
				        success : function(data) {//返回数据根据结果进行相应的处理 
				        if(data==null){
				        	window.location.href = "${ctx}/course/searchFirstCourseList?search=${search}";
				        }else{
				        	  $("#question2").html('<h1>'+data.question+'</h1>');
					        if(typeof(data.option3) != "undefined"&&data.option3!=null&&data.option3!=''){
					        	$("#div2_third_div").html('<p class="p_center">'+data.option1+'</p>');
					        	$("#div2_third_div").attr("name",data.id);
						        $("#div2_fourth_div").html('<p class="p_center">'+data.option3+'</p>');	
						        $("#div2_fourth_div").attr("name",data.id);
						        $("#div2_center2_div").attr("class","center_div");
						        $("#div2_center2_div").attr("name",data.id);
						        $("#div2_center2_div").html('<p class="p_center">'+data.option2+'</p>');
					        }else {
					        	   $("#div2_third_div").html('<p class="p_center">'+data.option1+'</p>');
					        	   $("#div2_third_div").attr("name",data.id);
							        $("#div2_fourth_div").html('<p class="p_center">'+data.option2+'</p>');	
							        $("#div2_fourth_div").attr("name",data.id);
					        }
				        }
				      }
				       });
				    });
			})
			$("div[id^='div2_']").each(function(){
				$(this).click(function(){
					var id=$(this).attr("name");
				    $.ajax({ 
				        type : "GET", //提交方式 
				        url : "${ctx}/minimalist/choosenext?id="+id,//路径 
				        dataType:"json",
				        success : function(data) {//返回数据根据结果进行相应的处理 
				        if(data==null){
				        	window.location.href = "${ctx}/course/searchFirstCourseList?search=${search}";
				        }else{
				        	  $("#question1").html('<h1>'+data.question+'</h1>');
				        	  if(typeof(data.option3) != "undefined"&&data.option3!=null&&data.option3!=''){
						        	$("#div1_fist_div").html('<p class="p_center">'+data.option1+'</p>');
						        	$("#div1_fist_div").attr("name",data.id);
							        $("#div1_second_div").html('<p class="p_center">'+data.option3+'</p>');	
							        $("#div1_second_div").attr("name",data.id);
							        $("#div1_center1_div").attr("class","center_div");
							        $("#div1_center1_div").attr("name",data.id);
							        $("#div1_center1_div").html('<p class="p_center">'+data.option2+'</p>');
						        }else {
						        	   $("#div1_fist_div").html('<p class="p_center">'+data.option1+'</p>');
						        	   $("#div1_fist_div").attr("name",data.id);
								        $("#div1_second_div").html('<p class="p_center">'+data.option2+'</p>');	
								        $("#div1_second_div").attr("name",data.id);
						        }
				        }
				       } 
				       });
				    });
			})
	</script>
</html>
