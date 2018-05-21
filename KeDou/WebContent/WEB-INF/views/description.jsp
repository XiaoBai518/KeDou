<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ page import="java.util.*"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <title>个人描述</title>
    <link rel="stylesheet" type="text/css" href="${ctx }/css/descript.css">
    <script src="${ctx }/js/jquery-1.11.1.min.js"></script>
     <%
     	List<Integer> loc = (List<Integer>)session.getAttribute("localList"); 
      	int []local = new int[4];
     	for(int i=0;i<4;i++){
     		local[i]=loc.get(i);
         	System.out.println(local[i]);
     	}
     	List<String> contents =(List<String>)session.getAttribute("disarr");
     	String []content = new String [4];
     	for(int j=0;j<4;j++){
     		content[j]=contents.get(j);
     	}
      %>
    <script>
	    $(document).ready(function getLocate(){ 
	    	//位置
	    	var arr= new Array();
	        arr[0] = "<%=local[0]%>"
	        arr[1] = "<%=local[1]%>"
	        arr[2] = "<%=local[2]%>"
	        arr[3] = "<%=local[3]%>"
	        //内容
	       	var str =  new Array();
	       	str[0]= "<%=content[0]%>"
	       	str[1]= "<%=content[1]%>"
	       	str[2]= "<%=content[2]%>"
	       	str[3]= "<%=content[3]%>"
       		var m = 0;
	       	for(var i=0;i<arr.length;i++){
	       		var a=arr[i].toString();
	       		var element = document.getElementById(a).getElementsByTagName("p")[0]; 
	       		if(element.innerHTML==""){
	       			element.innerHTML = str[m];
	       			m+=1;
	       		}
	       	}	       	        	
	      });
	    function aClick(item){
	 	   var parent=item.parentNode; 
	 	   var a = parent.getAttribute("id");
	 	   var element = document.getElementById(a).getElementsByTagName("p")[0].innerHTML; 
	 	   var text = document.getElementById("word").value;
	 	   if(text==""){
	 		  document.getElementById("word").value = element;
	 	   }
	 	   if(text!=""){
	 		  document.getElementById("word").value = document.getElementById("word").value+'，'+element;
	 	   }	 	    	   
	 	}
	    //保存个人描述
	   function subDis() {	 
	    	var keyword = $("#word").val();
	    	var params={};
	    	params.keyword=keyword;
	    	if (keyword == "") {
	    		alert("不能为空哦");
	    	}
	    	$.ajax({
	    		url : "http://localhost:8080/KeDou/user/description",
	    		type : "POST",
	    		data :params,
	    		dataType:'json',
	    		success : function(data) {
	    			if (data == "yes") {
	    				window.location.href ="http://localhost:8080/KeDou/user/showuserlabel";
	    			} else if (data == "no") {
	    				alert("错了！");
	    			}
	    		},error: function(data){
	    			alert("no");
	    		}
	    	});
	    }
	    
    </script>
   
</head>
<body>
    <!-- 设置背景图片 -->
    <div id="logoimg"><img src="${ctx }/img/logo1.png"></div>
    <!-- 用户输入框 -->
    <div id="keytext" style="position:relative;z-index:999">
          <input type="text" id="word" placeholder="用一句话描述自己">
          <input id="bsubmit" type="button"  name="finsh" value="完成" onClick="subDis();">
    </div>
    <!-- 提示性标签 -->
    <div class="grid">
        <div class="grid-tr">
           <div class="grid-td1" id="1"> <a href="javascript:void(0);" onclick="aClick(this)"><p></p></a></div>
        </div>
        <div class="grid-tr">
           <div class="grid-td2" id="2"> <a href="javascript:void(0);" onclick="aClick(this)"><p></p></a></div>
           <div class="grid-td2" id="3"> <a href="javascript:void(0);" onclick="aClick(this)"><p></p></a></div>
        </div>
        <div class="grid-tr">
           <div class="grid-td3" id="4"> <a href="javascript:void(0);" onclick="aClick(this)"><p></p></a></div>
           <div class="grid-td3" id="5"> <a href="javascript:void(0);" onclick="aClick(this)"><p></p></a></div>
           <div class="grid-td3" id="6"> <a href="javascript:void(0);" onclick="aClick(this)"><p></p></a></div>
        </div>
        <div class="grid-tr">
           <div class="grid-td4" id="7"> <a href="javascript:void(0);" onclick="aClick(this)"><p></p></a></div>
           <div class="grid-td3" id="8"> <a href="javascript:void(0);" onclick="aClick(this)"><p></p></a></div>
           <div class="grid-td4" id="9"> <a href="javascript:void(0);" onclick="aClick(this)"><p></p></a></div>
           <div class="grid-td3" id="10"> <a href="javascript:void(0);" onclick="aClick(this)"><p></p></a></div>
        </div>
    </div>
</body>
</html>