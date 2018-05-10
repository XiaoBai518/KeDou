
function getStyle(element, attr){
      if(element.currentStyle){
        return element.currentStyle[attr];
      }else{
        return window.getComputedStyle(element,null)[attr];
      }
    }
 function circle() {
 	var divs = document.getElementById('navigation').getElementsByTagName('div');

 		//按钮个数
 		var num = divs.length;

 	 	//按钮度数平分
 	 	var a = 70/(num-1);


 		 //半径(px)
 	 	var r = parseInt(getStyle(document.getElementById('navigation'), "height"));

 	 	//角度的Sin()
 	 	var dSin;
 	 	//角度的Cos()
 	 	var dCos

 	 	//按钮距底边距离
 	 	var y;
 	 	//按钮距右边距离
 	 	var x;

 	 	//按钮CSS属性
 	 	var bottom;
 	 	var right;

 	for (var i = 0; i < num; i++) {

 		if(i==0) {
 			divs[i].style.bottom =r-35+"px";
		divs[i].style.right = "10px";
 		}else if(i==num-1) {
 			divs[i].style.bottom ="0px";
		divs[i].style.right = r-35+"px";
 		}else {

 			 //该角度的Sin()
 	 	  dSin = Math.sin((a*i+9)*Math.PI/180);
 	 	 
 	 	 //该角度的Cos()
   		  dCos = Math.cos((a*i+9)*Math.PI/180);
   		 y = r*dCos-45;
 			x = r*dSin-35;
   		
   		  
 		divs[i].style.bottom = Math.ceil(y)+"px";
		divs[i].style.right = Math.ceil(x)+"px";
 		} 		
 	};
 }