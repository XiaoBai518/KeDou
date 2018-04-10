<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath }"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>验证码</title>
    <script type="text/javascript">
    function refresh(obj) {
        obj.src = "imageServlet?"+Math.random();
    }
    </script>
  </head>
  <body>
    <form action="${ctx }/business/registry" method="post">
    	账号：<input type="text" name="busAccount"/><br/>
    	密码：<input type="text" name="busPwd"/><br/>
        <label>输入验证码</label>
        <input type="text" name="randomCode"/><img title="点击更换" onclick="javascript:refresh(this);" src="${ctx }/imageServlet"><br/>
        <input type="submit" value="下一步"/>
    </form>
  </body>
</html>