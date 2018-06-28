<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
    <c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
	<title>minimalist</title>
	<meta charset="utf-8">
	<link rel="stylesheet" href="${ctx }/css/zui.min.css">
   <link rel="stylesheet" type="text/css" href="${ctx }/css/index.css">
         <link rel="stylesheet" type="text/css" href="${ctx }/css/autocomplete.css">
   <script src="${ctx }/js/jquery-2.1.1.min.js"></script>
   <script src="${ctx }/js/zui.min.js"></script>
         <script src="${ctx }/js/jquery.autocomplete.min.js"></script>
        <script src="${ctx }/js/autocomplete.js"></script>
      <script type="text/javascript" src="${ctx }/js/search.js"></script>
</head>
<body>
	<div id="est_cn">
		<a href="${ctx }/user/switchMode?mode=minimalist"><div id="minimalist" class="est_selected">极&nbsp&nbsp简</div></a>
		<a href="${ctx }/user/switchMode?mode=optional"><div id="optional" class="est_unselected">自&nbsp&nbsp选</div></a>
	</div>
	<div style="width:800px;height:80px;text-align: center;">
	 <form class="bs-example bs-example-form" action="${ctx }/minimalist/tochoose" method="get" role="form"  target="_top" onsubmit="return  onSubmitSearch() ">
            <div class="row">
               <div class="col-lg-10">
                  <div class="input-group">
					  <div  class="input-control search-box search-box-circle has-icon-left has-icon-right search-example" id="searchboxExample" style="float: left;">
					    <input id="index-find" type="search" name="search" class="form-control search-input"  placeholder="${defaultContent}">
					    <label for="inputSearchExample3" class="input-control-icon-left search-icon"></label>
					  </div>
					  <div class="input-group-btn" style="float: left;">
					    <button id="index-button" class="btn btn-primary" type="submit">搜索</button>
					  </div>
					</div>
               </div><!-- /.col-lg-6 -->
            </div><!-- /.row -->
         </form>
      </div>   
</body>
</html>