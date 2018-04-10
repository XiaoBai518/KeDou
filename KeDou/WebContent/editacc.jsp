<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="${ctx }/announcement/edit" method="post">
			公告Id:
			<input type="text" name="accId" value="${acc.accId }" class="text-word"/><br/>		
			公告标题:
			<input type="text" name="accTitle" value="${acc.accTitle }" class="text-word"/><br/>
			公告内容:
			<input type="text" name="accContent" value="${acc.accContent }" class="text-word"/><br/>
			浏览次数:
			<input type="text" name="accRead" value="${acc.accRead }" class="text-word"/><br/>
			发布人:
			<input type="text" name="busId" value="${acc.busId }" class="text-word"/><br/>
			发布时间:
			<input type="text" name="accPublish" value="${acc.accPublish }" class="text-word"/><br/>
			<input type="submit" value="保存"/>
	</form>
</body>
</html>