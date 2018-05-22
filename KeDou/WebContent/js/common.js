/**禁止用户回车键提交*/	
function keyDown(e) {
			var keynum;
			keynum = window.event?e.keyCode:e.which;
			if(keynum ==13) {
				return false;
			}
		}