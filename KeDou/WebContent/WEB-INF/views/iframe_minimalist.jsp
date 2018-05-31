<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
    <c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
	<title>minimalist</title>
	<meta charset="utf-8">
	<link rel="stylesheet" href="${ctx }/css/bootstrap.min.css">
   <link rel="stylesheet" type="text/css" href="${ctx }/css/index.css">
   <script src="${ctx }/js/jquery-2.1.1.min.js"></script>
   <script src="${ctx }/js/bootstrap.min.js"></script>
</head>
<body>
	<div id="est_cn">
		<a href="${ctx }/user/switchMode?mode=minimalist"><div id="minimalist" class="est_selected">极&nbsp&nbsp简</div></a>
		<a href="${ctx }/user/switchMode?mode=optional"><div id="optional" class="est_unselected">自&nbsp&nbsp选</div></a>
	</div>
	 <form class="bs-example bs-example-form" action="${ctx }/minimalist/tochoose" method="get" role="form" id="search" target="_top">
            <div class="row">
               <div class="col-lg-6">
                  <div class="input-group">
                     <input type="text"  name="search"class="form-control">
                     <span class="input-group-btn">
                        <button class="btn btn-default" type="submit">
                           <span class="glyphicon glyphicon-search"></span>
                        </button>
                     </span>
                  </div><!-- /input-group -->
               </div><!-- /.col-lg-6 -->
            </div><!-- /.row -->
         </form>
</body>
</html>