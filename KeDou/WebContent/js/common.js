/**禁止用户回车键提交*/	
function keyDown(e) {
			var keynum;
			keynum = window.event?e.keyCode:e.which;
			if(keynum ==13) {
				return false;
			}
		}

/**管理员后台登陆页面的登录逻辑判断*/
function bgonsubmit() {
	
}
