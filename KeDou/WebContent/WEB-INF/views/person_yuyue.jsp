<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
         <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
    <c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
		<meta charset="UTF-8">
		<title></title>
		<script src="${ctx }/js/jquery.zui.js"></script>
		<link rel="stylesheet" type="text/css" href="${ctx}/css/personal.css"/>
		<link rel="stylesheet" href="${ctx}/css/zui.min.css"/>
		<script type="text/javascript" src="${ctx}/js/zui.min.js"></script>
</head>
<body>
		<table cellspacing="4" cellpadding="0" class="table">
			<form action="${ctx}/user/bigdelete?page=${page }&checknum=3" method="post">		
			<tr>
				<th class="tr-th3" style="display:table-cell; vertical-align:middle">
				<input type="checkbox" style="margin-right: 20px;zoom:80%;"id="allCheckBox"  value="全选" onclick="selectAll()"/>
				</th>			
				<th class="tr-th1" style="vertical-align:middle;">课程名</th>
				<th class="tr-th1" style="vertical-align:middle;">课程机构</th>
				<th class="tr-th1" style="vertical-align:middle;">价格</th>
				<th class="tr-th1" style="vertical-align:middle;">时间</th>
				<th class="tr-th2" style="vertical-align:middle;">操作</th>
			</tr>
			<c:forEach items="${yuyue }" var="p">
			<tr class="test" id="${p.courseId }">
					<th class="tr-td1" style="display:table-cell; vertical-align:middle"><input type="checkbox" name="tempString"  style="margin-right: 10px;margin-left: 18px;" value="${p.courseId }" onclick="selectSingle()"/></th>
					<td class="tr-td" onclick="window.open('https://www.baidu.com');" style="display:table-cell; vertical-align:middle">${p.courseId }</td>
					<td class="tr-td"><img src="${ctx }/img/1.jpg" width="100%" height="100%"></td>
					<td class="tr-td" style="display:table-cell; vertical-align:middle">考研考研</td>
					<td class="tr-td" style="display:table-cell; vertical-align:middle">${p.orderCreateTime }</td>
					<th  class="tr-td1" id="${p.courseId }" style="display:table-cell; vertical-align:middle"><a href="${ctx}/user/delete?courseid=${p.courseId}&page=${page }&checknum=1"><div class="delete"><i class="icon icon-trash icon-1x"></i></div></a></th>
					
			</tr>
			</c:forEach>
				<div class="tbutton">
					<button type="submit"  name="delete" class="button button22"  disabled="disabled" value="删除" >删除</button>
				</div>
			</form>			
		</table>
		<div class="page">
			<ul id="myPager" class="pager" data-ride="pager" data-page="${page}" data-rec-Total="${pagecount }" data-rec-Per-Page="3" data-link-creator="${ctx }/user/page?page={page}&totalCount={recTotal}&checknum=3" ></ul>
		</div>		
		<script>
		ischeck=0;
		//全选按钮2
		function selectA(){
			var oInput=document.getElementsByName("tempString");
		for(var i=0;i<oInput.length;i++){
			oInput[i].checked=document.getElementById("allCheck").checked;
		}
		}
		//全选按钮1
		function selectAll(){
			var oInput=document.getElementsByName("tempString");
		for(var i=0;i<oInput.length;i++){
			oInput[i].checked=document.getElementById("allCheckBox").checked;
		}
		}
		//根据复选框是否被选择的状态去顶是否全选
		function selectSingle(){
			var k=0;
			var oInput=document.getElementsByName("tempString");
			for(var i=0;i<oInput.length;i++){
				if(oInput[i].checked==false){
					k=1;
					break;
				}
			}
			if(k==0){
				document.getElementById("allCheckBox").checked=true;
			}
			else{
				document.getElementById("allCheckBox").checked=false;
			}
		}
		</script>	
		<script>
		$("input[name='tempString']").on('click', function(){
			if($(this).is(':checked')) {
				ischeck+=1;
			}else{
				ischeck-=1;
			}
			if(ischeck>0){
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
			if($(this).is(':checked')) {
				ischeck+=1;
			}else{
				ischeck-=1;
			}
			if(ischeck>0){
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
		</script>
</body>
</html>