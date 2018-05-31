//个人中心面板
$(document).ready(function(){
	$("#myPhoto").hover(function(){
		$("#myPopover").toggle();
	});
});
$(document).ready(function(){
	$("#myPopover").hover(function(){
		$("#myPopover").show();
	});
	$("#myPopover").mouseleave(function(){
		$("#myPopover").hide();
	});
});
// 定义欢迎标签页
var tabs = [{
	title: '实时数据',
	url: 'iframe/realdata.html',
	type: 'iframe'
}];
//初始化标签页管理器
$(document).ready(function(){
	$('#tabs').tabs({tabs: tabs});
});

//关闭其他标签，返回标签管理器对象实例
function closeOther(){
	var myTabs = $('#tabs').data('zui.tabs');

	var myActiveTab = myTabs.getActiveTab();
	if (myActiveTab != null ) {
		myTabs.close(myActiveTab);
	}

	return myTabs;
}
//店铺实时数据
	//实时数据
	function realData(){
		// 获取标签页管理器对象实例
		var myTabs = closeOther();
		// 定义标签页
		var realdata = {
			title: '实时数据',
			url: 'iframe/realdata.html',
			type: 'iframe'
		};
		myTabs.open(realdata);
	}
	//基本信息
	function basicInfo(){
		//获取标签页管理器对象实例
		var myTabs = closeOther();
		//定义标签页
		var basicinfo = {
			title : '基本信息',
			url: 'iframe/basicinfo.html',
			type:'iframe'
		};
		myTabs.open(basicinfo);
	}
	//课程管理
		//添加课程
		function addCourse(){
			// 获取标签页管理器对象实例
			var myTabs = closeOther();
				var addCourse = {
					title: '添加课程',
					url: '/KeDou/business/toaddcourse',
					type: 'iframe'
				}
			myTabs.open(addCourse);
		}
		//课程列表
		function allCourse(){
			// 获取标签页管理器对象实例
			var myTabs = closeOther();
			// 定义标签页
			var courselist = {
				title: '所有课程',
				url: 'iframe/courselist.html',
				type: 'iframe'
			};
			myTabs.open(courselist);
		}
		//公告管理
			//添加公告
			function addAnnounce(){
				// 获取标签页管理器对象实例
				var myTabs = closeOther();
				// 定义标签页
				var addannounce = {
					title: '添加公告',
					url: 'iframe/addannounce.html',
					type: 'iframe'
				};
				myTabs.open(addannounce);
			}
			//公告列表
			function allAnnounce(){
				// 获取标签页管理器对象实例
				var myTabs = closeOther();
				// 定义标签页
				var announcelist = {
					title: '所有公告',
					url: 'iframe/announcelist.html',
					type: 'iframe'
				};
				myTabs.open(announcelist);
			}
		//预约管理
			//未处理预约
			function untreated(){
				// 获取标签页管理器对象实例
				var myTabs = closeOther();
				// 定义标签页
				var untreated = {
					title: '未处理预约',
					url: 'iframe/untreated.html',
					type: 'iframe'
				};
				myTabs.open(untreated);
			}
			//所有预约
			function allOrders(){
				// 获取标签页管理器对象实例
				var myTabs = closeOther();
				// 定义标签页
				var orderlist = {
					title: '所有预约',
					url: 'iframe/orderlist.html',
					type: 'iframe'
				};
				myTabs.open(orderlist);
			}
		//经验贴管理
			//新增经验贴
			function addExperience(){
				// 获取标签页管理器对象实例
				var myTabs = closeOther();
				// 定义标签页
				var addexperience = {
					title: '新增经验贴',
					url: 'iframe/addexperience.html',
					type: 'iframe'
				};
				myTabs.open(addexperience);
			}
			//所有经验贴
			function allExperience(){
				// 获取标签页管理器对象实例
				var myTabs = closeOther();
				// 定义标签页
				var experiencelist = {
					title: '所有经验贴',
					url: 'iframe/experiencelist.html',
					type: 'iframe'
				};
				myTabs.open(experiencelist);
			}