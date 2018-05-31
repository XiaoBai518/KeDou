<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
       <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
    <c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>课兜后台管理系统</title>
<link rel="stylesheet" href="${ctx }/css/zui.min.css">
<script src="${ctx }/js/jquery.zui.js"></script>
<script src="${ctx }/js/zui.min.js"></script>

<link type="text/css" rel="stylesheet" href="${ctx }/css/bgstyle.css" />
<script type="text/javascript" src="${ctx }/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${ctx }/js/menu.js"></script>
<script type="text/javascript" src="${ctx }/js/md5.js"></script>
<script type="text/javascript">
	function validate(){
		var pwd=document.addAdmin.adminPwd.value;
		var pwd2=document.addAdmin.adminPwd2.value;
		if(pwd==pwd2){
			document.getElementById("tishi").innerHTML="<font color='green'>两次密码相同</font>";
		}else{
			document.getElementById("tishi").innerHTML="<font color='red'>两次密码不相同,请修改密码</font>";
		}
	}
	function endsubmit(){
		var pwd=document.addAdmin.adminPwd.value;
		document.addAdmin.adminPwd.value=md5(pwd);
		return true;
	}
</script>
</head>

<body>
<div class="top"></div>
<div id="header">
	<div class="logo">课兜后台管理系统</div>
	<div class="navigation">
		<ul>
		 	<li>欢迎您！</li>
			<li>${sessionScope.loginAdmin.adminAccount }</li>
			<li><a href="${ctx }/bg_common/bggoChangeAdPwd">修改密码</a></li>
			<!--<li><a href="">设置</a></li>  -->
			<li><a href="${ctx}/bg_common/tobglogin">退出</a></li>
		</ul>
	</div>
</div>
<div id="content">
	<div class="left_menu">
				<ul id="nav_dot">
      <li>
          <h4 class="M1"><i class="icon icon-user">&nbsp;&nbsp;</i>用户管理</h4>
          <div class="list-item none">
            <a href='${ctx}/bg_common/bggoAddAdmin'>添加管理员</a>
            <a href='${ctx}/bg_common/bggoEditUser'>修改用户信息</a>
            <a href='${ctx}/bg_common/bggoSearchUser'>查看用户列表</a>
          </div>
        </li>
        <li>
          <h4 class="M2"><i class="icon icon-shopping-cart">&nbsp;&nbsp;</i>机构管理</h4>
          <div class="list-item none">
            <a href="${ctx}/bg_common/bggoEditStore">修改机构</a>
            <a href="${ctx}/bg_common/bggoSearchStore">查看机构列表</a>         
           </div>
        </li>
        <li>
          <h4 class="M3"><i class="icon icon-time">&nbsp;&nbsp;</i>预约管理</h4>
          <div class="list-item none">
            <a href='${ctx}/bg_common/bggoSearchReservation'>预约检索</a>
          </div>
        </li>
        <li>
          <h4 class="M5"><i class="icon icon-book">&nbsp;&nbsp;</i>课程管理</h4>
          <div class="list-item none">
            <a href='${ctx}/bg_common/bggoSearchCourse'>查看课程列表</a>
          </div>
        </li>
    <!-- 
        <li>
          <h4  class="M6"><i class="icon icon-newspaper-o">&nbsp;&nbsp;</i>公告管理</h4>
          <div class="list-item none">
            <a href='${ctx}/bg_common/bggoAddBulltein'>添加公告</a>
            <a href='bg_searchBulletin.html'>公告操作</a>
            <a href='bg_editBulletin.html'>修改公告</a>
          </div>
        </li>     
         -->  				
  </ul>
		</div>
		<div class="m-right">
			<div class="right-nav">
					<ul>
							<li></li>
								<li style="margin-left:25px;">您当前的位置：</li>
								<li><a href="bg_index.html">首页</a></li>
                <li>></li>
                <li><a href="#">添加管理员</a></li>
								
						</ul>
			</div>
			<div class="main">
			<!-- 添加管理员的form -->
			<form name="addAdmin" action="${ctx}/bg_admin/bgaddAdmin" method="post" onsubmit="return endsubmit()">
				<table class="table table-hover">
        <thead>
           <tr>
             <th></th>
             <th></th>
           </tr>
        </thead>
        
        <tbody>
          <tr>
          <td>管理员账号</td>
          <td><input name="adminAccount" class="form-control form-focus" autofocus type="text" placeholder="输入账号"></td>
        </tr>
        <tr>
          
        <tr>
          <td>上传头像</td>
          <td>
            <input name="adminImg"type="file" class="form-control" value="">
          </td>
        </tr>
        <tr>
          <td>密码</td>
          <td><input name="adminPwd" class="form-control form-focus" autofocus type="password" placeholder="输入密码"></td>
        </tr>
        <tr>
          <td>确认密码</td>
          <td><input name="adminPwd2" onkeyup="validate()" class="form-control form-focus" autofocus type="password" placeholder="再次输入密码"><span id="tishi"></span></td>
        </tr>
        <tr>
        	<td>
        		<span id="tishi"></span>
        	</td>
        </tr>
      </tbody>
      <tfoot>
        <tr  style="text-align:right">
          <td></td>
           <td>
              <input id="submit" class="btn btn-primary btn6 mr10" value="提交" type="submit">
              <input class="btn btn6" onclick="history.go(-1)" value="返回" type="button">
           </td>
        </tr>
        
      </tfoot>
    </table>
    </form>
			</div>
		</div>
</div>
<div class="bottom"></div>
<div id="footer"></div>
<script>navList(12);</script>
</body>
</html>