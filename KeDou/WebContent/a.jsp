<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
        <c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>page1</title>
    <script src="${ctx }/js/jquery.zui.js"></script>
    <script src="${ctx }/js/jquery.cookie.js"></script>
    <script src="${ctx }/js/tongji.js"></script>
    <script type="text/javascript">
        function test(){
            alert(localStorage.getItem('jsArr'));
        }
    </script>
</head>
<body>
<a href="javascript:;" onclick="test()">测试</a>
<p style="font-size: 20px;">
    <a style="color:red;" href="http://weber.pub/js记录用户行为浏览记录和停留时间/163.html" target="_blank">返回文章</a>
    <a style="color:red;" href="http://weber.pub" target="_blank">返回网站首页</a>
</p>

<a href="a.jsp">page1</a>
<div id="userid">weixinghua</div>
<p id="tj" style="border: 2px solid fuchsia;">

</p>

<script>
    $('#tj').html(localStorage.getItem('jsArr'));
    $(window).bind('beforeunload', function() {
	    return '哈哈哈';
	});
</script>
</body>
</html>