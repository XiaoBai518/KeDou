package com.kedou.controller.bg;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.kedou.entity.Business;
import com.kedou.entity.Torder;
import com.kedou.service.bg.ReservationServiceImpl;

@Controller
@RequestMapping("/bg_reservation")
public class bg_ReservationController {
	@Resource
	private ReservationServiceImpl reservationServiceImpl;
	
	@RequestMapping(value="/bgLockRe",method=RequestMethod.POST)
	@ResponseBody
	public String lockStore(@RequestParam("str")String str,HttpServletRequest request) {
		
		if(str!=null) {
		Gson g = new Gson();
		
		 List<Torder> ul = g.fromJson(str, new TypeToken<List<Torder>>() {}.getType());//对于不是类的情况，用这个参数给出)
		 int temp=0;
		 int[] array=new int[ul.size()];
		for(Torder torder:ul) {
			array[temp]=torder.getId();
			temp++;
		}
		reservationServiceImpl.lockBus(array);
		}else {
			
		}
		
		return null;
		
	}
}
