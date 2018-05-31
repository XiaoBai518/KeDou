/**
 * 地区代码开始函数
 * @author veer
 * @mail veer.mr@qq.com
 */
function districts_start(){
	data_districts = get_districts();//获取所有地区
	data_valueType = 'name';// option的列表中 value属性的值是 地区名还是地区id,如果是地区id就用 code
	var distpicker = jQuery('[data-toggle="distpicker"]');//定义所有要使用地区的元素上
	defeat_init(distpicker);
}
/**
 * 对 distpicker 开始处理
 */
function defeat_init(distpicker){
	distpicker.each(function(i){
		status_count = 1;//这个是固定值不是改，这个就是为了让jQuery的change函数(选择select时)执行有效次数，不多执行
		var data_toggle_obj = jQuery(this);
		distpicker_province_list(data_toggle_obj);
	});
}
/**
 * 先从省开始
 */
function distpicker_province_list(obj){
	var data_style = obj.attr("data-style");//这个是获取地区列表的展现形式，默认是 select/option格式，还可以定义ul/li 格式 在html页面上定义即可
	if(typeof(data_style) === 'undefined' && data_style ==''){
		data_style = 'option';
	}
	if(data_style != 'option'){
		status_count = 0;
	}
	var data_placeholder_province = obj.attr("data-placeholder-province");//默认option上的开头选择 比如：---请选择省--- 这个在html中定义
	if(typeof(data_placeholder_province) === 'undefined' || data_placeholder_province == ""){
		data_placeholder_province = false;
	}
	var selectedcid = obj.find(".distpicker_province").attr("data-province");//默认选中的省的id 在html中定义
	if(typeof(selectedcid) === 'undefined' || selectedcid == "0"){
		selectedcid = false;
	}
	codeid = 1;//固定值 distpicker.data.js中 定义所有省的键值
	var list = get_distpicker_options(codeid,data_style,data_placeholder_province,selectedcid);//调用组成option的列表,里面的参数意思下面有说
	obj.find(".distpicker_province").html(list).attr("data-province","0");//后面半句attr("data-province","0")表示初始化时被选中的省已经被执行过了，做个标记
	distpicker_city_list(obj);//紧接着进入市的选择
	if(selectedcid != false){//这个就是指有默认被选中的省存在就执行下面的
		//这里边是表示选择完省后执行你定义的函数（在html中定义，如果不需要选择省后要干点啥就不要定义“data-choose-back-fun”这个属性）
		var eventdispather = obj.find(".distpicker_province").attr("data-choose-back-fun");
		if(typeof(eventdispather) !== 'undefined' && eventdispather !=''){
			eval(eventdispather + "(obj)");
		}
	}
}
/**
 * 这里是市的逻辑
 */
function distpicker_city_list(obj){
	var data_style = obj.attr("data-style");//同上
	if(typeof(data_style) === 'undefined' && data_style ==''){
		data_style = 'option';
	}
	var data_placeholder_city = obj.attr("data-placeholder-city");//默认option上的开头选择 比如：---请选择市--- 这个在html中定义
	if(typeof(data_placeholder_city) === 'undefined'){
		data_placeholder_city = false;
	}
	var selectedcid = obj.find(".distpicker_city").attr("data-city");//默认选中的市的id 在html中定义
	if(typeof(selectedcid) === 'undefined' || selectedcid == "0"){
		selectedcid = false;
	}
	//为了找到省的id我们要分开处理 一个是 select/option 另一种是 ul/li 之类的 当然你也可以 都用 div啊什么的
	if(data_style == 'option'){
		//如果是select/option模式
		var codeid = obj.find(".distpicker_province").find(':selected').attr("data-code");//得到省的id
	}else{
		//如果是其他模式得到省的id代码
		var codeid = obj.find(".distpicker_province").find(data_style+'[selected="selected"]').attr("data-code");
	}
	//如果没有定义省的id 就给赋值false。这里有人问为什么会出现没定义的情况,当然会出现了，是因为显示地区列表时我们不需要有默认被选中的地区，比如option开头就是“---请选择省---”之类的。
	if(typeof(codeid) === 'undefined'){
		var codeid = false;
	}
	//这边做select省时也分开处理了 select/option时用change函数，众所周知 select的option选项不能用click事件。其他模式就用click事件
	if(data_style == 'option'){
		if(status_count == 1){//这个判断就是我说的有效执行次数，只要初始化一次就行，因为这个<select>不是动态生成的所以去掉的话会被重复定义浪费浏览器性能
			obj.find(".distpicker_province").change(function(){//人为选择啦
				var codeid = jQuery(this).find(':selected').attr("data-code");//被先中的省id
				//下面调用的这个函数是根据省的id获取它下面的市列表
				//里面的参数在这里说下吧，后面就不说了
				//codeid：这里即省的id;data_style：列表模式上面说过的;data_placeholder_city：初始化时默认选项的头头;selectedcid：初始化时默认被选中的项
				var list = get_distpicker_options(codeid,data_style,data_placeholder_city,selectedcid);
				obj.find(".distpicker_city").html(list).attr("data-city","0");//后面半句attr("data-city","0")表示初始化时被选中的市已经被执行过了，做个标记
				selectedcid = false;//只要人为选择过之后默认的被选中项就被执行过来做个标记
				distpicker_district_list(obj);//紧跟着定义区的函数逻辑
				//下面就是做选完省之后调用的函数，这个函数在html中定义，不需要调就不定义这个“data-choose-back-fun”值
				var eventdispather = jQuery(this).attr("data-choose-back-fun");
				if(typeof(eventdispather) !== 'undefined' && eventdispather !=''){
					eval(eventdispather + "(obj)");
				}
			});
		}
		
	}else{
		//这里是其他模式的click事件选择省逻辑和上面一样
		obj.find(".distpicker_province "+data_style).unbind().click(function(){
			obj.find(".distpicker_province").find(data_style+'[selected="selected"]').removeAttr("selected").removeClass("selected");
			jQuery(this).attr("selected","selected").addClass("selected");
			var codeid = jQuery(this).attr("data-code");
			var list = get_distpicker_options(codeid,data_style,data_placeholder_city,selectedcid);
			obj.find(".distpicker_city").html(list).attr("data-city","0");
			selectedcid = false;
			distpicker_district_list(obj);
			var eventdispather = jQuery(this).parent(".distpicker_province").attr("data-choose-back-fun");
			if(typeof(eventdispather) !== 'undefined' && eventdispather !=''){
				eval(eventdispather + "(obj)");
			}
		});
	}
	//下面是初始化市的select
	var list = get_distpicker_options(codeid,data_style,data_placeholder_city,selectedcid);
	obj.find(".distpicker_city").html(list).attr("data-city","0");
	distpicker_district_list(obj);//初始化区
	if(selectedcid != false){
		//如果定义了选择完市要调用的函数，这里初始化时也调用了
		var eventdispather = obj.find(".distpicker_city").attr("data-choose-back-fun");
		if(typeof(eventdispather) !== 'undefined' && eventdispather !=''){
			eval(eventdispather + "(obj)");
		}
	}
	
}
/**
 * 这里是区的逻辑
 */
function distpicker_district_list(obj){
	var data_style = obj.attr("data-style");
	if(typeof(data_style) === 'undefined' && data_style ==''){
		data_style = 'option';
	}
	var data_placeholder_district = obj.attr("data-placeholder-district");
	if(typeof(data_placeholder_district) === 'undefined'){
		data_placeholder_district = false;
	}
	var selectedcid = obj.find(".distpicker_district").attr("data-district");
	if(typeof(selectedcid) === 'undefined' || selectedcid == "0"){
		selectedcid = false;
	}
	if(data_style == 'option'){
		var codeid = obj.find(".distpicker_city").find(':selected').attr("data-code");
	}else{
	    var codeid = obj.find(".distpicker_city").find(data_style+'[selected="selected"]').attr("data-code");
	}
	if(typeof(codeid) === 'undefined'){
		var codeid = false;
	}
	if(data_style == 'option'){
        if(status_count == 1){
        	obj.find(".distpicker_city").change(function(){
    			var codeid = jQuery(this).find(':selected').attr("data-code");
    			var list = get_distpicker_options(codeid,data_style,data_placeholder_district,selectedcid);
    			obj.find(".distpicker_district").html(list).attr("data-district","0");
    			selectedcid = false;
    			distpicker_district_list_choose_back(obj);
    			var eventdispather = jQuery(this).attr("data-choose-back-fun");
    			if(typeof(eventdispather) !== 'undefined' && eventdispather !=''){
    				eval(eventdispather + "(obj)");
    			}
    		});
        }
		
	}else{
		obj.find(".distpicker_city "+data_style).unbind().click(function(){
			obj.find(".distpicker_city").find(data_style+'[selected="selected"]').removeAttr("selected").removeClass("selected");
			jQuery(this).attr("selected","selected").addClass("selected");
			var codeid = jQuery(this).attr("data-code");
			var list = get_distpicker_options(codeid,data_style,data_placeholder_district,selectedcid);
			obj.find(".distpicker_district").html(list).attr("data-district","0");
			selectedcid = false;
			distpicker_district_list_choose_back(obj);
			var eventdispather = jQuery(this).parent(".distpicker_city").attr("data-choose-back-fun");
			if(typeof(eventdispather) !== 'undefined' && eventdispather !=''){
				eval(eventdispather + "(obj)");
			}
		});
	}
	
	var list = get_distpicker_options(codeid,data_style,data_placeholder_district,selectedcid);
	obj.find(".distpicker_district").html(list).attr("data-district","0");
	distpicker_district_list_choose_back(obj);
	if(selectedcid != false){
		var eventdispather = obj.find(".distpicker_district").attr("data-choose-back-fun");
		if(typeof(eventdispather) !== 'undefined' && eventdispather !=''){
			eval(eventdispather + "(obj)");
		}
	}
}
/**
 * 这个函数的作用是选择完区后要做的事
 */
function distpicker_district_list_choose_back(obj){
	var data_style = obj.attr("data-style");
	if(typeof(data_style) === 'undefined' && data_style ==''){
		data_style = 'option';
	}
	if(data_style == 'option'){
		if(status_count == 1){
			obj.find(".distpicker_district").change(function(){
				var eventdispather = jQuery(this).attr("data-choose-back-fun");
				if(typeof(eventdispather) !== 'undefined' && eventdispather !=''){
					eval(eventdispather + "(obj)");
				}
			});
			status_count = 0;
		}
		
	}else{
		obj.find(".distpicker_district "+data_style).unbind().click(function(){
			obj.find(".distpicker_district").find(data_style+'[selected="selected"]').removeAttr("selected").removeClass("selected");
			jQuery(this).attr("selected","selected").addClass("selected");
			var eventdispather = jQuery(this).parent(".distpicker_district").attr("data-choose-back-fun");
			if(typeof(eventdispather) !== 'undefined' && eventdispather !=''){
				eval(eventdispather + "(obj)");
			}
		});
	}
	
}
/**
 * 这个就是组成选项的列表了参数上面已经说过了
 */
function get_distpicker_options(id,data_style,placeholder,selectedcid){
	var optionlist = '';
	if(placeholder){
		var str = ' data-code=' + 0 + ' data-text=' + placeholder + ' value=' + placeholder;
		optionlist +=  '<' + data_style + str + '>' + placeholder + '</' + data_style + '>';
	}
	if(id != false || id != "0"){
		var data = data_districts[id];
		if(typeof(data) === 'undefined'){
			data = {};
			//return '';
		}
		jQuery.each(data, function (i, n) {
			var str = ' data-code=' + i + ' data-text=' + n + ' value=' + (data_valueType === 'name' ? n : i);
			if(i == selectedcid){
				str = str + ' selected="selected"';
				if(data_style != 'option'){
					str = str + ' class="selected"';
				}
			}
			optionlist +=  '<' + data_style + str + '>' + n + '</' + data_style + '>';
	      });
	}
	return optionlist;
}