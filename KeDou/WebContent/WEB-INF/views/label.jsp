<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ page import="java.util.*"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>标签页</title>
	
	<link rel="stylesheet" href="${ctx }/css/label-style.css" media="screen" type="text/css" />
	<link rel="stylesheet" type="text/css" href="${ctx }/css/label-normalize.css" />
	<link rel="stylesheet" type="text/css" href="${ctx }/css/label-demo.css">
	<link rel="stylesheet" href="${ctx }/css/label-style1.css">
	
</head>
<body>
	<div class="satic-area">
		<div class="dynamic-area1">
			<div id="clearfix">
			<button class="select">&nbsp;</button>
			<span>选择出适合你的标签</span>
	  		 <button class="send " data-counter="0"  onClick="subLabel();">&#10004;</button>
	        <div style="text-align:center;clear:both">
				<c:forEach items="${labelList}" var="p">
		  			<li id="${p.id}"><img src="${ctx }/img/${p.img }"/></li>		  
				</c:forEach>
			</div>
		</div>
		</div>
		
	</div>
	<script src="${ctx }/js/jquery-1.11.1.min.js"></script>
	<script src="${ctx }/js/label.js"></script>
	<script>
	function subLabel(){
		var list =document.getElementsByTagName("li");
		var string = "";
		 for(var i=0;i<list.length;i++){
			string += list[i].id.toString();
		}
		var params={};
	    	params.label=string;
	    	if (string == "") {
	    		alert("不能为空哦");
	    	}
		$.ajax({
    		url : "${ctx}/user/label",
    		type : "POST",
    		data :params,
    		dataType:'json',
    		success : function(data) {
    			if (data == "yes") {
    				window.location.href ="${ctx}/common/toindex";
    			} else if (data == "no") {
    			}
    		},error: function(data){
    		}
    	});
	}
	</script>	
</body>

</html>