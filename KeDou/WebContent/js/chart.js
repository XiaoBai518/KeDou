		function func1(a){
			// 使用jquery方法获取 2d context 对象
			var ctx = $("#myChart").get(0).getContext("2d");
			// 或者使用 document.getElementById 获取 2d context 对象
			// var ctx = document.getElementById("myChart").getContext("2d");
			// 使用$.zui.Chart构造Chart实例
			var myNewChart = new $.zui.Chart(ctx);
			// 创建指定Canvas的Chart实例
			 var dataArray = a;
			 var data1 = eval(dataArray);
			 var name0 = data1[0].courseName;
			 var name1 = data1[1].courseName;
			 var name2 = data1[2].courseName;
//			 function sendNum(min,max)
//			 {
//			     return Math.floor(Math.random()*(max-min))+min;
//			 };
			 var Arr = [17,20,15,35,26,45,10];
//			 for(var i=0;i<7;i++){
//				
//				 Arr.push(data1[0].orderNumber[i]);
//			 }
//			
			
			 var Arr1 = [23,30,25,27,35,40,21];
//			 for(var i=0;i<7;i++){
//					
//				 Arr1.push(data1[1].orderNumber[i]);
//			 }
			 var Arr2 = [14,23,43,35,38,25,19];
//			 for(var i=0;i<7;i++){
//					
//				 Arr2.push(data1[2].orderNumber[i]);
//			 }
			var data = {
				    // labels 数据包含依次在X轴上显示的文本标签
				    labels: ["周一", "周二", "周三", "周四", "周五", "周六", "周日"],
				    datasets: [{
				        // 数据集名称，会在图例中显示
				        label: name0+"销量",
				        // 颜色主题，可以是'#fff'、'rgb(255,0,0)'、'rgba(255,0,0,0.85)'、'red' 或 ZUI配色表中的颜色名称
				        // 或者指定为 'random' 来使用一个随机的颜色主题
				        color: "red",
				        // 数据集
				        data: Arr
				    },{
				        label: name1+"销量",
				        color: "green",
				        data: Arr1
				    },{
				        label: name2+"销量",
				        color: "blue",
				        data: Arr2
				    }]
				};
				var options = {
						 //Boolean - 是否显示描边
					    segmentShowStroke : true,
					    //String - 描边分割线的颜色
					    segmentStrokeColor : "#fff",
					    //Number - 描边分割线的宽度，单位像素
					    segmentStrokeWidth : 2,
					    //Number - 环形中间留空圆形半径的百分比
					    percentageInnerCutout : 50, // 如果设置为0则为一般实心饼图
					    // Boolean - 是否显示标签
					    scaleShowLabels: true,
					    // 标签文本模板
					    scaleLabel: "<%=value%>",
					    // String - 标签位置，可选值有：
					    // 'outside' - 在扇形区域外显示
					    // 'inside' - 在扇形区域内显示
					    // 'auto' - 自动决定显示位置
					    scaleLabelPlacement: "auto",
					    // Number - 标签行高
					    scaleLineHeight: 1,
					    //Number - 动画执行总步数
					    animationSteps : 60,
					    //String - 动画效果
					    animationEasing : "easeOutBounce",
					    //Boolean - 是否启用旋转动画
					    animateRotate : true,
					    //Boolean - 是否启用缩放动画
					    animateScale : false,
					    //String - 图例模板
					    legendTemplate : "<ul class='<%=name.toLowerCase()%>-legend'><% for (var i=0; i<segments.length; i++){%><li><span style='background-color:<%=segments[i].fillColor%>'></span><%if(segments[i].label){%><%=segments[i].label%><%}%></li><%}%></ul>"
				}; // 图表配置项，可以留空来使用默认的配置
				var myLineChart = $("#myChart").lineChart(data, options);
				$("#myChart").on("click", function(evt){
				    var activePoints = myLineChart.getPointsAtEvent(evt);
				    // activePoints 为一个数组，如果当前点击所在的位置没有对应的数据，则为空
				});
		}
		var i = 0;
//		function	fc(page,totalCount,search){
//			window.location.href='http://localhost:8080/KeDou/course/searchCourseListAsc?page='+page+'&totalCount='+totalCount+'&search='+search;
//		}